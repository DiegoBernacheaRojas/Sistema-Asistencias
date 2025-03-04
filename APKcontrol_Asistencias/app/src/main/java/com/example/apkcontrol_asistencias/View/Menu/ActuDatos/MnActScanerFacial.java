package com.example.apkcontrol_asistencias.View.Menu.ActuDatos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;

import android.provider.MediaStore;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.apkcontrol_asistencias.Controller.ActualizarDatosController;
import com.example.apkcontrol_asistencias.R;

public class MnActScanerFacial extends AppCompatActivity implements ActualizarDatosController.actFacialListener {

    private ActualizarDatosController ActualizarDatosCont;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_actualizardatos_scaner_facial);
        ImageView imageView = findViewById(R.id.Frm_carga);
        Glide.with(this).asGif().load(R.drawable.carga).into(imageView);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            abrirCamara();
        }
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imgBitmap = (Bitmap) extras.get("data");
                ActualizarDatosCont=new ActualizarDatosController(MnActScanerFacial.this);
                ActualizarDatosCont.UpdateDatosFaciales(imgBitmap);

            }
        }
    }

    @Override
    public void onMnActFacialSuccess() {
        Intent intent = new Intent(MnActScanerFacial.this, MnActConfirmacion.class);
        startActivity(intent);
        finish();
    }
}