package com.example.apkcontrol_asistencias.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apkcontrol_asistencias.DAO.HistorialJustificacionDao.Dto.HistorialJustificacionListDataOutputDto;
import com.example.apkcontrol_asistencias.Model.HorarioDiaModel;
import com.example.apkcontrol_asistencias.Model.JustificacionModel;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.View.Menu.HistJustificacion.MnHistorialDetalle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ADPhistorialJustificacion extends ArrayAdapter<HistorialJustificacionListDataOutputDto> {

    private Context context;
    private List<HistorialJustificacionListDataOutputDto> justificarList;

    public ADPhistorialJustificacion(Context context, List<HistorialJustificacionListDataOutputDto> justificarList) {
        super(context, R.layout.conf_lmensaje, justificarList);
        this.context = context;
        this.justificarList = justificarList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.conf_lmensaje, parent, false);
        }

        HistorialJustificacionListDataOutputDto Justificacion = justificarList.get(position);

        TextView Lbltitulo = convertView.findViewById(R.id.FrmConfmen_Lbltitulo);
        TextView Lblfecha = convertView.findViewById(R.id.FrmConfmen_Lblfecha);
        TextView Lblestado = convertView.findViewById(R.id.FrmConfmen_Lblestado);
        TextView Lblrazon = convertView.findViewById(R.id.FrmConfmen_Lblrazon);
        TextView Lblid = convertView.findViewById(R.id.FrmConfmen_Lblid);

        Lbltitulo.setText(Justificacion.getTitulo());
        Lblfecha.setText( Justificacion.getFecha());
        // Establecer color de texto para Lblestado
        int color;
        switch (Justificacion.getEstado()) {
            case "ESPERA":
                color = Color.parseColor("#A3A5A3");  // Color naranja
                break;
            case "APROBADO":
                color = Color.parseColor("#008000");  // Color verde
                break;
            case "RECHAZADO":
                color = Color.parseColor("#FF0000");  // Color rojo
                break;
            default:
                color = Color.parseColor("#000000");  // Color negro por defecto
                break;
        }
        Lblestado.setTextColor(color);
        Lblestado.setText(Justificacion.getEstado());

        switch (Justificacion.getRazon()) {
            case "AUSENCIA":
                color = Color.parseColor("#FF00BCD4");  // Color naranja
                break;
            case "ANTES":
                color = Color.parseColor("#FFFFCB2D");  // Color verde
                break;
            case "TARDE":
                color = Color.parseColor("#FFE91E63");  // Color rojo
                break;
            default:
                color = Color.parseColor("#000000");  // Color negro por defecto
                break;
        }
        Lblrazon.setTextColor(color);
        Lblrazon.setText(Justificacion.getRazon());
        Lblid.setText(String.valueOf(Justificacion.getId()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el ID de la justificación
                int id = Justificacion.getId();

                // Iniciar el activity MnHistorialDetalle y pasar el ID como extra
                Intent intent = new Intent(context, MnHistorialDetalle.class);
                intent.putExtra("ID_JUSTIFICACION", id);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}