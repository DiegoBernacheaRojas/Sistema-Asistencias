package com.example.apkcontrol_asistencias.View.Menu.Perfil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.apkcontrol_asistencias.Controller.PerfilController;
import com.example.apkcontrol_asistencias.R;

public class MnPerfil extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private de.hdodenhof.circleimageview.CircleImageView ImgFoto;
    private TextView txtnombre,txttelefono,txtcargo,txtemail,txtarea;
    private PerfilController  PerfilCont;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_perfil_detalles);
        ImgFoto = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.Frm_Perfil_ImgFoto);
        txtnombre = (TextView)findViewById(R.id.textNombreCompleto);
        txttelefono = (TextView)findViewById(R.id.textTelefono);
        txtcargo = (TextView)findViewById(R.id.textCargo);
        txtemail = (TextView)findViewById(R.id.textEmail);
        txtarea = (TextView)findViewById(R.id.textArea);

        PerfilCont=new PerfilController(MnPerfil.this);
        PerfilCont.setImageFoto(ImgFoto);
        PerfilCont.setTxtName(txtnombre);
        PerfilCont.setTextTelefono(txttelefono);
        PerfilCont.setTxtCargo(txtcargo);
        PerfilCont.setTxtEmail(txtemail);
        PerfilCont.setTxtArea(txtarea);

        PerfilCont.GetDatosEmpleado();

    }

    public void OnClick_CamaraPerfil(View v) {
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
                ImgFoto.setImageBitmap(imgBitmap);
                PerfilCont=new PerfilController(MnPerfil.this);
                PerfilCont.updateFotoPerfil(imgBitmap);

            }
        }
    }
}