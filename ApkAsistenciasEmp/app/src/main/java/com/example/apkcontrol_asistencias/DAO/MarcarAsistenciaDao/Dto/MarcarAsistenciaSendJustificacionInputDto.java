package com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto;

public class MarcarAsistenciaSendJustificacionInputDto {
    private String titulo;
    private String descripcion;

    public MarcarAsistenciaSendJustificacionInputDto(String titulo, String descripcion) {
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
