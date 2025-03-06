<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use Illuminate\Support\Facades\DB;

class InsertCargo extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {

        $cargoId = DB::table('cargo')->insertGetId([
            'nombre' => 'Desarrollador Backend',
            'descripcion' => 'Desarrollador de software encrgado de la parte backend',
            'created_at' => now(),
            'updated_at' => now(),
        ]);
        DB::table('empleado')
            ->where('id', 1)
            ->update(['id_cargo' => $cargoId]);
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        //
    }
}
