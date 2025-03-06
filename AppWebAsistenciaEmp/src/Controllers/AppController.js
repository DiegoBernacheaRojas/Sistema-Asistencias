import url from '../ApiUrls';
import ApiController from '../HttpManager';

class AppController {
  static async getEmployee() {
    try {
      const data = await ApiController.fetchData(url.GET_EMPLOYEE);

      return data;
    } catch (error) {
      throw error;
    }
  }
  
  static async getAsistencias() {
    try {
      const data = await ApiController.fetchData(url.GET_ASISTENCIAS);

      return data;
    } catch (error) {
      throw error;
    }
  }

  static async getQR() {
    try {
      const data = await ApiController.fetchData(url.GET_QR);

      return data;
    } catch (error) {
      throw error;
    }
  }


}
export default AppController;