import apiUrls from '../Utils/ApisURL.js';
import { apiRequest } from '../Utils/ApiClient.js';

class FeriadoController {
    static async getall() {
        const response = await apiRequest(apiUrls.GET_ALL_FERIADO, 'GET');
        return response;

    }
    static async get(id) {
        const response = await apiRequest(apiUrls.GET_FERIADO+id, 'GET');
        return response;

    }
    static async delete(id) {
        const response = await apiRequest(apiUrls.DELETE_FERIADO+id, 'POST');
        return response;

    }
    static async register(RegisterFeriadoInput) {
        const response = await apiRequest(apiUrls.REGISTER_FERIADO, 'POST',{
            fecha: RegisterFeriadoInput.fecha,
            descripcion: RegisterFeriadoInput.descripcion
        });
        return response;

    }
    static async update(UpdateFeriadoInput) {
        const response = await apiRequest(apiUrls.UPDATE_FERIADO, 'POST',{
            id: UpdateFeriadoInput.id,
            fecha: UpdateFeriadoInput.fecha,
            descripcion: UpdateFeriadoInput.descripcion
        });
        return response;
    }
}

export default FeriadoController;