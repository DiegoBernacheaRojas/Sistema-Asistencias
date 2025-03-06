package com.example.apkcontrol_asistencias.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apkcontrol_asistencias.Controller.AuthController;
import com.example.apkcontrol_asistencias.Controller.HorarioSemanalController;
import com.example.apkcontrol_asistencias.Controller.MarcarAsistenciaController;
import com.example.apkcontrol_asistencias.Controller.MenuController;
import com.example.apkcontrol_asistencias.View.Menu.ActuDatos.MnActFacial;
import com.example.apkcontrol_asistencias.View.Menu.HistJustificacion.MnHistorial;
import com.example.apkcontrol_asistencias.View.Menu.HoraSemanal.MnHorarioSemanal;
import com.example.apkcontrol_asistencias.View.Menu.Justificar.MnJusticarAusencia;
import com.example.apkcontrol_asistencias.View.Menu.MarcAsistencia.MnScan;
import com.example.apkcontrol_asistencias.View.Menu.Perfil.MnPerfil;
import com.example.apkcontrol_asistencias.R;

public class MnMenu extends AppCompatActivity implements MenuController.LogoutListener{


    private TextView txtnombre;
    private de.hdodenhof.circleimageview.CircleImageView imageFoto;
    private MenuController MenuCont;
    private MarcarAsistenciaController marcarAsistenciaCont;
    private BroadcastReceiver actualizarImagenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Aquí actualizas la imagen en MnMenu
            MenuCont.GetEmpleado();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuasistencia);
        txtnombre = (TextView) findViewById(R.id.txtNombreEmpleado);
        imageFoto = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.Frm_Perfil_ImgFoto);
        MenuCont=new MenuController(MnMenu.this);
        MenuCont.setimage(imageFoto);
        MenuCont.setNombreCompleto(txtnombre);
        MenuCont.GetEmpleado();
        LocalBroadcastManager.getInstance(this).registerReceiver(actualizarImagenReceiver, new IntentFilter("actualizar_imagen"));
    }

    public void OnClick_ImagePerfil(View v){

        startActivity(new Intent(MnMenu.this, MnPerfil.class));

    }

    public void OnClick_JustificarAusencia(View v){

        startActivity(new Intent(MnMenu.this, MnJusticarAusencia.class));

    }

    public void OnClick_MarcarAsistencia(View v){


        startActivity(new Intent(MnMenu.this, MnScan.class));
        
    }

    public void OnClick_HorarioSemanal(View v){

        startActivity(new Intent(MnMenu.this, MnHorarioSemanal.class));
    }

    public void OnClick_HistorialJustificacion(View v){

        startActivity(new Intent(MnMenu.this, MnHistorial.class));

    }

    public void OnClick_ActualizarDatos(View v){

        startActivity(new Intent(MnMenu.this, MnActFacial.class));

    }

    public void OnClick_Logout(View v){
        MenuCont=new MenuController(MnMenu.this);
        MenuCont.Logout();
    }
    @Override
    public void onLogoutSuccess() {
        Intent intent = new Intent(MnMenu.this, MnLogin.class);
        startActivity(intent);
        finish();  // Finalizar la actividad actual
    }
    @Override
    protected void onDestroy() {
        // Desregistrar el BroadcastReceiver cuando la actividad se destruye
        LocalBroadcastManager.getInstance(this).unregisterReceiver(actualizarImagenReceiver);
        super.onDestroy();
    }
}