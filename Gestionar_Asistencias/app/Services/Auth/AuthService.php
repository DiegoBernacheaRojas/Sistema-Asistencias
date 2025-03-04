<?php
namespace App\Services\Auth;

use Illuminate\Support\Facades\Auth;
use App\Models\User;

class AuthService
{
    public function login(User $user)
    {
        $token = $user->createToken('Personal Access Token')->plainTextToken;
        $user->api_token = $token;
        $user->save();
        return $token;
    }

    public function logout(User $user)
    {
        $user->tokens()->delete(); // Corregido
    }
} 