<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Cargo extends Model
{
    protected $table = 'cargo';
    protected $fillable = [
        'nombre',
        'descripcion',
        'is_delete',
    ];

    // Relaciones
    public function empleados()
    {
        return $this->hasMany(Empleado::class);
    }
}