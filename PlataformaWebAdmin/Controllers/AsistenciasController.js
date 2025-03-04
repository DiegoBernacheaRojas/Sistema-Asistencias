import apiUrls from '../Utils/ApisURL.js';
import { apiRequest } from '../Utils/ApiClient.js';

class AsistenciasController {
    static async getallday() {
        const response = await apiRequest(apiUrls.GET_ALL_DAY_ASIST, 'GET');
        return response;

    }
    static async getForArea(area) {
        const response = await apiRequest(apiUrls.GET_FOR_AREA+area, 'GET');
        return response;

    }
    static async getTopMensual() {
        const response = await apiRequest(apiUrls.GET_TOP, 'GET');
        return response;

    }
    static async getHorasTrabajadas(fechaIni,fechaFin) {
        const response = await apiRequest(apiUrls.GET_HORAS_TRABAJO+fechaIni+'/'+fechaFin, 'GET');
        return response;

    }
    static async getTendencia() {
        const response = await apiRequest(apiUrls.GET_TENDENCIA, 'GET');
        return response;

    }
}

export default AsistenciasController;