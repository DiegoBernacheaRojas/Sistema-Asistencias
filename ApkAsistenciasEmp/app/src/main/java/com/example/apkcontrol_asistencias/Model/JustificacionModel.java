package com.example.apkcontrol_asistencias.Model;

public class JustificacionModel {
    private String titulo;
    private String descripcion;
    private String razon;
    private String estado;
    private String fecha;
    private String comentario;

    public JustificacionModel(String titulo, String descripcion, String razon, String estado, String fecha, String comentario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.razon = razon;
        this.estado = estado;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    // Getter para titulo
    public String getTitulo() {
        return titulo;
    }

    // Setter para titulo
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Getter para descripcion
    public String getDescripcion() {
        return descripcion;
    }

    // Setter para descripcion
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    // Getter para comentario
    public String getComentario() {
        return comentario;
    }

    // Setter para comentario
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
