<?php
namespace App\Services;

use Illuminate\Http\Request;
use App\Models\Feriado;


class FeriadoService
{
    public function getAll()
    {
        $feriados = Feriado::select('id', 'fecha', 'descripcion')
                    ->get();

        return $feriados;
    }

    public function getId($id)
    {
        $feriado = Feriado::select('id', 'fecha', 'descripcion')
                    ->where('id',$id)
                    ->get();

        return $feriado;
    }

    public function register(Request $request)
    {
        // Validar los datos del formulario, si es necesario

        // Crear una nueva instancia de Area
        $feriado = new Feriado();

        // Llamar a buildData para asignar los datos proporcionados
        $feriado = $this->buildData($feriado, $request->all());

        // Guardar el área en la base de datos
        $feriado->save();

        // Obtener el ID del área recién creada
        $feriadoId = $feriado->id;

        // Devolver el ID del área recién creada en la respuesta
        return $feriadoId;
    }

    public function update(Request $request)
    {
        // Validar los datos del formulario, si es necesario
        $validatedData = $request->validate([
            'fecha' => 'required',
            'descripcion' => 'required|string',
        ]);

        // Buscar el área existente por ID
        $feriado = Feriado::find($request->id);

        if (!$feriado) {
            // Devolver una respuesta en caso de que el área no sea encontrada
            return 'Feriado no encontrada';
        }
        // Llamar a buildData para asignar los datos proporcionados
        $feriado = $this->buildData($feriado, $validatedData);

        // Guardar los cambios en la base de datos
        $feriado->save();

        // Devolver una respuesta exitosa
        return $feriado;
    }

    public function delete($id)
    {
        // Buscar el área por su ID y actualizar el campo is_delete a true
        $feriado = Feriado::find($id);

        // Verificar si el usuario existe
        if ($feriado) {
            // Eliminar el usuario permanentemente
            $feriado->delete();
            return true;
        } else {
            return false;
        }
    
    }

    private function buildData(Feriado $feriado, array $data): Feriado
    {
        $feriado->fecha = $data['fecha'];
        $feriado->descripcion = $data['descripcion'];

        return $feriado;
    }
}
