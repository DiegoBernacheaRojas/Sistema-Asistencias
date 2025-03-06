<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateEmpleadoTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('empleado', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->text('foto')->nullable();
            $table->string('nombres', 255);
            $table->string('apellidos', 255);
            $table->string('telefono', 9);
            $table->string('dni', 8);
            $table->text('facial_data')->nullable();
            $table->boolean('is_delete')->default(false);
            $table->unsignedBigInteger('id_area')->nullable();
            $table->unsignedBigInteger('id_horario_semanal')->nullable();
            $table->unsignedBigInteger('id_cargo')->nullable();
            $table->unsignedBigInteger('id_user');
            $table->foreign('id_area')->references('id')->on('area');
            $table->foreign('id_horario_semanal')->references('id')->on('horario_semanal');
            $table->foreign('id_cargo')->references('id')->on('cargo');
            $table->foreign('id_user')->references('id')->on('users');
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
        Schema::dropIfExists('empleado');
    }
}
