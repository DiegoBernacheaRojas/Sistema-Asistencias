package com.example.apkcontrol_asistencias.View.Menu.MarcAsistencia;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.apkcontrol_asistencias.Controller.MarcarAsistenciaController;
import com.example.apkcontrol_asistencias.Controller.PerfilController;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.View.Menu.Perfil.MnPerfil;

public class MnDarEvidencia extends AppCompatActivity implements MarcarAsistenciaController.MenuListener {

    private  MarcarAsistenciaController marcarAsistenciaCont;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_marcarasistencia_dar_evidencia);
        ImageView imageView = findViewById(R.id.Frm_MarcAsis_correcto);
        Glide.with(this).asGif().load(R.drawable.icons8_correcto).into(imageView);
    }
    public void  OnClick_BtnMenu(View v){
        finish();
    }
    public void OnClick_BtnEvidencia(View v){
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

                marcarAsistenciaCont = new MarcarAsistenciaController(MnDarEvidencia.this);
                marcarAsistenciaCont.UpdateEvidencia(imgBitmap);

            }
        }
    }

    @Override
    public void onMnMenuSuccess() {
        finish();
    }
}