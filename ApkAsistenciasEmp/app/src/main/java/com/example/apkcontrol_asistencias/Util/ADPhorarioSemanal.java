package com.example.apkcontrol_asistencias.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apkcontrol_asistencias.Model.HorarioDiaModel;
import com.example.apkcontrol_asistencias.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ADPhorarioSemanal extends ArrayAdapter<HorarioDiaModel> {

    private Context context;
    private List<HorarioDiaModel> horarioDiaList;

    public ADPhorarioSemanal(Context context, List<HorarioDiaModel> horarioDiaList) {
        super(context, R.layout.conf_lhorario, horarioDiaList);
        this.context = context;
        this.horarioDiaList = horarioDiaList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.conf_lhorario, parent, false);
        }

        HorarioDiaModel horarioDia = horarioDiaList.get(position);

        TextView lblFecha = convertView.findViewById(R.id.FrmconfHora_lblFecha);
        TextView lblIngreso = convertView.findViewById(R.id.FrmConfhora_LblIngreso);
        TextView lblSalida = convertView.findViewById(R.id.FrmConfhora_LblSalida);
        TextView txtsalida = convertView.findViewById(R.id.txtsalida);
        TextView txtingreso = convertView.findViewById(R.id.txtingreso);

        if(horarioDia.getHoraEntrada().equals("") && horarioDia.getHoraSalida().equals("") && !horarioDia.getFeriado() && !horarioDia.getVacaciones()){
            lblFecha.setText(horarioDia.getDia());
            lblIngreso.setText( horarioDia.getMensaje());
            txtsalida.setText("-");
            txtingreso.setText("Nota:");
            lblIngreso.setTextSize(17);
            lblSalida.setText( "");
        } else if (horarioDia.getFeriado()) {
            lblFecha.setText(horarioDia.getDia());
            lblIngreso.setText( "Feriado");
            lblSalida.setText( horarioDia.getMensaje());
            lblSalida.setTextSize(17);
            txtsalida.setText("Descripción:");
            txtingreso.setText("Nota:");
        }else if(horarioDia.getVacaciones()){
            lblFecha.setText(horarioDia.getDia());
            lblIngreso.setText( "vacaciones");
            lblSalida.setText( horarioDia.getMensaje());
            lblSalida.setTextSize(17);
            txtsalida.setText("Descripción:");
            txtingreso.setText("Nota:");
        }else{
            txtsalida.setText("Salida:");
            txtingreso.setText("Ingreso:");
            lblFecha.setText(horarioDia.getDia());
            lblIngreso.setText( horarioDia.getHoraEntrada());
            lblSalida.setText( horarioDia.getHoraSalida());
        }


        return convertView;
    }
}