<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Support\Facades\DB;

class InsertHorarioSemanal extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        // Insertar registros de horario_dia
        $dias = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
        $horarioSemanalId = DB::table('horario_semanal')->insertGetId(['descripcion' => 'Horario Semanal Predeterminado']);
        foreach ($dias as $index => $dia) {
            if ($dia != 'Sábado') {
                DB::table('horario_dia')->insert([
                    'dia' => $dia,
                    'hora_entrada' => '08:00:00',
                    'hora_salida' => '17:00:00',
                    'id_horario_semanal' => $horarioSemanalId,
                    'created_at' => now(),
                    'updated_at' => now(),
                ]);
            } else {
                DB::table('horario_dia')->insert([
                    'dia' => $dia,
                    'hora_entrada' => '08:00:00',
                    'hora_salida' => '12:00:00',
                    'id_horario_semanal' => $horarioSemanalId,
                    'created_at' => now(),
                    'updated_at' => now(),
                ]);
            }
        }

        // Actualizar empleado con id 1 para asignarle el id de horario semanal recién creado
        DB::table('empleado')
            ->where('id', 1)
            ->update(['id_horario_semanal' => $horarioSemanalId]);
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        // No hay operaciones de reversión necesarias ya que esta migración solo agrega datos
    }
}