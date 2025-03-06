<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Empleado extends Model
{
    protected $table = 'empleado';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'foto', 'nombres', 'apellidos', 'telefono', 'dni', 'is_delete', 'id_area', 'id_horario_semanal', 'id_cargo', 'id_user', 'facial_data'
    ];

    /**
     * Get the user associated with the employee.
     */
    public function user()
    {
        return $this->belongsTo(User::class, 'id_user');
    }

    /**
     * Get the area associated with the employee.
     */
    public function area()
    {
        return $this->belongsTo(Area::class, 'id_area');
    }

    /**
     * Get the weekly schedule associated with the employee.
     */
    public function horarioSemanal()
    {
        return $this->belongsTo(HorarioSemanal::class, 'id_horario_semanal');
    }

    /**
     * Get the job position associated with the employee.
     */
    public function cargo()
    {
        return $this->belongsTo(Cargo::class, 'id_cargo');
    }
}