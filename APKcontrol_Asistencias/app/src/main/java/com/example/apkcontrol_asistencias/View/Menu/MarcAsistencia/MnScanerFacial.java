package com.example.apkcontrol_asistencias.View.Menu.MarcAsistencia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.apkcontrol_asistencias.Controller.ActualizarDatosController;
import com.example.apkcontrol_asistencias.Controller.MarcarAsistenciaController;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaVerificarFacialDataInputDto;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.View.Menu.ActuDatos.MnActScanerFacial;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class MnScanerFacial extends AppCompatActivity implements MarcarAsistenciaController.ScanFacialListener {

    private MarcarAsistenciaController MarcarAsistenciaCont;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_marcarasistencia_scaner_facial);
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
                MarcarAsistenciaCont=new MarcarAsistenciaController(MnScanerFacial.this);
                MarcarAsistenciaCont.VerificarFacialData(imgBitmap);

            }
        }
    }




    @Override
    public void onMnDarEvidenciaSuccess() {
        Intent intent = new Intent(MnScanerFacial.this, MnDarEvidencia.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMnOpcionJustSuccess() {
        Intent intent = new Intent(MnScanerFacial.this, MnOpcionJust.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMnMenuSuccess() {
        finish();
    }


}
