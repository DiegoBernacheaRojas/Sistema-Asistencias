package com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto;

public class MarcarAsistenciaVerificarFacialDataOutputDto {
    private boolean success;
    private boolean verificado;
    private String message;


    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getVerificado() {
        return verificado;
    }
    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
