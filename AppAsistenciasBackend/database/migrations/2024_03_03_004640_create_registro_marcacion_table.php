<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateRegistroMarcacionTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('registro_marcacion', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->text('evidencia')->nullable();
            $table->date('fecha');
            $table->time('hora')->nullable();
            $table->string('estado', 255);
            $table->string('tipo', 255);
            $table->unsignedBigInteger('id_justificacion')->nullable();
            $table->unsignedBigInteger('id_empleado');
            $table->foreign('id_justificacion')->references('id')->on('justificacion');
            $table->foreign('id_empleado')->references('id')->on('empleado');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('registro_marcacion');
    }
}
