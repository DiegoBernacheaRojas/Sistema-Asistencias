package com.example.apkcontrol_asistencias.Model;

public class UserModel {
    private int id;
    private String name;
    private String email;
    private String role;

    public UserModel(int id, String name, String email,String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setRole(String role) {
        this.role = role;
    }

}