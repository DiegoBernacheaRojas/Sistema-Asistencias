<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class HorarioSemanal extends Model
{
    protected $table = 'horario_semanal';
    protected $fillable = [
        'descripcion',
        'is_delete',
    ];

    public function horarioDia()
    {
        return $this->hasMany(HorarioDia::class, 'id_horario_semanal');
    }
}