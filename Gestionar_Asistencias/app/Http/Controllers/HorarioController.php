<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Services\HorarioService;
use App\Http\Controllers\Controller;
use App\Http\Resources\HorarioSemanalResource;
use App\Request\JustificacionRequest;
use App\Models\Empleado;
use Exception;

class HorarioController extends Controller
{
    private $Service;
    
    public function __construct(HorarioService $service)
    {
        $this->Service = $service;
    }

    public function getHorarioEmployee()
    {
        try {

            $records=$this->Service->getHorarioSemanal();
            
            return response()->json([
                'success' => true,
                'message' => 'Horario del Empleado',
                'horarioSemanal' => $records,
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se mostraron los datos del empleado.' . $e->getMessage(),
            ], 500);
        }
    }

    public function register(Request $request){
        try {

            $record = $this->Service->register($request);
            return response()->json([
                'success' => true,
                'message' => 'horario registrado.',
                'data'=>$record
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo registrar el horario.'.$e
            ], 500);
        }
    }

    public function getAll(){
        try {

            $records = $this->Service->getAll();
            return response()->json([
                'success' => true,
                'data'=>$records
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR'.$e
            ], 500);
        }
    }

    public function getId($id){
        try {

            $records = $this->Service->getId($id);
            return response()->json([
                'success' => true,
                'data'=>$records
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR'.$e
            ], 500);
        }
    }

    public function update(Request $request){
        try {

            $records = $this->Service->update($request);
            return response()->json([
                'success' => true,
                'data'=>$records
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR'.$e
            ], 500);
        }
    }

    public function delete($id){
        try {

            $records = $this->Service->delete($id);
            return response()->json([
                'success' => $records,
                'message'=>'usuario eliminada correctamente.'
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR'.$e
            ], 500);
        }
    }
}