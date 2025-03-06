<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use Illuminate\Support\Facades\DB;

class InsertArea extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        $areaId=DB::table('area')->insertGetId([
            'image' => '/uploads/e-commerce.jpg',
            'nombre' => 'Backend Ecommerce',
            'descripcion' => 'Proyecto de Ecommerce',
            'created_at' => now(),
            'updated_at' => now(),
        ]);
        DB::table('empleado')
            ->where('id', 1)
            ->update(['id_area' => $areaId]);
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
