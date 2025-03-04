<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\DB;

class InsertUserAdmin extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        DB::table('users')->insert([
            'name' => 'Admin Geor Asistencias',
            'email' => 'Geor@Asistencias.com',
            'password' => Hash::make('Geor2024@Admin.'),
            'role' => 'admin', // Agrega el campo role y establece el valor como 'admin'
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
        // Aquí puedes deshacer el registro del usuario si es necesario
        DB::table('users')->where('email', 'Geor@Asistencias.com')->delete();
    }
}
