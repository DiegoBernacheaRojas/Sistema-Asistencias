<?php

namespace App\Http\Controllers\Auth;

use Illuminate\Http\Request;
use App\Services\Auth\AuthService;
use Illuminate\Support\Facades\Auth;
use Exception;
use Illuminate\Auth\AuthenticationException;
use App\Http\Controllers\Controller;

class AuthController extends Controller
{
    private $authService;

    public function __construct(AuthService $authService)
    {
        $this->authService = $authService;
    }

    public function login(Request $request)
    {
        
        try {
            $request->validate([
                'email' => 'required|email',
                'password' => 'required',
            ]);
    
            $credentials = $request->only('email', 'password');
            if (Auth::attempt($credentials)) {

                $user = $request->user();

                if($user->role=="admin"){
                    $token = $this->authService->login($user);

                    return response()->json([
                        'token'=>$token,
                        'success' => true,
                        'message' => 'Se completo el logueo.'
                    ]);
                }else {
                    // Si la autenticación falla, devuelve un mensaje de error
                    return response()->json([
                        'success' => false,
                        'message' => 'Su cuenta no es de administrador.',
                    ], 200);
                }
            }else {
                // Si la autenticación falla, devuelve un mensaje de error
                return response()->json([
                    'success' => false,
                    'message' => 'Credenciales inválidas.',
                ], 200);
            }
        } catch (Exception $e) {

            return response()->json([
                'success' => false,
                'message' => 'Error al intentar loguear.'
            ], 401);
        }
    }
    public function logout()
    {
        try {
            $user = Auth::user();
            $this->authService->logout($user);
            
            return response()->json([
                'message' => 'Se ha cerrado sesión exitosamente.',
                'user' => $user
            ], 200);
        } catch (AuthenticationException $e) {
            return response()->json([
                'success' => false,
                'message' => 'No estás autenticado para acceder a esta funcionalidad.'
            ], 401);
        }
    }
}