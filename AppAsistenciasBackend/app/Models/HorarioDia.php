<?php 
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class HorarioDia extends Model
{
    protected $table = 'horario_dia';
    protected $fillable = [
        'dia',
        'hora_entrada',
        'hora_salida',
        'id_horario_semanal',
    ];

    // Relaciones
    public function horarioSemanal()
    {
        return $this->belongsTo(HorarioSemanal::class);
    }
}