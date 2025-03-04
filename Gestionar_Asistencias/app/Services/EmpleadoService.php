<?php
namespace App\Services;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\Empleado;
use App\Models\RegistroMarcacion;
use App\Models\User;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Http;

class EmpleadoService
{


    public function getEmpleado()
    {
        $user = Auth::user();
    
        $empleado = Empleado::with(['Area' => function ($query) {
            $query->where('is_delete', '=', 0); // Filtrar las áreas donde is_delete no es 1
        }, 'Cargo' => function ($query) {
            $query->where('is_delete', '=', 0); // Filtrar los cargos donde is_delete no es 1
        }, 'User'])
        ->select('id', 'foto', 'nombres', 'apellidos', 'telefono', 'dni', 'is_delete', 'id_cargo', 'id_area', 'id_user')
        ->where('id_user', $user->id)
        ->where('is_delete', '=', 0) // Verificar que is_delete del empleado sea 0
        ->first();
    
        return $empleado;
    }

    public function verificarFacialData(Request $request) {
        $user = Auth::user();
        $empleado = Empleado::where('id_user', $user->id)->first();
    
        // Configurar la solicitud a la API de Face++
        $response = Http::asForm()->post('https://api-us.faceplusplus.com/facepp/v3/compare', [
            'api_key' => 'CYTDKw1XTHTNd-uEbQnVGxMDcHHVxdtZ',
            'api_secret' => 'rnwWCaanOPEDrL2bPaFVkua0RWRrhBXf',
            'image_base64_1' => $request->facial_data, // Aquí deberías proporcionar la imagen base64
            'image_base64_2' => $empleado->facial_data // Y aquí la imagen base64 guardada en tu base de datos
        ]);
    
        // Verificar si la solicitud fue exitosa
        if ($response->successful()) {
            $responseData = $response->json();
    
            // Verificar si la comparación fue exitosa según la respuesta de la API de Face++
            if ($responseData['confidence'] >= 70) {
                return (object) [
                    'success' => true,
                    'message' => 'La verificación facial fue exitosa.'
                ];
            } else {
                return (object) [
                    'success' => false,
                    'message' => 'No se pudo verificar los datos faciales.'
                ];
            }
        } else {
            return (object) [
                'success' => false,
                'message' => 'Error al conectarse con la API de Face++.'
            ];
        }
    }

    public function updateFotoPerfil(Request $request)
    {
        $user = Auth::user();

        // Validar y guardar la imagen
        if ($request->has('foto')) {
            // Obtener la imagen codificada en Base64 de la solicitud
            $imagenCodificada = $request->input('foto');

            // Decodificar la imagen Base64 y guardarla en un archivo
            $imagenDecodificada = base64_decode($imagenCodificada);

            $emailFolder = explode('@', $user->email)[0]; // Obtener el email sin el texto después del arroba
            $carpetaUsuario = public_path('uploads/fotoPerfil/' . $emailFolder.'ID'.$user->id);

            // Crear la carpeta si no existe
            if (!File::exists($carpetaUsuario)) {
                File::makeDirectory($carpetaUsuario, 0777, true, true);
            }


            $empleado = Empleado::where('id_user', $user->id)->first();
            $apellidos = str_replace(' ', '_', $empleado->apellidos);
            // Generar un nombre único para la imagen
            $nombreImagen = $apellidos. '_' . time() . '_foto_perfil.jpg'; // Cambia la extensión según el formato de imagen que esperes

            $ruta = $carpetaUsuario . '/' . $nombreImagen;
            
            // Cargar la imagen y obtener sus dimensiones
            $imagen = imagecreatefromstring($imagenDecodificada);
            $anchoOriginal = imagesx($imagen);
            $altoOriginal = imagesy($imagen);

            // Calcular el tamaño del cuadrado
            $tamañoCuadrado = min($anchoOriginal, $altoOriginal);

            // Crear un lienzo cuadrado
            $lienzo = imagecreatetruecolor($tamañoCuadrado, $tamañoCuadrado);

            // Copiar la parte central de la imagen original al lienzo cuadrado
            $x = ($anchoOriginal - $tamañoCuadrado) / 2;
            $y = ($altoOriginal - $tamañoCuadrado) / 2;
            imagecopy($lienzo, $imagen, 0, 0, $x, $y, $tamañoCuadrado, $tamañoCuadrado);

            // Guardar el lienzo como imagen
            imagejpeg($lienzo, $ruta);

            // Liberar recursos
            imagedestroy($imagen);
            imagedestroy($lienzo);

            // Si tienes un modelo Empleado relacionado con el usuario, también actualiza su campo 'foto'
            $empleado = Empleado::where('id_user', $user->id)->first();
            if ($empleado) {
                // Eliminar la imagen antigua si existe
                if (File::exists(public_path($empleado->foto))) {
                    File::delete(public_path($empleado->foto));
                }

                $empleado->foto = '/' .'uploads/fotoPerfil/' .$emailFolder.'ID'.$user->id . '/' . $nombreImagen;
                $empleado->save();
            }
        }
    }

