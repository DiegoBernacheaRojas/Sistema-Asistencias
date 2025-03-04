import apiUrls from '../Utils/ApisURL.js';
import { apiRequest } from '../Utils/ApiClient.js';

class AreaController {
    static async getall() {
        const response = await apiRequest(apiUrls.GET_ALL_AREA, 'GET');
        return response;

    }
    static async get(id) {
        const response = await apiRequest(apiUrls.GET_AREA+id, 'GET');
        return response;

    }
    static async delete(id) {
        const response = await apiRequest(apiUrls.DELETE_AREA+id, 'POST');
        return response;

    }
    static async register(RegisterAreaInput) {
        const response = await apiRequest(apiUrls.REGISTER_AREA, 'POST',{
            nombre: RegisterAreaInput.nombre,
            descripcion: RegisterAreaInput.descripcion
        });
        return response;

    }
    static async update(UpdateAreaInput) {
        const response = await apiRequest(apiUrls.UPDATE_AREA, 'POST',{
            id: UpdateAreaInput.id,
            nombre: UpdateAreaInput.nombre,
            descripcion: UpdateAreaInput.descripcion
        });
        return response;
    }
}

export default AreaController;