import url from '../ApiUrls';
import ApiController from '../HttpManager';

class LoginController {
  static async loginUser(email, password) {
    const body = {
      email,
      password,
    };

    try {
      const data = await ApiController.fetchData(url.LOGIN, 'POST', body);

      // Guardar el token en una cookie segura
      document.cookie = `token=${data.token}`;

      return data;
    } catch (error) {
      throw error;
    }
  }

}
export default LoginController;