package com.example.apkcontrol_asistencias.Model;

public class CargoModel {
    private int id;
    private String nombre;
    private String descripcion;

    public CargoModel(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getter para el campo id
    public int getId() {
        return id;
    }

    // Setter para el campo id
    public void setId(int id) {
        this.id = id;
    }

    // Getter para el campo nombre
    public String getNombre() {
        return nombre;
    }

    // Setter para el campo nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter para el campo descripcion
    public String getDescripcion() {
        return descripcion;
    }

    // Setter para el campo descripcion
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}