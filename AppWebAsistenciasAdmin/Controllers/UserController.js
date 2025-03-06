import apiUrls from '../Utils/ApisURL.js';
import { apiRequest } from '../Utils/ApiClient.js';

class UserController {
    static async getall() {
        const response = await apiRequest(apiUrls.GET_ALL_USER, 'GET');
        return response;

    }

    static async getUnusedUsers() {
        const response = await apiRequest(apiUrls.GET_UNUSED_USERS, 'GET');
        return response;

    }

    static async get(id) {
        const response = await apiRequest(apiUrls.GET_USER+id, 'GET');
        return response;

    }
    static async delete(id) {
        const response = await apiRequest(apiUrls.DELETE_USER+id, 'POST');
        return response;

    }
    static async register(RegisterInput) {
        const response = await apiRequest(apiUrls.REGISTER_USER, 'POST',{
            name: RegisterInput.name,
            email: RegisterInput.email,
            password: RegisterInput.password,
            role: RegisterInput.role
        });
        return response;

    }
    static async update(UpdateInput) {
        const response = await apiRequest(apiUrls.UPDATE_USER, 'POST',{
            id: UpdateInput.id,
            name: UpdateInput.name,
            email: UpdateInput.email,
            password: UpdateInput.password,
            role: UpdateInput.role
        });
        return response;
    }
}

export default UserController;