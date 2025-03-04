<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateHorarioDiaTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('horario_dia', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('dia', 50);
            $table->time('hora_entrada');
            $table->time('hora_salida');
            $table->unsignedBigInteger('id_horario_semanal');
            $table->foreign('id_horario_semanal')->references('id')->on('horario_semanal');
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
        Schema::dropIfExists('horario_dia');
    }
}
