package com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao;

import android.graphics.Bitmap;

import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaVerificarFacialDataInputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaSendJustificacionInputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaVerificarQRInputDto;

public interface MarcarAsistenciaDao {
    void VerificarQR(MarcarAsistenciaVerificarQRInputDto verificarQRInputDto );
    void GenerarQR();
    void Register();
    void UpdateEvidencia(Bitmap image);
    void SendJustifiacion(MarcarAsistenciaSendJustificacionInputDto sendJustificacionInputDto);
    void VerificarFacialData(Bitmap image);
}
