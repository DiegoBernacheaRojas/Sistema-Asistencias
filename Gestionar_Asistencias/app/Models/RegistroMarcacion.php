<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class RegistroMarcacion extends Model
{
    protected $table = 'registro_marcacion';
    protected $fillable = [
        'evidencia',
        'fecha',
        'hora',
        'estado',
        'tipo',
        'id_justificacion',
        'id_empleado',
    ];

    // Relaciones
    public function justificacion()
    {
        return $this->belongsTo(Justificacion::class, 'id_justificacion');
    }

    public function empleado()
    {
        return $this->belongsTo(Empleado::class, 'id_empleado');
    }
}