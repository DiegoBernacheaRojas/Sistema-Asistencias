import apiUrls from '../Utils/ApisURL.js';
import { apiRequest } from '../Utils/ApiClient.js';
import LoginOutput from '../Models/LoginOutput.js';

class AuthController {
    static async login(LoginInput) {

            const response = await apiRequest(apiUrls.LOGIN, 'POST', {
                email: LoginInput.email,
                password: LoginInput.password
            });
            const {token, success, message } = response;

            if (success && token) {
                // Guardar el token en una cookie
                Cookies.set('tokenadmin', token, { expires: 1 }); // El token expirará en 7 días
            }

            return new LoginOutput(success, message, token);
    }
    static async logout() {

        Cookies.remove('tokenadmin'); // Eliminar la cookie 'tokenadmin'
        
        return true;
    }   
    
}

export default AuthController;