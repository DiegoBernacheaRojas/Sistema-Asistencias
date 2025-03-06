<?php
namespace App\Services;


use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use GuzzleHttp\Client;
use App\Models\QRcode; // Asume que tienes un modelo QRcode


class QRcodeService
{
    public function generarCodigoQR($id)
    {
        // Generar texto aleatorio para el código QR
        $randomText = $this->generateRandomString();
    
        // Buscar un registro existente para el usuario actual
        $qrcode = QRcode::where('id_user', $id)->first();
    
        if ($qrcode) {
            // Si existe un registro, actualizar el texto del código QR
            $qrcode->codigo_qr = $randomText;
            $qrcode->save();
        } else {
            // Si no existe un registro, crear uno nuevo
            $qrcode = new QRcode();
            $qrcode->id_user = $id;
            $qrcode->codigo_qr = $randomText;
            $qrcode->save();
        }
    
        return  'Se genero el codigo QR.';
    }

    public function mostrarCodigoQR()
    {
        $user = Auth::user();
        $qrcode = QRcode::where('id_user', $user->id)->first();
        // Configurar la solicitud a la API externa para generar el código QR

        return $qrcode->codigo_qr;
    }

    public function  verificarCodigoQR(Request $request){
        $user = Auth::user();
        $qrcode = QRcode::where('id_user', $user->id)->first();
        // Configurar la solicitud a la API externa para generar el código QR
        if($request->code_qr==$qrcode->codigo_qr){
            return (object) [
                'success' => true,
                'message' => 'El codigo QR es correcto.'
            ]; 
        }
        return (object) [
            'success' => false,
            'message' => 'No es el QR correcto, recarga la pagina.'
        ]; 
    }

    private function generateRandomString($length = 10)
    {
        $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $charactersLength = strlen($characters);
        $randomString = '';
        
        for ($i = 0; $i < $length; $i++) {
            $randomString .= $characters[rand(0, $charactersLength - 1)];
        }
        
        return $randomString;
    }
}