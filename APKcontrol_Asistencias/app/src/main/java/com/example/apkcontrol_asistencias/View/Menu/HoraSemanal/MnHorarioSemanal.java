package com.example.apkcontrol_asistencias.View.Menu.HoraSemanal;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apkcontrol_asistencias.Controller.AuthController;
import com.example.apkcontrol_asistencias.Controller.HorarioSemanalController;
import com.example.apkcontrol_asistencias.DAO.HorarioSemanalDao.Dto.HorarioSemanalListOutputDto;
import com.example.apkcontrol_asistencias.Model.HorarioDiaModel;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.Util.ADPhorarioSemanal;

import java.util.List;

public class MnHorarioSemanal extends AppCompatActivity {

    private HorarioSemanalController HorarioSemanalCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_horariosemanal);
        ListView listView = findViewById(R.id.Frm_ListViewHorario);
        HorarioSemanalCont = new HorarioSemanalController(this,listView);
        HorarioSemanalCont.List();


    }

}