package com.example.apkcontrol_asistencias.View.Menu.MarcAsistencia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.View.Menu.Perfil.MnPerfil;
import com.example.apkcontrol_asistencias.View.MnLogin;
import com.example.apkcontrol_asistencias.View.MnMenu;
import com.google.zxing.integration.android.IntentIntegrator;

public class MnOpcionJust extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_marcarasistencia_opcionjustificar);
        ImageView imageView = findViewById(R.id.Frm_MarcAsis_correcto);
        Glide.with(this).asGif().load(R.drawable.icons8_correcto).into(imageView);
    }

    public void OnClick_BtnJustificar(View v){
        Intent intent = new Intent(MnOpcionJust.this, MnJustificar.class);
        startActivity(intent);
        finish();
    }
    public void  OnClick_BtnMenu(View v){
        finish();
    }
}