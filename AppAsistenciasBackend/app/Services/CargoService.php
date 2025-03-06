<?php
namespace App\Services;

use Illuminate\Http\Request;
use App\Models\Cargo;


class CargoService
{
    public function getAll()
    {
        $cargos = Cargo::select('id', 'nombre', 'descripcion')
                    ->where('is_delete', false)
                    ->get();

        return $cargos;
    }

    public function getId($id)
    {
        $cargo = Cargo::select('id', 'nombre', 'descripcion')
                    ->where('is_delete', false)
                    ->where('id',$id)
                    ->get();

        return $cargo;
    }

    public function register(Request $request)
    {
        // Validar los datos del formulario, si es necesario

        // Crear una nueva instancia de Area
        $cargo = new Cargo();

        // Llamar a buildData para asignar los datos proporcionados
        $cargo = $this->buildData($cargo, $request->all());

        // Guardar el área en la base de datos
        $cargo->save();

        // Obtener el ID del área recién creada
        $cargoId = $cargo->id;

        // Devolver el ID del área recién creada en la respuesta
        return $cargoId;
    }

    public function update(Request $request)
    {
        // Validar los datos del formulario, si es necesario
        $validatedData = $request->validate([
            'nombre' => 'required|string|max:255',
            'descripcion' => 'nullable|string',
        ]);

        // Buscar el área existente por ID
        $cargo = Cargo::find($request->id);

        if (!$cargo) {
            // Devolver una respuesta en caso de que el área no sea encontrada
            return 'Cargo no encontrada';
        }
        // Llamar a buildData para asignar los datos proporcionados
        $cargo = $this->buildData($cargo, $validatedData);

        // Guardar los cambios en la base de datos
        $cargo->save();

        // Devolver una respuesta exitosa
        return $cargo;
    }

    public function delete($id)
    {
        // Buscar el área por su ID y actualizar el campo is_delete a true
        $cargo = Cargo::where('id', $id)->first();

        if ($cargo) {
            $cargo->is_delete = true;
            $cargo->save();

            return true;
        } else {
            return false;
        }
    
    }

    private function buildData(Cargo $cargo, array $data): Cargo
    {
        $cargo->nombre = $data['nombre'];
        $cargo->descripcion = $data['descripcion'];

        return $cargo;
    }
}
