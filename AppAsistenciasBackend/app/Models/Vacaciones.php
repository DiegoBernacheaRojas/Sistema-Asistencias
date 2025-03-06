<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Vacaciones extends Model
{
    protected $table = 'vacaciones';
    protected $fillable = [
        'fecha_inicio',
        'fecha_fin',
        'description',
        'id_empleado',
    ];

    // Relaciones
    public function empleado()
    {
        return $this->belongsTo(Empleado::class, 'id_empleado');
    }
}