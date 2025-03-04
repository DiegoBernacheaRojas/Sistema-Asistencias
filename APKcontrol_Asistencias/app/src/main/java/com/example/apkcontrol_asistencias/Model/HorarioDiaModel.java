package com.example.apkcontrol_asistencias.Model;

public class HorarioDiaModel {
    private String dia;
    private String horaEntrada;
    private String horaSalida;
    private String mensaje;
    private boolean feriado;
    private boolean vacaciones;

    public HorarioDiaModel( String dia, String horaEntrada, String horaSalida, String mensaje, boolean feriado, boolean vacaciones) {
        this.dia = dia;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.mensaje = mensaje;
        this.feriado= feriado;
        this.vacaciones= vacaciones;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getMensaje() { return mensaje; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public boolean getFeriado() { return feriado; }

    public void setFeriado(boolean feriado) { this.feriado = feriado; }

    public boolean getVacaciones() { return vacaciones; }

    public void setVacaciones(boolean vacaciones) { this.vacaciones = vacaciones; }

}
