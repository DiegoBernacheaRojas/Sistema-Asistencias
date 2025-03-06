<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::prefix('auth')->group(function () {
    Route::post('/login', 'App\Http\Controllers\AuthController@login')->name('login');
    Route::middleware('auth:sanctum')->post('/logout', 'App\Http\Controllers\AuthController@logout');
});
