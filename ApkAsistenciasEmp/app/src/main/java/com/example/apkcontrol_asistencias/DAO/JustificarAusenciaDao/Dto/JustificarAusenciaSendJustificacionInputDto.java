package com.example.apkcontrol_asistencias.DAO.JustificarAusenciaDao.Dto;

import com.example.apkcontrol_asistencias.Model.EmpleadoModel;

public class JustificarAusenciaSendJustificacionInputDto {
    private String titulo;
    private String descripcion;

    public JustificarAusenciaSendJustificacionInputDto(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
