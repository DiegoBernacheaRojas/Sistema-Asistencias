<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Area extends Model
{
    protected $table = 'area';
    protected $fillable = [
        'image',
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