package com.example.apkcontrol_asistencias.View.Menu.MarcAsistencia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.apkcontrol_asistencias.Controller.MarcarAsistenciaController;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaVerificarQRInputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaVerificarQROutputDto;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.View.MnLogin;
import com.example.apkcontrol_asistencias.View.MnMenu;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MnScan extends AppCompatActivity implements MarcarAsistenciaController.ScanQRListener {
    private   TextView textView;
    private  MarcarAsistenciaController marcarAsistenciaCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_marcarasistencia_scan);
        ImageView imageView = findViewById(R.id.Frm_marcarAsis_Scan);
         textView = findViewById(R.id.textView);
        Glide.with(this).asGif().load(R.drawable.escanearqr).into(imageView);
    }

    public void OnClick_BtnScanQR(View v){
        IntentIntegrator intentIntegrator= new IntentIntegrator(MnScan.this);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Escanear Codigo QR");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            String contents= intentResult.getContents();
            if(contents !=null){
                marcarAsistenciaCont = new MarcarAsistenciaController(MnScan.this);
                MarcarAsistenciaVerificarQRInputDto VerificarQRInputDto = new MarcarAsistenciaVerificarQRInputDto();
                VerificarQRInputDto.setCodeQR(contents);
                marcarAsistenciaCont.VerificarQR(VerificarQRInputDto);
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onMnFacialSuccess() {
        Intent intent = new Intent(MnScan.this, MnFacial.class);
        startActivity(intent);
        finish();  // Finalizar la actividad actual
    }
}