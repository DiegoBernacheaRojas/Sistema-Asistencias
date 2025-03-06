<?php

namespace App\Http\Controllers\Auth;

use Illuminate\Http\Request;
use App\Services\Auth\AuthEmployeeService;
use Illuminate\Support\Facades\Auth;
use Exception;
use Illuminate\Auth\AuthenticationException;
use App\Http\Controllers\Controller;

class AuthEmployeeController extends Controller
{
    private $Service;

    public function __construct(AuthEmployeeService $Service)
    {
        $this->Service = $Service;
    }

    public function loginEmpleado(Request $request)
    {
        try {
            $request->validate([
                'email' => 'required|email',
                'password' => 'required',
            ]);

            $credentials = $request->only('email', 'password');
            if (Auth::attempt($credentials)) {
                $user = $request->user();
                if ($user->role == "employee") {
                    $result = $this->Service->loginEmpleado($user);

                    return response()->json([
                        'success' => $result['success'],
                        'message' => $result['message'],
                        'token' => $result['token'] ?? null
                    ], $result['status']);
                } else {
                    return response()->json([
                        'success' => false,
                        'message' => 'Su cuenta no es de empleado.',
                    ], 403);
                }
            } else {
                return response()->json([
                    'success' => false,
                    'message' => 'Credenciales inválidas.',
                ], 401);
            }
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'Error al intentar loguear: ' . $e->getMessage()
            ], 500);
        }
    }

    public function logoutEmpleado()
    {
        try {
            $user = Auth::user();
            $this->Service->logoutEmpleado($user);

            return response()->json([
                'success' => true,
                'message' => 'Se ha cerrado sesión exitosamente.'
            ], 200);
        } catch (AuthenticationException $e) {
            return response()->json([
                'success' => false,
                'message' => 'No estás autenticado para acceder a esta funcionalidad.'
            ], 401);
        }
    }
}