    public function updateFacialData(Request $request)
    {
        $user = Auth::user();

        $empleado = Empleado::where('id_user', $user->id)->first();
        if ($empleado) {
            
            $empleado->facial_data = $request->input('facial_data');
            $empleado->save();
        }
        
    }

    public function getEmpleadosPresentes()
    {
        $fechaActual = Carbon::now()->toDateString();

        // Obtener el total de empleados registrados
        $totalEmpleados = Empleado::count();

        // Obtener los empleados que tienen registro de entrada para la fecha actual
        $empleadosConEntrada = RegistroMarcacion::where('fecha', $fechaActual)
            ->where('tipo', 'entrada')
            ->pluck('id_empleado')
            ->toArray();

        // Obtener los empleados que tienen registro de salida para la fecha actual
        $empleadosConSalida = RegistroMarcacion::where('fecha', $fechaActual)
            ->where('tipo', 'salida')
            ->pluck('id_empleado')
            ->toArray();

        // Determinar los empleados presentes
        $empleadosPresentes = array_diff($empleadosConEntrada, $empleadosConSalida);

        // Contar los presentes y no presentes
        $numPresentes = count($empleadosPresentes);
        $numNoPresentes = $totalEmpleados - $numPresentes;
        
        return (object) [
            'presente' => $numPresentes,
            'no_presente' => $numNoPresentes
        ];
    }

    public function register(Request $request)
    {
        $validatedData = $request->validate([
            'nombres' => ['required', 'string', 'max:255'],
            'apellidos' => ['required', 'string', 'max:255'],
            'telefono' => ['nullable', 'string', 'max:9'], // Hacer que el teléfono sea opcional (nullable)
            'dni' => ['required', 'string', 'max:8'],
            'id_area' => ['required', 'integer', 'max:20'],
            'id_horario_semanal' => ['required', 'integer', 'max:20'],
            'id_cargo' => ['required', 'integer', 'max:20'],
            'id_user' => ['nullable', 'integer', 'max:20'],
        ]);
        $empleado = new Empleado();
        $empleado= $this->buildData($empleado,$validatedData);
        $empleado->save();

        return $empleado;
        
    }

