package com.example.apkcontrol_asistencias.Conexion;

public class ApisUrl {
    public static final String BASE_URL = "http://192.168.1.3:8000";

    public static final String HOME = BASE_URL + "/api/employee";
    public static final String LOGIN_ACCOUNT = HOME + "/login";
    public static final String LOGOUT_ACCOUNT = HOME + "/logout";
    public static final String GET_EMPLOYEE = HOME + "/getEmployee";
    public static final String GET_HORARIO = HOME + "/getHorarioEmployee";
    public static final String UPDATE_FOTO_PERFIL = HOME + "/updateFotoPerfil";
    public static final String UPDATE_FACIAL_DATA = HOME + "/updateFacialData";
    public static final String REGISTER_AUSENCIA = HOME + "/justificacion/registerAusencia";
    public static final String GENERAR_QR = HOME + "/codeqr/generar";
    public static final String VERIFICAR_QR = HOME + "/codeqr/verificar";
    public static final String HISTORIAL = HOME + "/justificacion/historial";
    public static final String REGISTRAR_JUSTIFICACION = HOME + "/justificacion/register";
    public static final String REGISTER_ASISTENCIA = HOME + "/asistencia/register";
    public static final String UPDATE_EVIDENCIA = HOME + "/asistencia/evidencia";
    public static final String VERIFICAR_FACIAL_DATA = HOME + "/verificarFacialData";

}