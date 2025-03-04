<?php
namespace App\Services;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\HorarioSemanal;
use App\Models\Empleado;
use App\Models\Feriado;
use App\Models\HorarioDia;
use App\Models\Vacaciones;
use Carbon\Carbon;

class HorarioService
{

    private $semana = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"];

    public function getHorarioSemanal()
    {
        $user = Auth::user();
        $Empleado = Empleado::where('id_user', $user->id)->first();
        $records = HorarioSemanal::with('horarioDia')->where('id', $Empleado->id_horario_semanal)->first();
        
        // Acceder a la relación 'horarioDia' para obtener los días
        $horarioDias = $records->horarioDia;

        $horario = [];
        
        // Obtener las fechas de la semana actual
        $fechasSemana = $this->obtenerFechasSemanaActual('d-m-Y');
        $fechasS = $this->obtenerFechasSemanaActual('Y-m-d');
        $vacaciones = $this->obtenerFechasVacacionesSemanaActual($Empleado->id);
        $feriados = $this->obtenerFeriadosSemanaActual();
        
        foreach ($this->semana as $index => $dia) {
            $found = false;
            
            foreach ($horarioDias as $horarioDia) {
                if ($horarioDia->dia == $dia) {
                    $d = $this->buildHorarioDia($horarioDia, $fechasSemana[$index], $fechasS[$index], $feriados, $vacaciones);
                    array_push($horario, $d);
                    $found = true;
                    break;
                }
            }

            if (!$found) {
                $d = $this->buildHorarioDiaNoEncontrado($dia, $fechasSemana[$index], $fechasS[$index], $feriados, $vacaciones);
                array_push($horario, $d);
            }
        }
        
        return $horario;
    }

    private function buildHorarioDia($horarioDia, $fechaFormato, $fechaS, $feriados, $vacaciones)
    {
        $d = [
            'dia' => $horarioDia->dia . ', ' . $fechaFormato,
            'hora_entrada' => $horarioDia->hora_entrada,
            'hora_salida' => $horarioDia->hora_salida,
            'feriado' => false,
            'vacaciones' => false,
            'mensaje' => ''
        ];

        foreach ($feriados as $feriado) {
            if ($fechaS == $feriado->fecha) {
                $d['feriado'] = true;
                $d['mensaje'] = $feriado->descripcion;
                break;
            }
        }
        
        foreach ($vacaciones as $fecha => $descripcionVacacion) {
            if ($fechaS == $fecha) {
                $d['vacaciones'] = true;
                $d['mensaje'] = $descripcionVacacion;
                break;
            }
        }
        
        return $d;
    }

    private function buildHorarioDiaNoEncontrado($dia, $fechaFormato, $fechaS, $feriados, $vacaciones)
    {
        $d = [
            'dia' => $dia . ', ' . $fechaFormato,
            'hora_entrada' => '',
            'hora_salida' => '',
            'feriado' => false,
            'vacaciones' => false,
            'mensaje' => 'Nada Programado'
        ];
        
        foreach ($feriados as $feriado) {
            if ($fechaS == $feriado->fecha) {
                $d['feriado'] = true;
                $d['mensaje'] = $feriado->descripcion;
                break;
            }
        }
        
        foreach ($vacaciones as $fecha => $descripcionVacacion) {
            if ($fechaS == $fecha) {
                $d['vacaciones'] = true;
                $d['mensaje'] = $descripcionVacacion;
                break;
            }
        }
        
        return $d;
    }
    public function obtenerFechasSemanaActual(String $formato) {
        Carbon::setLocale('es');

        // Obtener la fecha actual y establecer la zona horaria
        $fechaActual = Carbon::now();
        $fechaActual->setTimezone('America/Lima');
    
        // Obtener el día de inicio de la semana (lunes de esta semana)
        $fechaInicioSemana = $fechaActual->copy()->startOfWeek();
    
        $fechas = [];
    
        // Generar las fechas para los siete días de la semana
        for ($i = 0; $i < 7; $i++) {
            $fechaFormateada = $fechaInicioSemana->format($formato);
            $fechas[] = $fechaFormateada;
            $fechaInicioSemana->addDay();
        }
    
        return $fechas;
    }
    public function obtenerFeriadosSemanaActual()
    {
        Carbon::setLocale('es');

        // Obtener la fecha de inicio de la semana actual (lunes) y establecer la zona horaria
        $inicioSemana = Carbon::now()->startOfWeek()->setTimezone('America/Lima')->format('Y-m-d');

        // Obtener la fecha de fin de la semana actual (domingo) y establecer la zona horaria
        $finSemana = Carbon::now()->endOfWeek()->setTimezone('America/Lima')->format('Y-m-d');

        // Realizar la consulta para obtener los feriados de la semana actual
        $feriados = Feriado::whereBetween('fecha', [$inicioSemana, $finSemana])->get();

        return $feriados;
    }

