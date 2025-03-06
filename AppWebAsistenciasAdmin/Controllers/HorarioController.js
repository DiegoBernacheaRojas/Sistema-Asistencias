import apiUrls from '../Utils/ApisURL.js';
import { apiRequest } from '../Utils/ApiClient.js';

class HorarioController {
    static async getall() {
        const response = await apiRequest(apiUrls.GET_ALL_HORARIO, 'GET');
        return response;

    }
    static async get(id) {
        const response = await apiRequest(apiUrls.GET_HORARIO+id, 'GET');
        return response;

    }
    static async delete(id) {
        const response = await apiRequest(apiUrls.DELETE_HORARIO+id, 'POST');
        return response;

    }
    static async register(RegisterHorarioInput) {
        const response = await apiRequest(apiUrls.REGISTER_HORARIO, 'POST',{
            descripcion: RegisterHorarioInput.descripcion,
            dias: RegisterHorarioInput.dias
        });
        return response;

    }
    static async update(UpdateHorarioInput) {
        const response = await apiRequest(apiUrls.UPDATE_HORARIO, 'POST',{
            id: UpdateHorarioInput.id,
            descripcion: UpdateHorarioInput.descripcion,
            dias: UpdateHorarioInput.dias
        });
        return response;
    }
}

export default HorarioController;