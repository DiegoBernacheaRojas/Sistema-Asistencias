package com.example.apkcontrol_asistencias.DAO.HistorialJustificacionDao.Dto;

import java.util.List;

public class HistorialJustificacionListOutputDto {
    private boolean success;
    private String message;
    private List<HistorialJustificacionListDataOutputDto> data;

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

    public List<HistorialJustificacionListDataOutputDto> getData() {
        return data;
    }

    public void setData(List<HistorialJustificacionListDataOutputDto> data) {
        this.data = data;
    }
}