    public function obtenerFechasVacacionesSemanaActual($id_empleado)
    {
        Carbon::setLocale('es');

        // Obtener la fecha de inicio de la semana actual (lunes) y establecer la zona horaria
        $inicioSemana = Carbon::now()->startOfWeek()->setTimezone('America/Lima')->format('Y-m-d');
    
        // Obtener la fecha de fin de la semana actual (domingo) y establecer la zona horaria
        $finSemana = Carbon::now()->endOfWeek()->setTimezone('America/Lima')->format('Y-m-d');
    
        // Realizar la consulta para obtener los registros de vacaciones del empleado para la semana actual
        $vacaciones = Vacaciones::where('id_empleado', $id_empleado)
                                ->where(function ($query) use ($inicioSemana, $finSemana) {
                                    $query->whereBetween('fecha_inicio', [$inicioSemana, $finSemana])
                                        ->orWhereBetween('fecha_fin', [$inicioSemana, $finSemana]);
                                })
                                ->get();
    
        // Construir el array de fechas y descripciones de vacaciones
        $fechasVacaciones = [];
    
        foreach ($vacaciones as $vacacion) {
            $fechaInicio = Carbon::parse($vacacion->fecha_inicio);
            $fechaFin = Carbon::parse($vacacion->fecha_fin);
    
            // Iterar sobre cada día entre fechaInicio y fechaFin
            for ($date = $fechaInicio; $date->lte($fechaFin); $date->addDay()) {
                $fechasVacaciones[$date->format('Y-m-d')] = $vacacion->descripcion;
            }
        }
    
        return $fechasVacaciones;
    }

    public function register(Request $request)
    {
        // Validar los datos de entrada
        $validatedData = $request->validate([
            'descripcion' => 'required|string|max:255',
            'dias' => 'required|array|min:1',
            'dias.*.dia' => 'required|string|max:255',
            'dias.*.hora_entrada' => 'required|date_format:H:i:s',
            'dias.*.hora_salida' => 'required|date_format:H:i:s',
        ]);

            // Crear y guardar el HorarioSemanal
            $hSemanal = new HorarioSemanal();
            $hSemanal = $this->buildData($hSemanal, $validatedData);
            $hSemanal->save();
    
            // Crear y guardar los HorarioDia correspondientes
            foreach ($validatedData['dias'] as $diaData) {
                $hDia = new HorarioDia();
                $diaData['id_horario_semanal'] = $hSemanal->id;
                $hDia = $this->buildDataHD($hDia, $diaData);
                $hDia->save();
            }
            
            return $hSemanal->id;
    }

    public function getAll()
    {
        $horarioSemanal = HorarioSemanal::select('id','descripcion')
                    ->where('is_delete', false)
                    ->get();

        return $horarioSemanal;
    }

    public function getId($id)
    {
        // Buscar el HorarioSemanal por su ID y verificar que no esté eliminado
        $horarioSemanal = HorarioSemanal::where('is_delete', false)->find($id);

        // Verificar si el HorarioSemanal existe
        if ($horarioSemanal) {
            // Obtener todos los HorarioDia asociados con el HorarioSemanal
            $horariosDias = HorarioDia::select('id', 'dia', 'hora_entrada', 'hora_salida', 'id_horario_semanal')
                                    ->where('id_horario_semanal', $id)
                                    ->get();

            // Retornar la respuesta en formato JSON
            return [
                'id' => $horarioSemanal->id,
                'descripcion' => $horarioSemanal->descripcion,
                'horariosDias' => $horariosDias
            ];
        } else {
            // Retornar un mensaje de error si no se encuentra el HorarioSemanal
            return ['message' => 'Horario no encontrado'];
        }
    }

    public function update(Request $request)
    {
         // Validar los datos de entrada
        $validatedData = $request->validate([
            'id' => 'required|integer|exists:horario_semanal,id',
            'descripcion' => 'required|string|max:255',
            'dias' => 'required|array|min:1',
            'dias.*.id' => 'sometimes|integer|exists:horario_dia,id',
            'dias.*.dia' => 'required|string|max:255',
            'dias.*.hora_entrada' => 'required|date_format:H:i:s',
            'dias.*.hora_salida' => 'required|date_format:H:i:s',
        ]);

        
        // Buscar el HorarioSemanal por su ID
        $hSemanal = HorarioSemanal::findOrFail($validatedData['id']);

        // Actualizar los datos del HorarioSemanal
        $hSemanal->descripcion = $validatedData['descripcion'];
        $hSemanal->save();

        // Obtener los IDs de los HorarioDia que se deben mantener
        $diasIds = array_column($validatedData['dias'], 'id');

        // Eliminar los HorarioDia que no están en la lista de IDs a mantener
        HorarioDia::where('id_horario_semanal', $hSemanal->id)
                ->whereNotIn('id', $diasIds)
                ->delete();

        // Actualizar o crear los HorarioDia
        foreach ($validatedData['dias'] as $diaData) {
            if (isset($diaData['id'])) {
                // Actualizar el HorarioDia existente
                $hDia = HorarioDia::findOrFail($diaData['id']);
            } else {
                // Crear un nuevo HorarioDia
                $hDia = new HorarioDia();
                $hDia->id_horario_semanal = $hSemanal->id;
            }

            // Actualizar los datos del HorarioDia
            $hDia->dia = $diaData['dia'];
            $hDia->hora_entrada = $diaData['hora_entrada'];
            $hDia->hora_salida = $diaData['hora_salida'];
            $hDia->save();
        }

        return $hSemanal;
    }

    public function delete($id)
    {
        // Buscar el área por su ID y actualizar el campo is_delete a true
        $hSemanal = HorarioSemanal::where('id', $id)->first();

        if ($hSemanal) {
            $hSemanal->is_delete = true;
            $hSemanal->save();

            return true;
        } else {
            return false;
        }
    
    }

    private function buildData(HorarioSemanal $hSemanal, array $data): HorarioSemanal
    {
        $hSemanal->descripcion = $data['descripcion'];

        return $hSemanal;
    }

    private function buildDataHD(HorarioDia $hDia, array $data): HorarioDia
    {
        $hDia->dia = $data['dia'];
        $hDia->hora_entrada = $data['hora_entrada'];
        $hDia->hora_salida = $data['hora_salida'];
        $hDia->id_horario_semanal = $data['id_horario_semanal'];

        return $hDia;
    }

}
