<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Services\QRcodeService;
use App\Http\Controllers\Controller;
use GuzzleHttp\Client;

use Exception;

class QRcodeController extends Controller
{
    private $Service;
    
    public function __construct(QRcodeService $Service)
    {
        $this->Service = $Service;
    }

    // public function generarCodigoQR(){
    //     try {
    //         $record=$this->Service->generarCodigoQR();
    //         return response()->json([
    //             'success' => true,
    //             'message' => $record
    //         ], 200);
            
    //     } catch (Exception $e) {
    //         return response()->json([
    //             'success' => false,
    //             'message' => 'ERROR, No se genero el codigo QR'
    //         ], 500);
    //     }
    // }

    public function verificarCodigoQR(Request $request){
        try {
            $record=$this->Service->verificarCodigoQR($request);
            return response()->json([
            'success' => $record->success,
            'message' => $record->message
            ], 200);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se pudo hacer la verificacion'.$e
            ], 500);
        }
    }

    public function mostrarCodigoQR(){
        try {
            $record=$this->Service->mostrarCodigoQR();

            // $client = new Client();
            // $response = $client->post('https://api.qr-code-generator.com/v1/create?access-token=hGp0LyZPfMpyKVNkMIMSoie82_eu4ynnOfa9E1LuT2WXFHnyE-UDqK9BzOxTuD_Z', [
            //     'headers' => [
            //         'Content-Type' => 'application/json',
            //         'Accept' => 'application/json',
            //     ],
            //     'json' => [
            //         'frame_name' => 'no-frame',
            //         'qr_code_text' => $record,
            //         'image_format' => 'PNG', 
            //         'qr_code_logo' => 'scan-me-square',
            //         'frame_color' => '#0576F9',
            //         'image_width' => 300
            //     ],
            // ]);

            // $body = $response->getBody();
            // $imageContent = $body->getContents();

            // return response($imageContent)
            // ->header('Content-Type', 'image/png');

            $qrUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=350x350&data=' . urlencode($record);

            // Redirigir directamente a la URL del QR
            return redirect()->away($qrUrl);
            
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'ERROR, No se genero el codigo QR'.$e
            ], 500);
        }
    }
}