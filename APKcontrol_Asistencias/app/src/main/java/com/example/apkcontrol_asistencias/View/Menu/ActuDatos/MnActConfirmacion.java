package com.example.apkcontrol_asistencias.View.Menu.ActuDatos;

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
import com.example.apkcontrol_asistencias.View.Menu.MarcAsistencia.MnJustificar;
import com.example.apkcontrol_asistencias.View.Menu.MarcAsistencia.MnOpcionJust;
import com.example.apkcontrol_asistencias.View.MnMenu;

public class MnActConfirmacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_actualizardatos_confirmacion);
        ImageView imageView = findViewById(R.id.Frm_ActualizarDatos_Correcto);
        Glide.with(this).asGif().load(R.drawable.icons8_correcto).into(imageView);
    }

    public void OnClick_BtnMenu(View v){
        Intent intent = new Intent(MnActConfirmacion.this, MnMenu.class);
        startActivity(intent);
        finish();
    }
}