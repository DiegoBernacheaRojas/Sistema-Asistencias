package com.example.apkcontrol_asistencias.DAO.JustificarAusenciaDao.Dto;

import com.example.apkcontrol_asistencias.Model.EmpleadoModel;

public class JustificarAusenciaSendJustificacionOutputDto {
    private boolean success;
    private String message;


    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
