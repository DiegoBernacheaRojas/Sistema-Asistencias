<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\DB;

class InsertUserEmployee extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        $userId = DB::table('users')->insertGetId([
            'name' => 'Empleado Demo',
            'email' => 'Empleado@demo.com',
            'password' => Hash::make('Demo@123.'),
            'role' => 'employee', 
            'created_at' => now(),
            'updated_at' => now(),
        ]);
        DB::table('Empleado')->insert([
            'foto' => '/uploads/foto.png',
            'nombres' => 'Empleado',
            'apellidos' => 'Demo',
            'telefono' => '999888777',
            'dni' => '12345678',
            'facial_data' => null,
            'id_area' => null,
            'id_horario_semanal' => null,
            'id_cargo' => null,
            'id_user' => $userId,
            'created_at' => now(),
            'updated_at' => now(),
        ]);
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        {
            // Eliminar el empleado creado
            DB::table('Empleado')->where('dni', '12345678')->delete();
        
            // Eliminar el usuario creado
            DB::table('users')->where('email', 'Empleado@demo.com')->delete();
        }
    }
}
