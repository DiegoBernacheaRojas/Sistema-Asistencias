<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class QRcode extends Model
{
    
    protected $table = 'q_r_codes';
    
    protected $fillable = [
        'codigo_qr',
        'id_user',
    ];

    public function user()
    {
        return $this->belongsTo(User::class, 'id_user');
    }
}