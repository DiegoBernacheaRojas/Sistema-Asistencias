package com.example.apkcontrol_asistencias.DAO.HistorialJustificacionDao.Dto;

public class HistorialJustificacionListDataOutputDto {
    private int id;
    private String titulo;
    private String razon;
    private String estado;
    private String fecha;

    public HistorialJustificacionListDataOutputDto(String titulo, String razon, String estado, String fecha, int id) {
        this.titulo = titulo;
        this.razon = razon;
        this.estado = estado;
        this.fecha = fecha;
        this.id = id;
    }

    // Getter para titulo
    public String getTitulo() {
        return titulo;
    }

    // Setter para titulo
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Getter para razon
    public String getRazon() {
        return razon;
    }

    // Setter para razon
    public void setRazon(String razon) {
        this.razon = razon;
    }

    // Getter para estado
    public String getEstado() {
        return estado;
    }

    // Setter para estado
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Getter para fecha
    public String getFecha() {
        return fecha;
    }

    // Setter para fecha
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    // Getter para id
    public int getId() {
        return id;
    }

    // Setter para fecha
    public void setId(int id) {
        this.id = id;
    }
}
