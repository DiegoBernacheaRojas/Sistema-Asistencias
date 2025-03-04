import apiUrls from '../Utils/ApisURL.js';
import { apiRequest } from '../Utils/ApiClient.js';
import GetEmpPresentesOutput from '../Models/GetEmpPresentesOutput.js';

class EmpleadoController {
    static async getEmpPresentes() {
        try {
            const response = await apiRequest(apiUrls.GET_EMP_PRESENTES, 'GET');
            const { data, success, message } = response;
            return new GetEmpPresentesOutput(success, message, data);
        } catch (error) {
            console.error('Error in getEmpPresentes:', error);
            return new GetEmpPresentesOutput(false, 'Error occurred', null);
        }
    }
    static async getall() {
        const response = await apiRequest(apiUrls.GET_ALL_EMP, 'GET');
        return response;

    }
    static async get(id) {
        const response = await apiRequest(apiUrls.GET_EMP+id, 'GET');
        return response;

    }
    static async delete(id) {
        const response = await apiRequest(apiUrls.DELETE_EMP+id, 'POST');
        return response;

    }
    static async register(RegisterEmpInput) {
        const response = await apiRequest(apiUrls.REGISTER_EMP, 'POST',{
            nombres: RegisterEmpInput.nombres,
            apellidos: RegisterEmpInput.apellidos,
            telefono: RegisterEmpInput.telefono,
            dni: RegisterEmpInput.dni,
            id_area: RegisterEmpInput.id_area,
            id_horario_semanal: RegisterEmpInput.id_horario_semanal,
            id_cargo: RegisterEmpInput.id_cargo,
            id_user: RegisterEmpInput.id_user
        });
        return response;

    }
    static async update(UpdateEmpInput) {
        const response = await apiRequest(apiUrls.UPDATE_EMP, 'POST',{
            id: UpdateEmpInput.id,
            nombres: UpdateEmpInput.nombres,
            apellidos: UpdateEmpInput.apellidos,
            telefono: UpdateEmpInput.telefono,
            dni: UpdateEmpInput.dni,
            id_area: UpdateEmpInput.id_area,
            id_horario_semanal: UpdateEmpInput.id_horario_semanal,
            id_cargo: UpdateEmpInput.id_cargo,
            id_user: UpdateEmpInput.id_user
        });
        return response;
    }
}

export default EmpleadoController;