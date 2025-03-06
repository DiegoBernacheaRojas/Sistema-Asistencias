<?php

namespace App\Services;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\Justificacion;
use App\Models\Empleado;
use App\Models\RegistroMarcacion;
use Carbon\Carbon;

class JustificacionService
{

    public function register(Request $request): Justificacion
    {
        $service = new MarcarAsistenciaService();
        $ultimoRegistro = $service->getLastRegistroMarcacion();
        $request->merge(['razon' => $ultimoRegistro->estado]);
        $validatedData = $request->validate([
            'titulo' => ['required', 'string'],
            'descripcion' => ['required', 'string', 'max:255'],
            'razon' => ['required', 'string', 'max:255']
        ]);

        $justificacion = new Justificacion();
        $justificacion = $this->buildData($justificacion, $validatedData);
        $justificacion->save();
        $justificacionId = $justificacion->id;


        $service->updateIdJustificacion($justificacionId);
        return $justificacion;
    }

    public function registerAusencia(Request $request)
    {
        $request->merge(['razon' => "AUSENCIA"]);
        $validatedData = $request->validate([
            'titulo' => ['required', 'string'],
            'descripcion' => ['required', 'string', 'max:255'],
            'razon' => ['required', 'string', 'max:255']
        ]);

        $service = new MarcarAsistenciaService();
        $regist = new Request();
        $regist->merge(['ausente' => true]);
        $records = $service->register($regist);

        if (isset($records->success) && $records->success === true) {
            $justificacion = new Justificacion();
            $justificacion = $this->buildData($justificacion, $validatedData);
            $justificacion->save();
            $justificacionId = $justificacion->id;
            $service->updateIdJustificacionAusencia($justificacionId);
            return $justificacion;
        }
        return $records->message;
    }

    public function historial()
    {
        $user = Auth::user();
        $empleado = Empleado::where('id_user', $user->id)->first();

        $historialJustificaciones = Justificacion::whereIn('id', function ($query) use ($empleado) {
            $query->select('id_justificacion')
                ->from('registro_marcacion')
                ->where('id_empleado', $empleado->id)
                ->distinct('id_justificacion');
        })
            ->with(['registro_marcacion' => function ($query) use ($empleado) {
                $query->select('id_justificacion', 'fecha')
                    ->where('id_empleado', $empleado->id);
            }])
            ->get(['id', 'titulo', 'estado', 'razon']);

        // Manipular el resultado para estructurar los datos como necesitas
        $data = $historialJustificaciones->map(function ($justificacion) {
            $fechaString = $justificacion->registro_marcacion->isEmpty() ? null : $justificacion->registro_marcacion->first()->fecha;
            $fecha = $fechaString ? Carbon::parse($fechaString) : null;

            // Verificar si $fecha es null antes de formatearlo
            $fechaFormateada = $fecha ? $fecha->format('d-m-Y') : null;

            return [
                "id" => $justificacion->id,
                'titulo' => $justificacion->titulo,
                'fecha' => $fechaFormateada,
                'estado' => $justificacion->estado,
                'razon' => $justificacion->razon
            ];
        });

        return $data;
    }

    public function detalleHistorial(int $id)
    {
        // Obtener el detalle de la justificación específica
        $justificacion = Justificacion::find($id);

        if (!$justificacion) {
            return response()->json([
                'success' => false,
                'message' => 'No se encontró la justificación con el ID proporcionado.'
            ], 404);
        }

        // Obtener el registro de marcación correspondiente a la justificación
        $registroMarcacion = RegistroMarcacion::where('id_justificacion', $id)->first();

        // Convertir la fecha de string a Carbon
        $fecha = $registroMarcacion ? Carbon::parse($registroMarcacion->fecha) : null;

        // Formatear la fecha
        $fechaFormateada = $fecha ? $fecha->format('d-m-Y') : null;

        // Crear y retornar el array de respuesta
        $data = [
            'titulo' => $justificacion->titulo,
            'fecha' => $fechaFormateada,
            'estado' => $justificacion->estado,
            'razon' => $justificacion->razon,
            'descripcion' => $justificacion->descripcion,
            'comentario' => $justificacion->comentario
        ];
        return $data;
    }

    public function getAllDay()
    {

        $fechaActual = Carbon::now()->format('Y-m-d');

        // Obtener los id_justificacion únicos de las entradas de registro_marcacion del día actual
        $justificacionesIds = RegistroMarcacion::whereDate('fecha', $fechaActual)
            ->whereNotNull('id_justificacion')
            ->distinct()
            ->pluck('id_justificacion');

        // Obtener las justificaciones con estado "ESPERA" usando los ids obtenidos
        // Obtener las justificaciones con estado "ESPERA" usando los ids obtenidos
        $historialJustificaciones = Justificacion::whereIn('id', $justificacionesIds)
        ->where('estado', 'ESPERA')
        ->with(['registro_marcacion' => function($query) use ($fechaActual) {
            $query->select('id', 'id_justificacion', 'id_empleado')
                ->whereDate('fecha', $fechaActual);
        }])
        ->get(['id', 'titulo', 'estado', 'razon']);

        // Manipular el resultado para estructurar los datos como necesitas
        $data = $historialJustificaciones->map(function ($justificacion) {
        $registroMarcacion = $justificacion->registro_marcacion->first();

        if ($registroMarcacion) {
            $empleado = Empleado::find($registroMarcacion->id_empleado);
            
            if ($empleado) {
                return [
                    "id" => $justificacion->id,
                    'dni' => $empleado->dni,
                    'nombre_completo' => $empleado->nombres . ' ' . $empleado->apellidos,
                    'titulo' => $justificacion->titulo,
                    'razon' => $justificacion->razon
                ];
            }
        }

        // En caso de que no se encuentre el registro de marcación o el empleado
        return null;
        })->filter(); // Filtra los valores nulos

        return $data;
    }
    
    public function update(Request $request)
    {
        // Validar los datos de entrada
        $validatedData = $request->validate([
            'id' => 'required',
            'estado' => 'required|string|max:255',
            'comentario' => 'nullable|string|max:255',
        ]);

       
        // Encontrar la justificación existente
        $justificacion = Justificacion::findOrFail($validatedData['id']);

        // Actualizar los datos de la justificación
        $justificacion->estado = $validatedData['estado'];
        $justificacion->comentario = $validatedData['comentario'];

        
        // Guardar los cambios
        $justificacion->save();

        if ($justificacion->estado == "APROBADO") {
            RegistroMarcacion::where('id_justificacion', $justificacion->id)
                ->update(['estado' => 'JUSTIFICADO']);
        }

        return $justificacion;
        
    }

    private function buildData(Justificacion $justificacion, array $data): Justificacion
    {
        $justificacion->titulo = $data['titulo'];
        $justificacion->descripcion = $data['descripcion'];
        $justificacion->razon = $data['razon'];
        $justificacion->estado = "ESPERA";
        $justificacion->comentario = $data['comentario'] ?? '';

        return $justificacion;
    }
}
