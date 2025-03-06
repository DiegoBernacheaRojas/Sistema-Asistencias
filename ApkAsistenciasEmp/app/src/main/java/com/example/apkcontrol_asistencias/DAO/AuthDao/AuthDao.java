package com.example.apkcontrol_asistencias.DAO.AuthDao;

import com.example.apkcontrol_asistencias.DAO.AuthDao.Dto.AuthLoginInputDto;

public interface AuthDao <User>{

    void ValidAccount(AuthLoginInputDto authLoginInputDto);
    void ServicioTecnico();
}
