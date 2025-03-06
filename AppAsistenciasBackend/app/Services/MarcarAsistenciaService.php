<?php

namespace App\Services;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\Feriado;
use App\Models\Empleado;
use App\Models\HorarioSemanal;
use App\Models\RegistroMarcacion;
use App\Models\Vacaciones;
use Carbon\Carbon;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Log;

class MarcarAsistenciaService
{
    public function register(Request $request)
    {
        $user = Auth::user();
        $empleado = Empleado::where('id_user', $user->id)->first();
        $records = HorarioSemanal::with('horarioDia')->where('id', $empleado->id_horario_semanal)->first();
        $horarioDias = $records->horarioDia;

        $diaLaborable = false;
        Carbon::setLocale('es');

        $diaActual = ucfirst(Carbon::now()->setTimezone('America/Lima')->isoFormat('dddd'));
        Carbon::setLocale('es');
        if (!$request->ausente) {
            foreach ($horarioDias as $horarioDia) {

                if ($horarioDia->dia == $diaActual) {
                    $diaLaborable = true;
                    $fecha = Carbon::now()->setTimezone('America/Lima')->toDateString();
                    $hora = Carbon::now()->setTimezone('America/Lima')->toTimeString();
                    $feriado = Feriado::where('fecha', $fecha)->first();
                    if (!$feriado) {
                        $vacacion = Vacaciones::where('id_empleado', $empleado->id)
                            ->whereDate('fecha_inicio', '<=', $fecha)
                            ->whereDate('fecha_fin', '>=', $fecha)
                            ->first();
                        if (!$vacacion) {
                            $tipo = "";
                            $estado = "";
                            $registroMarcacionFecha = RegistroMarcacion::where('fecha', $fecha)->where('id_empleado', $empleado->id)->get();
                            if ($registroMarcacionFecha->count() == 2) {
                                return 'Ya marco la entrada y salida de este día.';
                            } else {
                                if ($registroMarcacionFecha->count() == 1) {

                                    $tipo = "SALIDA";
                                    if ($hora >= $horarioDia->hora_salida) {
                                        $estado = "SALIDA ";
                                    } else {
                                        $estado = "ANTES";
                                    }
                                } else {
                                    $tipo = "ENTRADA";
                                    if ($hora <= $horarioDia->hora_entrada) {
                                        $estado = "PUNTUAL";
                                    } else {
                                        $estado = "TARDE";
                                    }
                                }
                            }


                            $request->merge(['tipo' => $tipo]);
                            $request->merge(['estado' => $estado]);
                            $request->merge(['id_empleado' => $empleado->id]);
                            $request->merge(['fecha' => $fecha]);
                            $request->merge(['hora' => $hora]);
                            $request->merge(['id_justificacion' => null]);
                            $request->merge(['evidencia' => null]);
                            $validatedData = $request->validate([
                                'evidencia' => ['nullable', 'string'],
                                'estado' => ['required', 'string', 'max:255'],
                                'hora' => ['required', 'string', 'max:255'],
                                'fecha' => ['required', 'string', 'max:255'],
                                'tipo' => ['required', 'string', 'max:255'],
                                'id_justificacion' => ['nullable', 'numeric'],
                                'id_empleado' => ['required', 'numeric']
                            ]);
                            // Crear y guardar el registro de marcación
                            $registroMarcacion = new RegistroMarcacion();
                            $registroMarcacion = $this->buildData($registroMarcacion, $validatedData);
                            $registroMarcacion->save();

                            return $registroMarcacion;
                        } else {
                            return "Estas de vacaciones, no puede marcar asistencia.";
                        }
                    } else {
                        return "Hoy es festivo o no laborables, no puede marcar asistencia.";
                    }
                }
            }
            if (!$diaLaborable) {
                return "Hoy no hay horario de trabajo, no puede marcar asistencia.";
            }
        } else {

            $fecha = Carbon::now()->setTimezone('America/Lima')->toDateString();
            $tipo = "";
            $registroMarcacionFecha = RegistroMarcacion::where('fecha', $fecha)->where('id_empleado', $empleado->id)->get();
            if ($registroMarcacionFecha->count() == 0) {
                for ($i = 0; 2 > $i; $i++) {
                    if ($i == 0) {
                        $tipo = "ENTRADA";
                    } else {
                        $tipo = "SALIDA";
                    }

                    $request->merge(['tipo' => $tipo]);
                    $request->merge(['estado' => "AUSENTE"]);
                    $request->merge(['id_empleado' => $empleado->id]);
                    $request->merge(['fecha' => $fecha]);
                    $request->merge(['hora' => null]);
                    $request->merge(['id_justificacion' => null]);
                    $request->merge(['evidencia' => null]);
                    $validatedData = $request->validate([
                        'evidencia' => ['nullable', 'string'],
                        'estado' => ['required', 'string', 'max:255'],
                        'hora' => ['nullable', 'string', 'max:255'],
                        'fecha' => ['required', 'string', 'max:255'],
                        'tipo' => ['required', 'string', 'max:255'],
                        'id_justificacion' => ['nullable', 'numeric'],
                        'id_empleado' => ['required', 'numeric']
                    ]);
                    $registroMarcacion = new RegistroMarcacion();
                    $registroMarcacion = $this->buildData($registroMarcacion, $validatedData);
                    $registroMarcacion->save();
                }
                return (object) [
                    'success' => true,
                    'message' => 'Se registro la ausensia.'
                ];
            } else {
                return (object) [
                    'success' => false,
                    'message' => 'Ya se hizo un registro de asistencia, no puede registrar una ausencia.'
                ];
            }
        }
    }

