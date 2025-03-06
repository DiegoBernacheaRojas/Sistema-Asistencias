package com.example.apkcontrol_asistencias.DAO.HistorialJustificacionDao;

import android.widget.ListView;
import android.widget.TextView;

public interface HistorialJustificacionDao {
    void List();
    void Detalle(String id);
    void setTextTitulo(TextView titulo);
    void setTextFecha(TextView fecha);
    void setTextEstado(TextView estado);
    void setTextRazon(TextView razon);
    void setTextDescripcion(TextView descripcion);
    void setTextComentario(TextView comentario);
    void setListViewHistorial(ListView Historial);
}
