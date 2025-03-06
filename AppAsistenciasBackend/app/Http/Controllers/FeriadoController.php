<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Services\AreaService;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Controller;
use App\Models\Area;
use App\Services\CargoService;
use App\Services\FeriadoService;
use Exception;

class FeriadoController extends Controller
{
    private $Service;
    
    public function __construct(FeriadoService $Service)
    {
        $this->Service = $Service;
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

    public function register(Request $request){
        try {

            $records = $this->Service->register($request);
            return response()->json([
                'success' => true,
                'id'=>$records
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
                'message'=>'Feriado eliminada correctamente.'
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR'.$e
            ], 500);
        }
    }
}