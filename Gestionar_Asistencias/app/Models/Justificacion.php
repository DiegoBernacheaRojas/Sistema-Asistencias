<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Justificacion extends Model
{
    
    protected $table = 'justificacion';
    
    protected $fillable = [
        'titulo',
        'descripcion',
        'razon',
        'estado',
        'comentario',
    ];
    
    public function registro_marcacion()
    {
        return $this->hasMany(RegistroMarcacion::class, 'id_justificacion', 'id');
    }
}