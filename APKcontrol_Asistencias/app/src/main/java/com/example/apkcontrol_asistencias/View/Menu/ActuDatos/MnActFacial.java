package com.example.apkcontrol_asistencias.View.Menu.ActuDatos;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.View.MnMenu;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.io.IOException;

public class MnActFacial extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_actualizardatos_scanfacila);
        ImageView imageView = findViewById(R.id.Frm_ActualizarDatos_Facial);
        Glide.with(this).asGif().load(R.drawable.escaner_facial).into(imageView);

    }
    public void OnClick_BtnScanerFacial(View v) {
        Intent intent = new Intent(MnActFacial.this, MnActScanerFacial.class);
        startActivity(intent);
        finish();
    }


}