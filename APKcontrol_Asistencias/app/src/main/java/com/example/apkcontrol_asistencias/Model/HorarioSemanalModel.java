package com.example.apkcontrol_asistencias.Model;

public class HorarioSemanalModel {
    private int id;
    private String nombre;
    private String descripcion;
    private boolean isDelete;

    public HorarioSemanalModel(int id, String nombre, String descripcion, boolean isDelete) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.isDelete = isDelete;
    }
}
