<?php

namespace App\Services\Auth;

use App\Models\Empleado;
use Illuminate\Support\Facades\Auth;
use App\Models\User;

class AuthEmployeeService
{
    public function loginEmpleado(User $user)
    {
        // Verificar si el empleado vinculado al usuario está eliminado
        $empleado = Empleado::where('id_user', $user->id)->first();

        if ($empleado && $empleado->is_delete == 1) {
            return [
                'success' => false,
                'message' => 'El empleado vinculado a este usuario está eliminado.',
                'status' => 403
            ];
        }

        // Si el empleado no está eliminado, continuar con la creación del token
        $token = $user->createToken('Personal Access Token')->plainTextToken;
        $user->api_token = $token;
        $user->save();

        return [
            'success' => true,
            'message' => 'Token generado exitosamente.',
            'token' => $token,
            'status' => 200
        ];
    }

    public function logoutEmpleado(User $user)
    {
        $user->tokens()->delete(); // Corregido
        $user->api_token = null;
        $user->save();
    }
}
