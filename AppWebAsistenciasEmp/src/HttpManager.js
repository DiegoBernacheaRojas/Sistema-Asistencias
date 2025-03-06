import Cookies from 'js-cookie';

class ApiController {
  static async fetchData(url, method = 'GET', body = null, headers = {}) {
    const defaultHeaders = {
      'Content-Type': 'application/json',
    };

    // Obtener el token de las cookies
    const token = Cookies.get('token');

    // Agregar el token a los encabezados si está disponible
    if (token) {
      defaultHeaders['Authorization'] = `Bearer ${token}`;
    }

    const requestOptions = {
      method,
      headers: { ...defaultHeaders, ...headers }, // Combina los encabezados predeterminados con los proporcionados
    };

    if (body) {
      requestOptions.body = JSON.stringify(body);
    }

    try {
      const response = await fetch(url, requestOptions);
      const data = await response.json();

      if (!response.ok) {
        const errorDetails = {
          status: response.status,
          statusText: response.statusText,
          message: data.message || 'Error al consultar la API',
        };
        throw new Error(JSON.stringify(errorDetails));
      }

      return data;
    } catch (error) {
      console.error('Error fetching data:', error);
      throw error;
    }
  }
}

export default ApiController;