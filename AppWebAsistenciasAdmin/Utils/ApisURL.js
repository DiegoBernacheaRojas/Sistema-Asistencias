const LOCAL_API_BASE_URL = 'http://127.0.0.1:8000/api';

const apiUrls = {
    DOMINIO: 'http://127.0.0.1:8000',
    LOGIN : `${LOCAL_API_BASE_URL}/admin/login`,
    GET_EMP_PRESENTES : `${LOCAL_API_BASE_URL}/admin/getEmpleadosPresentes`,

    GET_ALL_AREA :  `${LOCAL_API_BASE_URL}/admin/area/getall`,
    GET_AREA :  `${LOCAL_API_BASE_URL}/admin/area/get/`,
    DELETE_AREA :  `${LOCAL_API_BASE_URL}/admin/area/delete/`,
    REGISTER_AREA :  `${LOCAL_API_BASE_URL}/admin/area/register`,
    UPDATE_AREA :  `${LOCAL_API_BASE_URL}/admin/area/update`,

    GET_ALL_EMP :  `${LOCAL_API_BASE_URL}/admin/employee/getall`,
    GET_EMP :  `${LOCAL_API_BASE_URL}/admin/employee/get/`,
    DELETE_EMP :  `${LOCAL_API_BASE_URL}/admin/employee/delete/`,
    REGISTER_EMP :  `${LOCAL_API_BASE_URL}/admin/employee/register`,
    UPDATE_EMP :  `${LOCAL_API_BASE_URL}/admin/employee/update`,

    GET_ALL_CARGO :  `${LOCAL_API_BASE_URL}/admin/cargo/getall`,
    GET_CARGO :  `${LOCAL_API_BASE_URL}/admin/cargo/get/`,
    DELETE_CARGO :  `${LOCAL_API_BASE_URL}/admin/cargo/delete/`,
    REGISTER_CARGO :  `${LOCAL_API_BASE_URL}/admin/cargo/register`,
    UPDATE_CARGO :  `${LOCAL_API_BASE_URL}/admin/cargo/update`,

    GET_ALL_HORARIO :  `${LOCAL_API_BASE_URL}/admin/horario/getall`,
    GET_HORARIO :  `${LOCAL_API_BASE_URL}/admin/horario/get/`,
    DELETE_HORARIO :  `${LOCAL_API_BASE_URL}/admin/horario/delete/`,
    REGISTER_HORARIO :  `${LOCAL_API_BASE_URL}/admin/horario/register`,
    UPDATE_HORARIO :  `${LOCAL_API_BASE_URL}/admin/horario/update`,

    GET_ALL_USER :  `${LOCAL_API_BASE_URL}/admin/user/getall`,
    GET_UNUSED_USERS: `${LOCAL_API_BASE_URL}/admin/user/getUnusedUsers`,
    GET_USER :  `${LOCAL_API_BASE_URL}/admin/user/get/`,
    DELETE_USER :  `${LOCAL_API_BASE_URL}/admin/user/delete/`,
    REGISTER_USER :  `${LOCAL_API_BASE_URL}/admin/user/create`,
    UPDATE_USER :  `${LOCAL_API_BASE_URL}/admin/user/update`,

    GET_ALL_DAY_JUST :`${LOCAL_API_BASE_URL}/admin/justificacion/getallday`,
    GET_DETALLE_JUST :`${LOCAL_API_BASE_URL}/admin/justificacion/detalle/`,
    UPDATE_JUST: `${LOCAL_API_BASE_URL}/admin/justificacion/update`,

    GET_ALL_DAY_ASIST:`${LOCAL_API_BASE_URL}/admin/asistencias/getallday`,
    GET_FOR_AREA:`${LOCAL_API_BASE_URL}/admin/asistencias/getForArea/`,
    GET_TOP:`${LOCAL_API_BASE_URL}/admin/asistencias/getTopMensual`,
    GET_HORAS_TRABAJO:`${LOCAL_API_BASE_URL}/admin/asistencias/getHorasTrabajadas/`,
    GET_TENDENCIA:`${LOCAL_API_BASE_URL}/admin/asistencias/getTendencia`,

    GET_ALL_FERIADO:`${LOCAL_API_BASE_URL}/admin/feriado/getall`,
    GET_FERIADO:`${LOCAL_API_BASE_URL}/admin/feriado/get/`,
    REGISTER_FERIADO:`${LOCAL_API_BASE_URL}/admin/feriado/register`,
    UPDATE_FERIADO:`${LOCAL_API_BASE_URL}/admin/feriado/update`,
    DELETE_FERIADO:`${LOCAL_API_BASE_URL}/admin/feriado/delete/`,
};

export default apiUrls;


