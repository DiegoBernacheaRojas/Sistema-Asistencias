package com.example.apkcontrol_asistencias.View.Menu.HistJustificacion;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apkcontrol_asistencias.Controller.HistorialJustificacionController;
import com.example.apkcontrol_asistencias.R;

public class MnHistorialDetalle extends AppCompatActivity {

    private HistorialJustificacionController HistorialJustificacionCont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_historialjustificacion_detalle);
        TextView lblTitulo = findViewById(R.id.lblTitulo);
        TextView lblFecha = findViewById(R.id.lblFecha);
        TextView lblEstado = findViewById(R.id.Frm_lblEstado);
        TextView lblRazon = findViewById(R.id.Frm_lblRazon);
        TextView lblDescripcion = findViewById(R.id.lblDescripcion);
        TextView lblComentario = findViewById(R.id.lblComentario);
        int idJustificacion = getIntent().getIntExtra("ID_JUSTIFICACION", 0);
        HistorialJustificacionCont = new HistorialJustificacionController(this);
        HistorialJustificacionCont.setTextTitulo(lblTitulo);
        HistorialJustificacionCont.setTextFecha(lblFecha);
        HistorialJustificacionCont.setTextEstado(lblEstado);
        HistorialJustificacionCont.setTextRazon(lblRazon);
        HistorialJustificacionCont.setTextDescripcion(lblDescripcion);
        HistorialJustificacionCont.setTextComentario(lblComentario);

        HistorialJustificacionCont.Detalle(String.valueOf(idJustificacion));
    }
    public void OnClick_BtnRegresar(View v){
        finish();
    }
}