    public function getAll()
    {
        // Obtener todos los empleados con las relaciones necesarias
        $empleados = Empleado::with(['area:id,nombre', 'horarioSemanal:id,descripcion', 'cargo:id,nombre', 'user:id'])
            ->where('is_delete', 0)
            ->get();

        // Recorrer cada empleado para verificar si el id_user existe
        foreach ($empleados as $empleado) {
            if ($empleado->id_user && !User::where('id', $empleado->id_user)->exists()) {
                $empleado->id_user = null;
                $empleado->save();
            }
        }

        // Formatear el resultado
        $result = $empleados->map(function ($empleado) {
            return [
                'id' => $empleado->id,
                'nombres' => $empleado->nombres,
                'apellidos' => $empleado->apellidos,
                'telefono' => $empleado->telefono,
                'dni' => $empleado->dni,
                'area' => [
                    'id' => $empleado->area->id,
                    'nombre' => $empleado->area->nombre,
                ],
                'horario_semanal' => [
                    'id' => $empleado->horarioSemanal->id,
                    'descripcion' => $empleado->horarioSemanal->descripcion,
                ],
                'cargo' => [
                    'id' => $empleado->cargo->id,
                    'nombre' => $empleado->cargo->nombre,
                ],
                'id_user' => $empleado->id_user,
            ];
        });

        return $result;
    }

    public function getId($id)
    {
        $empleado = Empleado::with([
                'area:id,nombre',
                'horarioSemanal:id,descripcion',
                'cargo:id,nombre'
            ])
            ->select('id', 'nombres', 'apellidos', 'telefono', 'dni', 'id_area', 'id_horario_semanal', 'id_cargo', 'id_user')
            ->where('id', $id)
            ->where('is_delete', 0)
            ->first();

        if (!$empleado) {
            return ['message' => 'User not found'];
        }

        return [
            'id' => $empleado->id,
            'nombres' => $empleado->nombres,
            'apellidos' => $empleado->apellidos,
            'telefono' => $empleado->telefono,
            'dni' => $empleado->dni,
            'area' => [
                'id' => $empleado->area->id,
                'nombre' => $empleado->area->nombre,
            ],
            'horario_semanal' => [
                'id' => $empleado->horarioSemanal->id,
                'descripcion' => $empleado->horarioSemanal->descripcion,
            ],
            'cargo' => [
                'id' => $empleado->cargo->id,
                'nombre' => $empleado->cargo->nombre,
            ],
            'id_user' => $empleado->id_user,
        ];
    }

    public function update(Request $request)
    {
        // Validar los datos del formulario, si es necesario
        $validatedData = $request->validate([
            'nombres' => ['required', 'string', 'max:255'],
            'apellidos' => ['required', 'string', 'max:255'],
            'telefono' => ['nullable', 'string', 'max:9'], // Hacer que el teléfono sea opcional (nullable)
            'dni' => ['required', 'string', 'max:8'],
            'id_area' => ['required', 'integer', 'max:20'],
            'id_horario_semanal' => ['required', 'integer', 'max:20'],
            'id_cargo' => ['nullable', 'integer', 'max:20'],
            'id_user' => ['required', 'integer', 'max:20'],
        ]);

        // Buscar el área existente por ID
        $empleado = Empleado::find($request->id);

        if (!$empleado) {
            // Devolver una respuesta en caso de que el área no sea encontrada
            return 'empleado no encontrada';
        }
        // Llamar a buildData para asignar los datos proporcionados
        $empleado = $this->buildData($empleado, $validatedData);

        // Guardar los cambios en la base de datos
        $empleado->save();

        // Devolver una respuesta exitosa
        return $empleado;
    }

    public function delete($id)
    {

       // Buscar el usuario por su ID
        $empleado = Empleado::find($id);

        // Verificar si el usuario existe
        if ($empleado) {
            // Eliminar el registro correspondiente en la tabla empleado
            Empleado::where('id', $id)->update([
                'is_delete' => true
            ]);

            return true;
        } else {
            return false;
        }
    
    }

    private function buildData(Empleado $empleado, array $data): Empleado
    {
        $empleado->nombres = $data['nombres'];
        $empleado->apellidos = $data['apellidos'];
        $empleado->telefono = $data['telefono'];
        $empleado->dni = $data['dni'];
        $empleado->id_area = $data['id_area'];
        $empleado->id_horario_semanal = $data['id_horario_semanal'];
        $empleado->id_cargo = $data['id_cargo'];
        $empleado->id_user = $data['id_user'];

        return $empleado;
    }

} 