    public function updateIdJustificacionAusencia(int $idJustificacion)
    {

        $user = Auth::user();
        $empleado = Empleado::where('id_user', $user->id)->first();
        // Obtener la fecha actual
        $fecha = Carbon::now()->setTimezone('America/Lima')->toDateString();

        // Buscar y actualizar los registros
        RegistroMarcacion::where('fecha', $fecha)->where('id_empleado', $empleado->id)
            ->update(['id_justificacion' => $idJustificacion]);
    }

    public function updateIdJustificacion(int $idJustificacion)
    {
        // Obtener la fecha actual
        $user = Auth::user();
        $empleado = Empleado::where('id_user', $user->id)->first();

        RegistroMarcacion::where('id_empleado', $empleado->id)
            ->latest()
            ->first()
            ->update(['id_justificacion' => $idJustificacion]);
    }

    public function updateEvidencia(Request $request)
    {
        // Obtener la fecha actual
        $user = Auth::user();

        $empleado = Empleado::where('id_user', $user->id)->first();


        // Validar y guardar la imagen
        if ($request->has('evidencia')) {
            // Obtener la imagen codificada en Base64 de la solicitud
            $imagenCodificada = $request->input('evidencia');

            // Decodificar la imagen Base64 y guardarla en un archivo
            $imagenDecodificada = base64_decode($imagenCodificada);

            $emailFolder = explode('@', $user->email)[0]; // Obtener el email sin el texto después del arroba
            $carpetaUsuario = public_path('uploads/evidencia/' . $emailFolder . 'ID' . $user->id);

            // Crear la carpeta si no existe
            if (!File::exists($carpetaUsuario)) {
                File::makeDirectory($carpetaUsuario, 0777, true, true);
            }

            $ultimoRegistro = RegistroMarcacion::where('id_empleado', $empleado->id)->latest()
                ->first();
            $apellidos = str_replace(' ', '_', $empleado->apellidos);
            // Generar un nombre único para la imagen
            $nombreImagen = $apellidos . '_' . time() . 'Evidencia_' . $ultimoRegistro->id . '.jpg'; // Cambia la extensión según el formato de imagen que esperes

            $ruta = $carpetaUsuario . '/' . $nombreImagen;
            file_put_contents($ruta, $imagenDecodificada);

            // Si tienes un modelo Empleado relacionado con el usuario, también actualiza su campo 'foto'
            // Obtener el primer

            $ultimoRegistro->evidencia = '/' . 'uploads/evidencia/' . $emailFolder . 'ID' . $user->id . '/' . $nombreImagen;
            $ultimoRegistro->save();
        }
    }

    public function getLastRegistroMarcacion()
    {
        // Obtener la fecha actual
        $user = Auth::user();
        $empleado = Empleado::where('id_user', $user->id)->first();

        $ultimoRegistro = RegistroMarcacion::where('id_empleado', $empleado->id)->latest()
            ->first(); // Obtener el primero

        return $ultimoRegistro;
    }

    public function getId()
    {
        // Obtener el usuario autenticado
        $user = Auth::user();

        // Obtener el empleado asociado al usuario
        $empleado = Empleado::where('id_user', $user->id)->first();

        // Obtener todos los registros de marcación del empleado con las relaciones cargadas
        $registros = RegistroMarcacion::with('justificacion')
            ->where('id_empleado', $empleado->id)
            ->get()
            ->groupBy('fecha'); // Agrupar por el campo 'fecha'
        // Devolver los registros obtenidos
        return $registros;
    }
    public function getHistorial()
    {
        // Obtener todos los registros de marcación del empleado con las relaciones cargadas
        $registros = RegistroMarcacion::with('justificacion')
            ->get()
            ->groupBy('fecha'); // Agrupar por el campo 'fecha'
        // Devolver los registros obtenidos
        return $registros;
    }

