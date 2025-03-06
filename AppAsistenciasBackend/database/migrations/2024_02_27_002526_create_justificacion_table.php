<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateJustificacionTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('justificacion', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('titulo', 255);
            $table->string('descripcion', 255);
            $table->string('razon', 255);
            $table->string('estado', 255);
            $table->string('comentario', 255)->nullable();
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
        Schema::dropIfExists('justificacion');
    }
}
