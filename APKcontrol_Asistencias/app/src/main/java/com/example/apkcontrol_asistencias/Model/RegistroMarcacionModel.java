package com.example.apkcontrol_asistencias.Model;

public class RegistroMarcacionModel {
    private int id;
    private String evidencia;
    private String fecha;
    private String hora;
    private String estado;
    private String tipo;
    private int IdJustificacion;
    private int IdEmpleado;

    public RegistroMarcacionModel(String estado) {
        this.id = id;
        this.evidencia = evidencia;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.tipo = tipo;
        this.IdJustificacion = IdJustificacion;
        this.IdEmpleado = IdEmpleado;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public String getTipo() {
        return tipo;
    }

    public int getIdJustificacion() {
        return IdJustificacion;
    }

    public int getIdEmpleado() {
        return IdEmpleado;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setIdJustificacion(int IdJustificacion) {
        this.IdJustificacion = IdJustificacion;
    }

    public void setIdEmpleado(int IdEmpleado) {
        this.IdEmpleado = IdEmpleado;
    }
}
