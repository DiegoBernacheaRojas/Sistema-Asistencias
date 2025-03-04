package com.example.apkcontrol_asistencias.DAO.HistorialJustificacionDao.Dto;

import com.example.apkcontrol_asistencias.Model.JustificacionModel;

import java.util.List;

public class HistorialJustificacionDetalleOutputDto {
    private boolean success;
    private String message;
    private JustificacionModel data;

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

    public JustificacionModel getData() {
        return data;
    }

    public void setData(JustificacionModel data) {
        this.data = data;
    }
}
