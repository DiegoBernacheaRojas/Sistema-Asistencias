<?php

namespace App\Services;

use App\Models\Empleado;
use App\Models\QRcode;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\User;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Http;

class UserService
{


    public function create(Request $request)
    {
        $validatedData = $request->validate([
            'name' => ['required', 'string', 'max:255'],
            'email' => ['required', 'string', 'max:255', 'email'],
            'password' => ['required', 'string'],
            'role' => ['required', 'string', 'in:admin,employee']
        ]);
        $user = new User();
        $user = $this->buildData($user, $validatedData);
        $user->save();

        $user_id = $user->id;
        $QRservice = new QRcodeService();
        $QRservice->generarCodigoQR($user_id);

        return $user;
    }

    public function getAll()
    {
        $users = User::select('users.id', 'users.name', 'users.email', 'users.password', 'users.role')
            ->leftJoin('empleado', 'users.id', '=', 'empleado.id_user')
            ->where(function ($query) {
                $query->whereNull('empleado.id_user')  // Usuarios sin relación con un empleado
                    ->orWhere('empleado.is_delete', '=', 0);  // Usuarios vinculados con empleados no eliminados
            })
            ->get();

        return $users;
    }

    public function getUnusedUsers()
    {
        $unusedUsers = User::select('id', 'name', 'email', 'role')
            ->whereNotIn('id', function ($query) {
                $query->select('id_user')
                    ->from('empleado');
            })
            ->where('role', 'employee')
            ->get();

        return $unusedUsers;
    }

    public function getId($id)
    {
        $user = User::select('id', 'name', 'email', 'password', 'role')
            ->where('id', $id)
            ->get();

        return $user;
    }

    public function update(Request $request)
    {
        // Validar los datos del formulario, si es necesario
        $validatedData = $request->validate([
            'name' => ['required', 'string', 'max:255'],
            'email' => ['required', 'string', 'max:255'],
            'password' => ['required', 'string', 'max:255'],
            'role' => ['nullable', 'string', 'max:255']
        ]);

        // Buscar el área existente por ID
        $user = User::find($request->id);

        if (!$user) {
            // Devolver una respuesta en caso de que el área no sea encontrada
            return 'user no encontrada';
        }
        // Llamar a buildData para asignar los datos proporcionados
        $user = $this->buildData($user, $validatedData);

        // Guardar los cambios en la base de datos
        $user->save();

        // Devolver una respuesta exitosa
        return $user;
    }

    public function delete($id)
    {

        // Buscar el usuario por su ID
        $user = User::find($id);

        // Verificar si el usuario existe
        if ($user) {
            // Eliminar el registro correspondiente en la tabla empleado
            Empleado::where('id_user', $id)->update([
                'id_user' => null,
            ]);

            QRcode::where('id_user', $id)->delete();

            // Eliminar el usuario permanentemente
            $user->delete();


            return true;
        } else {
            return false;
        }
    }

    private function buildData(User $user, array $data): User
    {
        $user->name = $data['name'];
        $user->email = $data['email'];
        $user->password = Hash::make($data['password']);
        $user->role = $data['role'];

        return $user;
    }
}
