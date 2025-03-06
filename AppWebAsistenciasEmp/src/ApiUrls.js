const dominio = 'http://127.0.0.1:8000'
const home = dominio+'/api/employee'
const url = {
    DOMINIO: dominio,

    LOGIN: home+'/login',
    GET_EMPLOYEE: home+'/getEmployee',
    GET_ASISTENCIAS: home+'/asistencia/getId',
    GET_QR: home+'/codeqr/mostrar'
    // Puedes agregar más URLs aquí si lo necesitas
  };
  
  export default url;