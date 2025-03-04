package com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto;

import com.example.apkcontrol_asistencias.Model.RegistroMarcacionModel;

public class MarcarAsistenciaRegisterOutputDto {
    private boolean success;
    private String message;
    private RegistroMarcacionModel data;

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

    public RegistroMarcacionModel getData() {
        return data;
    }

    public void setData(RegistroMarcacionModel data) {
        this.data = data;
    }

}
