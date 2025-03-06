<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Services\EmpleadoService;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Controller;
use App\Services\UserService;
use Exception;

class UserController extends Controller
{
    private $Service;
    
    public function __construct(UserService $Service)
    {
        $this->Service = $Service;
    }

    public function create(Request $request){
        try {

            $record = $this->Service->create($request);
            return response()->json([
                'success' => true,
                'message' => 'usuario creado.',
                'data'=>$record
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo crear el usuario.'.$e
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

    public function getUnusedUsers(){
        try {

            $records = $this->Service->getUnusedUsers();
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