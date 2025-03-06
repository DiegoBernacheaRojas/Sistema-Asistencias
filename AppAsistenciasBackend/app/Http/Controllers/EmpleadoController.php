<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Services\EmpleadoService;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Controller;
use App\Request\JustificacionRequest;
use App\Models\Empleado;
use Exception;

class EmpleadoController extends Controller
{
    private $Service;
    
    public function __construct(EmpleadoService $Service)
    {
        $this->Service = $Service;
    }

    public function getEmployee(){
        try {

            $records = $this->Service->getEmpleado();
            return response()->json([
                'success' => true,
                'message' => 'Datos del empleado.',
                'data'=>$records
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se mostraron los datos del empleado.'.$e
            ], 500);
        }
    }

    public function updateFotoPerfil(Request $request){
        try {

            $this->Service->updateFotoPerfil($request);
            
            return response()->json([
                'success' => true,
                'message' => 'se actualizo la foto de perfil',
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo actualizar la foto de perfil.' . $e->getMessage(),
            ], 500);
        }
    }
    
    public function updateFacialData(Request $request){
        try {

            $this->Service->updateFacialData($request);
            
            return response()->json([
                'success' => true,
                'message' => 'Datos faciales actualizados correctamente.',
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo actualizar los datos faciales.' . $e->getMessage(),
            ], 500);
        }
    }

    public function verificarFacialData(Request $request){
        try {
            $record=$this->Service->verificarFacialData($request);
            return response()->json([
            'success' => true,
            'verificado'=> $record->success,
            'message' => $record->message
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo hacer la verificacion'.$e
            ], 500);
        }
    }

    public function getEmpleadosPresentes(){
        try {
            $record=$this->Service->getEmpleadosPresentes();
            return response()->json([
            'success' => true,
            'message' => 'Se muestra los empleados presentes',
            'data' => $record
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo mostrar los empleados presentes'.$e
            ], 500);
        }
    }

    public function register(Request $request){
        try {
            $record=$this->Service->register($request);
            return response()->json([
            'success' => true,
            'message' => 'Empleado registrado',
            'data' => $record
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo registrar al empleado'.$e
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
                'message'=>'empleado eliminado correctamente.'
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR'.$e
            ], 500);
        }
    }
}