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
import com.example.apkcontrol_asistencias.View.Menu.ActuDatos.MnActScanerFacial;

public class MnFacial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_marcarasistencia_facial);
        ImageView imageView = findViewById(R.id.Frm_MarcAsis_Facial);
        Glide.with(this).asGif().load(R.drawable.escaner_facial).into(imageView);
    }
    public void OnClick_BtnScanerFacial(View v) {
        Intent intent = new Intent(MnFacial.this, MnScanerFacial.class);
        startActivity(intent);
        finish();
    }
}