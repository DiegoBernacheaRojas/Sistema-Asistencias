<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Feriado extends Model
{
    protected $table = 'feriado';
    protected $fillable = [
        'fecha',
        'descripcion',
    ];
}