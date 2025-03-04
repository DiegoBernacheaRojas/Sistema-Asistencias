import apiUrls from '../Utils/ApisURL.js';
import { apiRequest } from '../Utils/ApiClient.js';

class CargoController {
    static async getall() {
        const response = await apiRequest(apiUrls.GET_ALL_CARGO, 'GET');
        return response;

    }
    static async get(id) {
        const response = await apiRequest(apiUrls.GET_CARGO+id, 'GET');
        return response;

    }
    static async delete(id) {
        const response = await apiRequest(apiUrls.DELETE_CARGO+id, 'POST');
        return response;

    }
    static async register(RegisterInput) {
        const response = await apiRequest(apiUrls.REGISTER_CARGO, 'POST',{
            nombre: RegisterInput.nombre,
            descripcion: RegisterInput.descripcion
        });
        return response;

    }
    static async update(UpdateInput) {
        const response = await apiRequest(apiUrls.UPDATE_CARGO, 'POST',{
            id: UpdateInput.id,
            nombre: UpdateInput.nombre,
            descripcion: UpdateInput.descripcion
        });
        return response;
    }
}

export default CargoController;