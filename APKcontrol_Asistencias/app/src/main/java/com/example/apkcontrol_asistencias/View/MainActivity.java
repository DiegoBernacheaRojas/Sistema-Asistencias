package com.example.apkcontrol_asistencias.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.apkcontrol_asistencias.Controller.MainActivityController;

public class MainActivity extends AppCompatActivity implements MainActivityController.MenuListener {

    private MainActivityController MainActivityCont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityCont = new MainActivityController(MainActivity.this);
        MainActivityCont.VerificarSesion();
    }
    @Override
    public void onMenuSuccess() {
        Intent intent = new Intent(this, MnMenu.class);
        startActivity(intent);
        finish();  // Finalizar la actividad actual
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, MnLogin.class);
        startActivity(intent);
        finish();  // Finalizar la actividad actual
    }
}