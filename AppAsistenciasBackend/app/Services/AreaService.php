<?php
namespace App\Services;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\Area;
use App\Models\RegistroMarcacion;
use App\Models\User;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Http;

class AreaService
{
    public function getAll()
    {
        $areas = Area::select('id', 'nombre', 'descripcion')
                    ->where('is_delete', false)
                    ->get();

        return $areas;
    }

    public function getId($id)
    {
        $area = Area::select('id', 'nombre', 'descripcion')
                    ->where('is_delete', false)
                    ->where('id',$id)
                    ->get();

        return $area;
    }

    public function register(Request $request)
    {
        // Validar los datos del formulario, si es necesario

        // Crear una nueva instancia de Area
        $area = new Area();

        // Llamar a buildData para asignar los datos proporcionados
        $area = $this->buildData($area, $request->all());

        // Guardar el área en la base de datos
        $area->save();

        // Obtener el ID del área recién creada
        $areaId = $area->id;

        // Devolver el ID del área recién creada en la respuesta
        return $areaId;
    }

    public function update(Request $request)
    {
        // Validar los datos del formulario, si es necesario
        $validatedData = $request->validate([
            'nombre' => 'required|string|max:255',
            'descripcion' => 'nullable|string',
        ]);

        // Buscar el área existente por ID
        $area = Area::find($request->id);

        if (!$area) {
            // Devolver una respuesta en caso de que el área no sea encontrada
            return 'Área no encontrada';
        }
        // Llamar a buildData para asignar los datos proporcionados
        $area = $this->buildData($area, $validatedData);

        // Guardar los cambios en la base de datos
        $area->save();

        // Devolver una respuesta exitosa
        return $area;
    }

    public function delete($id)
    {
        // Buscar el área por su ID y actualizar el campo is_delete a true
        $area = Area::where('id', $id)->first();

        if ($area) {
            $area->is_delete = true;
            $area->save();

            return true;
        } else {
            return false;
        }
    
    }

    private function buildData(Area $area, array $data): Area
    {
        $area->nombre = $data['nombre'];
        $area->descripcion = $data['descripcion'];

        return $area;
    }
}
