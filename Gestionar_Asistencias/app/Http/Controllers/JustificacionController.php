<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Services\JustificacionService;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Controller;
use App\Request\JustificacionRequest;
use App\Models\Empleado;
use Exception;

class JustificacionController extends Controller
{
    private $Service;

    public function __construct(JustificacionService $Service)
    {
        $this->Service = $Service;
    }

    public function registerAusencia(Request $request)
    {
        try {

            $record = $this->Service->registerAusencia($request);
            if (is_string($record)) {
                return response()->json([
                    'success' => true,
                    'message' => $record,
                ], 200);
            }
            return response()->json([
                'success' => true,
                'message' => 'Se envio la justificación de la ausencia.',
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se envio la justificacion de ausencia' . $e
            ], 500);
        }
    }

    public function register(JustificacionRequest $request)
    {
        try {

            $record = $this->Service->register($request);
            return response()->json([
                'success' => true,
                'message' => 'Se envio la justificación.',
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se envio la justificacion' . $e
            ], 500);
        }
    }

    public function historial()
    {
        try {

            $record = $this->Service->historial();
            return response()->json([
                'success' => true,
                'message' => 'Se obtuvo el historial correctamente.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se obtuvo el historial.' . $e
            ], 500);
        }
    }

    public function detalleHistorial(int $id)
    {
        try {

            $record = $this->Service->detalleHistorial($id);
            return response()->json([
                'success' => true,
                'message' => 'Se obtuvo el detalle de historial correctamente.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se obtuvo el detalle de historial.' . $e
            ], 500);
        }
    }

    public function getAllDay()
    {
        try {

            $record = $this->Service->getAllDay();
            return response()->json([
                'success' => true,
                'message' => 'Se obtuvo las justificaciones del día.',
                'data' => $record
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se obtuvo las justificaciones del día.' . $e
            ], 500);
        }
    }

    public function update(Request $request)
    {
        try {

            $record = $this->Service->update($request);
            return response()->json([
                'success' => true,
                'message' => 'Se evaluo la justificacion.',
            ], 200);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No puedo evaluar la justificacion' . $e
            ], 500);
        }
    }
}
