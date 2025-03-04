package com.example.apkcontrol_asistencias.View.Menu.Justificar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apkcontrol_asistencias.Controller.AuthController;
import com.example.apkcontrol_asistencias.Controller.JustificarAusenciaController;
import com.example.apkcontrol_asistencias.DAO.AuthDao.Dto.AuthLoginInputDto;
import com.example.apkcontrol_asistencias.DAO.JustificarAusenciaDao.Dto.JustificarAusenciaSendJustificacionInputDto;
import com.example.apkcontrol_asistencias.View.MnLogin;
import com.example.apkcontrol_asistencias.View.MnMenu;
import com.example.apkcontrol_asistencias.R;

public class MnJusticarAusencia extends AppCompatActivity {

    private JustificarAusenciaController JustAusenCont;
    private EditText txtTitulo,txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_justificar_ausencia);
        txtTitulo = (EditText) findViewById(R.id.Frm_JustAuse_txtTitulo);
        txtDescripcion = (EditText) findViewById(R.id.Frm_JustAuse_txtDescripcion);
        JustAusenCont=new JustificarAusenciaController(MnJusticarAusencia.this);

    }
    public void OnClick_BtnCancelar(View v){
        finish();
    }
    public void OnClick_BtnEnviar(View v){
        try {
            JustificarAusenciaSendJustificacionInputDto SendJustificacionInputDto= new JustificarAusenciaSendJustificacionInputDto(txtTitulo.getText().toString(),txtDescripcion.getText().toString());
            JustAusenCont.SendJustificacion(SendJustificacionInputDto);
            txtTitulo.setText(null);
            txtDescripcion.setText(null);
            txtTitulo.clearFocus();
            txtDescripcion.clearFocus();
        } catch (Exception e)
        {throw new RuntimeException(e);}
    }
}