    public function getallday()
    {
        $hoy = Carbon::today();

        // Obtener todos los registros de marcación del día actual con las relaciones cargadas
        $registros = RegistroMarcacion::with(['justificacion', 'empleado'])
            ->whereDate('fecha', $hoy)
            ->get();

        // Verificar si hay registros para el día de hoy
        if ($registros->isEmpty()) {
            return ['message' => 'No se encontraron registros para el día de hoy.'];
        }

        // Agrupar registros por empleado
        $registrosPorEmpleado = $registros->groupBy('id_empleado');

        // Estructurar la respuesta
        $data = $registrosPorEmpleado->map(function ($registros, $empleadoId) {
            // Obtener el empleado del primer registro
            $empleado = $registros->first()->empleado;

            // Obtener la marca de entrada y salida
            $entrada = $registros->where('tipo', 'ENTRADA')->first();
            $salida = $registros->where('tipo', 'SALIDA')->first();

            // Manejo de registros inexistentes
            $hora_entrada = $entrada ? $entrada->hora : '--:--:--';
            $estado_entrada = $entrada ? $entrada->estado : '';
            $justificacion_entrada = $entrada && $entrada->justificacion ? $entrada->justificacion->estado : '';

            $hora_salida = $salida ? $salida->hora : '--:--:--';
            $estado_salida = $salida ? $salida->estado : '';
            $justificacion_salida = $salida && $salida->justificacion ? $salida->justificacion->estado : '';

            return [
                'Foto_Empleado' => $empleado->foto ?? '', // Usar operador null coalesce para evitar errores si la foto es nula
                'Nombre_Empleado' => trim($empleado->nombres . ' ' . $empleado->apellidos) ?: 'N/A', // Evitar nombres vacíos
                'hora_entrada' => $hora_entrada,
                'estado_entrada' => $estado_entrada,
                'justificacion_entrada' => $justificacion_entrada,
                'hora_salida' => $hora_salida,
                'estado_salida' => $estado_salida,
                'justificacion_salida' => $justificacion_salida,
            ];
        })->values();

        return $data;
    }

    public function getForArea($id_area)
    {
        // Obtener todos los empleados del área específica
        $empleados = Empleado::where('id_area', $id_area)
            ->where('is_delete', '!=', 1)
            ->get();

        $resultado = [];

        // Obtener el mes y año actual
        $mesActual = date('m');  // Mes en formato numérico (01 - 12)
        $añoActual = date('Y');  // Año actual

        foreach ($empleados as $empleado) {
            // Inicializar el array para los conteos de cada estado
            $conteoEstados = [
                'empleado' => $empleado->nombres,  // Asumiendo que el campo 'nombres' contiene el nombre del empleado
                'PUNTUAL' => 0,
                'TARDE' => 0,
                'SALIDA' => 0,
                'ANTES' => 0,
                'JUSTIFICADO' => 0,
                'AUSENTE' => 0,
            ];

            // Obtener los registros de marcación del empleado filtrados por el mes y año actual
            $registros = RegistroMarcacion::where('id_empleado', $empleado->id)
                ->whereMonth('fecha', $mesActual)  // Filtrar por mes
                ->whereYear('fecha', $añoActual)   // Filtrar por año
                ->get();

            // Contar los diferentes estados
            foreach ($registros as $registro) {
                $estado = strtoupper($registro->estado);

                // Incrementar el conteo del estado correspondiente si existe en el array
                if (array_key_exists($estado, $conteoEstados)) {
                    $conteoEstados[$estado]++;
                }
            }

            // Añadir el resultado al array final
            $resultado[] = $conteoEstados;
        }

        // Retornar el resultado como JSON
        return $resultado;
    }

