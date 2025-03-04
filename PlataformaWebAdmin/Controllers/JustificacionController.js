import apiUrls from '../Utils/ApisURL.js';
import { apiRequest } from '../Utils/ApiClient.js';

class HorarioController {
    static async getallday() {
        const response = await apiRequest(apiUrls.GET_ALL_DAY_JUST, 'GET');
        return response;

    }
    static async detalle(id) {
        const response = await apiRequest(apiUrls.GET_DETALLE_JUST+id, 'GET');
        return response;

    }
    static async update(UpdateInput) {
        const response = await apiRequest(apiUrls.UPDATE_JUST, 'POST',{
            id: UpdateInput.id,
            estado: UpdateInput.estado,
            comentario: UpdateInput.comentario
        });
        return response;
    }

}

export default HorarioController;