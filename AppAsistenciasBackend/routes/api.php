<?php

use App\Http\Controllers\AreaController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Auth\AuthController;
use App\Http\Controllers\Auth\AuthEmployeeController;
use App\Http\Controllers\CargoController;
use App\Http\Controllers\JustificacionController;
use App\Http\Controllers\EmpleadoController;
use App\Http\Controllers\FeriadoController;
use App\Http\Controllers\HorarioController;
use App\Http\Controllers\MarcarAsistenciaController;
use App\Http\Controllers\QRcodeController;
use App\Http\Controllers\UserController;
use App\Http\Middleware\AdminMiddleware;
use App\Http\Middleware\EmployeeMiddleware;
use App\Models\Empleado;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

// Public routes of authtication

Route::post('/employee/login', [AuthEmployeeController::class, 'loginEmpleado']);

Route::post('/admin/login', [AuthController::class, 'login']);

// Protected routes of product and logout
Route::middleware('auth:sanctum')->group( function () {
    
    Route::middleware('employee')
    ->prefix('employee')
    ->group(function () {
        Route::post('logout', [AuthEmployeeController::class, 'logoutEmpleado']);
        Route::get('getEmployee', [EmpleadoController::class, 'getEmployee']);
        Route::get('getHorarioEmployee', [HorarioController::class, 'getHorarioEmployee']);
        Route::post('updateFotoPerfil', [EmpleadoController::class, 'updateFotoPerfil']);
        Route::post('updateFacialData', [EmpleadoController::class, 'updateFacialData']);
        Route::post('verificarFacialData', [EmpleadoController::class, 'verificarFacialData']);

        Route::prefix('justificacion')
        ->group(function () {
            Route::post('register', [JustificacionController::class, 'register']);
            Route::post('registerAusencia', [JustificacionController::class, 'registerAusencia']);
            Route::get('historial', [JustificacionController::class, 'historial']);
            Route::get('historial/{id}', [JustificacionController::class, 'detalleHistorial']);
        });

        Route::prefix('asistencia')
        ->group(function () {
            Route::post('register', [MarcarAsistenciaController::class, 'register']);
            Route::post('evidencia', [MarcarAsistenciaController::class, 'updateEvidencia']);
            Route::get('getId', [MarcarAsistenciaController::class, 'GetId']);
        });

        Route::prefix('codeqr')
        ->group(function () {
            Route::get('mostrar', [QRcodeController::class, 'mostrarCodigoQR']);
            Route::post('verificar', [QRcodeController::class, 'verificarCodigoQR']);
        });

    });

    Route::middleware('admin')
    ->prefix('admin')
    ->group(function () {
        Route::post('logout', [AuthController::class, 'logout']);
        Route::get('getEmpleadosPresentes', [EmpleadoController::class, 'getEmpleadosPresentes']);

        Route::prefix('area')
        ->group(function () {
            Route::get('getall', [AreaController::class, 'getAll']);
            Route::get('get/{id}', [AreaController::class, 'getId']);
            Route::post('register', [AreaController::class, 'register']);
            Route::post('update', [AreaController::class, 'update']);
            Route::post('delete/{id}', [AreaController::class, 'delete']);
        });

        Route::prefix('justificacion')
        ->group(function () {
            Route::get('getallday', [JustificacionController::class, 'getAllDay']);
            Route::get('detalle/{id}', [JustificacionController::class, 'detalleHistorial']);
            Route::post('update', [JustificacionController::class, 'update']);
        });

        Route::prefix('user')
        ->group(function () {
            Route::post('create', [UserController::class, 'create']);
            Route::get('getall', [UserController::class, 'getAll']);
            Route::get('getUnusedUsers', [UserController::class, 'getUnusedUsers']);
            Route::get('get/{id}', [UserController::class, 'getId']);
            Route::post('update', [UserController::class, 'update']);
            Route::post('delete/{id}', [UserController::class, 'delete']);

        });

        Route::prefix('employee')
        ->group(function () {
            Route::post('register', [EmpleadoController::class, 'register']);
            Route::get('getall', [EmpleadoController::class, 'getAll']);
            Route::get('get/{id}', [EmpleadoController::class, 'getId']);
            Route::post('update', [EmpleadoController::class, 'update']);
            Route::post('delete/{id}', [EmpleadoController::class, 'delete']);
        });

        Route::prefix('cargo')
        ->group(function () {
            Route::get('getall', [CargoController::class, 'getAll']);
            Route::get('get/{id}', [CargoController::class, 'getId']);
            Route::post('register', [CargoController::class, 'register']);
            Route::post('update', [CargoController::class, 'update']);
            Route::post('delete/{id}', [CargoController::class, 'delete']);
        });

        Route::prefix('feriado')
        ->group(function () {
            Route::get('getall', [FeriadoController::class, 'getAll']);
            Route::get('get/{id}', [FeriadoController::class, 'getId']);
            Route::post('register', [FeriadoController::class, 'register']);
            Route::post('update', [FeriadoController::class, 'update']);
            Route::post('delete/{id}', [FeriadoController::class, 'delete']);
        });

        Route::prefix('horario')
        ->group(function () {
            Route::get('getall', [HorarioController::class, 'getAll']);
            Route::get('get/{id}', [HorarioController::class, 'getId']);
            Route::post('register', [HorarioController::class, 'register']);
            Route::post('update', [HorarioController::class, 'update']);
            Route::post('delete/{id}', [HorarioController::class, 'delete']);
        });

        Route::prefix('asistencias')
        ->group(function () {
            Route::get('getallday', [MarcarAsistenciaController::class, 'getallday']);
            Route::get('getForArea/{idarea}', [MarcarAsistenciaController::class, 'getForArea']);
            Route::get('getTopMensual', [MarcarAsistenciaController::class, 'getTopMensual']);
            Route::get('getHorasTrabajadas/{fechainicio}/{fechafin}', [MarcarAsistenciaController::class, 'getHorasTrabajadas']);
            Route::get('getTendencia', [MarcarAsistenciaController::class, 'getTendencia']);
        });
    });
});
