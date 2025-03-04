package com.example.apkcontrol_asistencias.Model;

public class EmpleadoModel {
    private int id;
    private String foto;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String dni;
    private AreaModel Area;
    private CargoModel Cargo;
    private UserModel User;

    public EmpleadoModel(int id, String foto, String nombres, String apellidos, String telefono, String dni, AreaModel Area, CargoModel Cargo, UserModel User) {
        this.id = id;
        this.foto = foto;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.dni = dni;
        this.Area = Area;
        this.Cargo = Cargo;
        this.User = User;
    }

    public int getId() {
        return id;
    }

    public String getFoto() {
        return foto;
    }

    public String getNombres() { return nombres; }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDni() {
        return dni;
    }

    public AreaModel getArea() {
        return Area;
    }

    public CargoModel getCargo() {
        return Cargo;
    }

    public UserModel getUser() {
        return User;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setArea(AreaModel Area) {
        this.Area = Area;
    }

    public void setCargo(CargoModel Cargo) {
        this.Cargo = Cargo;
    }

    public void setUser(UserModel User) {
        this.User = User;
    }
}
