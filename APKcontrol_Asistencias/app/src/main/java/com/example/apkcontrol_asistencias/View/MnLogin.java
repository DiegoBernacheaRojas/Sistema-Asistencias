package com.example.apkcontrol_asistencias.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.apkcontrol_asistencias.Controller.AuthController;
import com.example.apkcontrol_asistencias.DAO.AuthDao.Dto.AuthLoginInputDto;
import com.example.apkcontrol_asistencias.R;

public class MnLogin extends AppCompatActivity implements AuthController.menuListener {

    private EditText txtemail,txtpas;
    private AuthController AuthCont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginasistencia);
        txtemail = (EditText) findViewById(R.id.editTextTextEmail);
        txtpas = (EditText) findViewById(R.id.editTextTextPassword);
        AuthCont=new AuthController(MnLogin.this);
    }

    public void OnClick_BtnLogin(View v){
        try {
            AuthLoginInputDto loginInputDto= new AuthLoginInputDto(txtemail.getText().toString(),txtpas.getText().toString());
            AuthCont.ValidAccount(loginInputDto);
        } catch (Exception e)
        {throw new RuntimeException(e);}
    }

    public void OnClick_BtnSoporteTecnico(View v){
        try {
            AuthCont.ServicioTecnico();
        } catch (Exception e)
        {throw new RuntimeException(e);}
    }

    @Override
    public void onMenuSuccess() {
        Intent intent = new Intent(MnLogin.this, MnMenu.class);
        startActivity(intent);
        finish();  // Finalizar la actividad actual
    }
}