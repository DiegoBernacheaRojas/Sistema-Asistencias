package com.example.apkcontrol_asistencias.View.Menu.MarcAsistencia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.apkcontrol_asistencias.Controller.JustificarAusenciaController;
import com.example.apkcontrol_asistencias.Controller.MarcarAsistenciaController;
import com.example.apkcontrol_asistencias.DAO.JustificarAusenciaDao.Dto.JustificarAusenciaSendJustificacionInputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaSendJustificacionInputDto;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.View.MnLogin;
import com.example.apkcontrol_asistencias.View.MnMenu;

public class MnJustificar extends AppCompatActivity implements  MarcarAsistenciaController.MenuListener{
    private MarcarAsistenciaController MarcarAsistenciaCont;
    private TextView txtTitulo, txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_marcacasistencia_justificar);
        txtTitulo = (EditText) findViewById(R.id.Frm_MarcarAistencia_Just_Titulo);
        txtDescripcion = (EditText) findViewById(R.id.Frm_MarcarAistencia_Just_Descripcion);
        MarcarAsistenciaCont=new MarcarAsistenciaController(MnJustificar.this);

    }
    public void OnClick_BtnCancelar(View v){
        finish();
    }
    public void OnClick_BtnEnviar(View v){
        try {
            MarcarAsistenciaSendJustificacionInputDto SendJustificacionInputDto= new MarcarAsistenciaSendJustificacionInputDto(txtTitulo.getText().toString(),txtDescripcion.getText().toString());
            MarcarAsistenciaCont.SendJustifiacion(SendJustificacionInputDto);
        } catch (Exception e)
        {throw new RuntimeException(e);}
    }

    @Override
    public void onMnMenuSuccess() {
        finish();
    }
}

