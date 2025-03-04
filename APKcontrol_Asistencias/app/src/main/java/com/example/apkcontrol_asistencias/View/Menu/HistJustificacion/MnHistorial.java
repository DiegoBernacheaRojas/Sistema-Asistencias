package com.example.apkcontrol_asistencias.View.Menu.HistJustificacion;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apkcontrol_asistencias.Controller.HistorialJustificacionController;
import com.example.apkcontrol_asistencias.Controller.HorarioSemanalController;
import com.example.apkcontrol_asistencias.R;

public class MnHistorial extends AppCompatActivity {

    private HistorialJustificacionController  HistorialJustificacionCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_historialjustificacion_lista);
        ListView listView = findViewById(R.id.FrmHistorialJust_LstData);
        HistorialJustificacionCont = new HistorialJustificacionController(this);
        HistorialJustificacionCont.setListViewHistorial(listView);
        HistorialJustificacionCont.List();
    }
}