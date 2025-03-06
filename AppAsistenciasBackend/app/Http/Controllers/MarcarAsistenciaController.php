<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Services\MarcarAsistenciaService;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Controller;
use App\Models\Empleado;
use Exception;

class MarcarAsistenciaController extends Controller
{
    private $Service;

    public function __construct(MarcarAsistenciaService $Service)
    {
        $this->Service = $Service;
    }

    public function register(Request $request)
    {
        try {

            $record = $this->Service->register($request);

            if (is_string($record)) {
                return response()->json([
                    'success' => false,
                    'message' => $record,
                ], 200);
            } else {
                return response()->json([
                    'success' => true,
                    'message' => 'Se marco su asistencia.',
                    'data' => $record
                ], 200);
            }
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se marco su asistencia' . $e
            ], 500);
        }
    }

    public function updateEvidencia(Request $request)
    {
        try {

            $this->Service->updateEvidencia($request);
            return response()->json([
                'success' => true,
                'message' => 'Se subio su evidencia.',
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo subir su evidencia' . $e
            ], 500);
        }
    }

    public function getId()
    {
        try {

            $record = $this->Service->getId();
            return response()->json([
                'success' => true,
                'message' => 'Se mostro los registros.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo mostrar los registros' . $e
            ], 500);
        }
    }

    public function getForArea($id_area)
    {
        try {

            $record = $this->Service->getForArea($id_area);
            return response()->json([
                'success' => true,
                'message' => 'Se mostro los registros por área.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo mostrar los registros por área' . $e
            ], 500);
        }
    }

    public function getallday()
    {
        try {

            $record = $this->Service->getallday();
            return response()->json([
                'success' => true,
                'message' => 'Se mostro los registros.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo mostrar los registros' . $e
            ], 500);
        }
    }

    public function getTopMensual()
    {
        try {

            $record = $this->Service->getTopMensual();
            return response()->json([
                'success' => true,
                'message' => 'Se mostro el top.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo mostrar el top' . $e
            ], 500);
        }
    }

    public function getHorasTrabajadas($fechainicio,$fechafin)
    {
        try {

            $record = $this->Service->getHorasTrabajadas($fechainicio,$fechafin);
            return response()->json([
                'success' => true,
                'message' => 'Se mostro la relacion de horas trabajadas.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo mostrar la relacion de horas trabajadas' . $e
            ], 500);
        }
    }

    public function getTendencia()
    {
        try {

            $record = $this->Service->getTendencia();
            return response()->json([
                'success' => true,
                'message' => 'Se mostro las tendencias del mes.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo mostrar las tendencias del mes' . $e
            ], 500);
        }
    }

    public function getHistorial()
    {
        try {

            $record = $this->Service->getHistorial();
            return response()->json([
                'success' => true,
                'message' => 'Se mostro el historial.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo mostrar el historial' . $e
            ], 500);
        }
    }
}