    public function getTopMensual()
    {
        // Obtener el mes y año actual
        $mesActual = date('m');
        $añoActual = date('Y');

        // Obtener todos los empleados que no han sido eliminados
        $empleados = Empleado::where('is_delete', 0)->get();

        // Crear un array para almacenar los puntos de los empleados
        $empleadosPuntos = [];

        // Contabilizar puntos para cada empleado solo en el mes actual
        foreach ($empleados as $empleado) {
            $puntosPositivos = RegistroMarcacion::where('id_empleado', $empleado->id)
                ->whereIn('estado', ['PUNTUAL', 'SALIDA'])
                ->whereMonth('fecha', $mesActual)
                ->whereYear('fecha', $añoActual)
                ->count();

            // Guardar el nombre del empleado y sus puntos
            $empleadosPuntos[] = [
                'nombre' => $empleado->nombres,
                'posipoint' => $puntosPositivos
            ];
        }

        // Ordenar el array de empleados por puntos positivos (descendente)
        usort($empleadosPuntos, function ($a, $b) {
            return $b['posipoint'] - $a['posipoint'];
        });

        // Seleccionar los 5 mejores
        $top5 = array_slice($empleadosPuntos, 0, 5);

        // Seleccionar los 5 peores (sin incluir a los del top 5)
        $bottom5 = array_slice($empleadosPuntos, -5, 5);

        // Eliminar duplicados en el array bottom5 si algún empleado ya está en el top5
        $bottom5 = array_udiff($bottom5, $top5, function ($a, $b) {
            return strcmp($a['nombre'], $b['nombre']);
        });

        // Combinar los 5 mejores y los 5 peores
        $top10 = array_merge($top5, $bottom5);

        // Si hay menos de 10 empleados, rellenar con guiones
        while (count($top10) < 10) {
            $top10[] = [
                'nombre' => '-',
                'posipoint' => '-'
            ];
        }

        // Formatear el resultado en JSON
        $resultado = [];
        for ($i = 0; $i < 10; $i++) {
            $resultado["top" . ($i + 1)] = isset($top10[$i]) ? $top10[$i] : [
                'nombre' => '-',
                'posipoint' => '-'
            ];
        }

        // Retornar el resultado como JSON
        return $resultado;
    }

    public function getHorasTrabajadas($fechainicio, $fechafin)
    {
        // Obtener todos los empleados no eliminados
        $empleados = Empleado::with(['area:id,nombre'])->where('is_delete', 0)->get();

        $resultados = [];

        foreach ($empleados as $empleado) {


            // Obtener los registros de marcación del empleado dentro del rango de fechas
            $registros = RegistroMarcacion::where('id_empleado', $empleado->id)
                ->whereBetween('fecha', [$fechainicio, $fechafin])
                ->whereNotNull('hora')
                ->get()
                ->groupBy('fecha');

            $totalHoras = 0;

            foreach ($registros as $dia) {
                if (count($dia) === 2) {
                    // Obtener la hora de entrada y salida
                    $horaEntrada = $dia[0]->hora;
                    $horaSalida = $dia[1]->hora;

                    if ($horaEntrada && $horaSalida) {
                        // Calcular la diferencia en horas
                        $horasTrabajadas = (strtotime($horaSalida) - strtotime($horaEntrada)) / 3600;
                        $totalHoras += $horasTrabajadas;
                    }
                }
            }
            $nombres = $empleado->nombres;
            $apellidos = $empleado->apellidos;
            $nombre_completo = $nombres . ' ' . $apellidos;;
            $resultados[] = [
                'id' => $empleado->id,
                'foto' => $empleado->foto,
                'area' => $empleado->area->nombre,
                'nombre' => $nombre_completo,
                'horas' => round($totalHoras, 2),
            ];
        }

        return $resultados;
    }

    public function getTendencia()
    {
        // Obtener el año actual
        $currentYear = date('Y');

        // Inicializar el array para los conteos mensuales
        $conteoMensual = [];

        // Inicializar los meses del año
        for ($mes = 1; $mes <= 12; $mes++) {
            // Inicializar el array para los conteos de cada estado para este mes
            $conteoEstados = [
                'PUNTUAL' => 0,
                'TARDE' => 0,
                'SALIDA' => 0,
                'ANTES' => 0,
                'JUSTIFICADO' => 0,
                'AUSENTE' => 0,
            ];

            // Obtener los registros de marcación para el mes y año actual
            $registros = RegistroMarcacion::whereYear('fecha', $currentYear)
                ->whereMonth('fecha', $mes)
                ->get();

            // Contar los diferentes estados
            foreach ($registros as $registro) {
                $estado = strtoupper($registro->estado);

                // Incrementar el conteo del estado correspondiente si existe en el array
                if (array_key_exists($estado, $conteoEstados)) {
                    $conteoEstados[$estado]++;
                }
            }

            // Guardar el conteo de este mes
            $conteoMensual[date('F', mktime(0, 0, 0, $mes, 10))] = $conteoEstados;
        }


        // Retornar el resultado como JSON
        return $conteoMensual;
    }

    private function buildData(RegistroMarcacion $registroMarcacion, array $data): RegistroMarcacion
    {
        $registroMarcacion->evidencia = $data['evidencia'];
        $registroMarcacion->estado = $data['estado'];
        $registroMarcacion->hora = $data['hora'];
        $registroMarcacion->fecha = $data['fecha'];
        $registroMarcacion->tipo = $data['tipo'];
        $registroMarcacion->id_justificacion = $data['id_justificacion'];
        $registroMarcacion->id_empleado = $data['id_empleado'];

        return $registroMarcacion;
    }
}
