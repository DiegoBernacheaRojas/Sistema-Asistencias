package com.example.apkcontrol_asistencias.DAO.AuthDao.Dto;

public class AuthLoginOutputDto {

    public  String token;
    public  Boolean success;
    public  String message;

    public void AuthLoginInputDto(String token, Boolean success, String message){
        this.token=token;
        this.success=success;
        this.message=message;
    }

    public String getToken() {
        return token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}