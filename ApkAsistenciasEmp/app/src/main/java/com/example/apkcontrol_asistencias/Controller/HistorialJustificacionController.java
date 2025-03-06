package com.example.apkcontrol_asistencias.Controller;

import android.content.Context;
import android.graphics.Color;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apkcontrol_asistencias.Conexion.ApisUrl;
import com.example.apkcontrol_asistencias.Conexion.HttpManager;
import com.example.apkcontrol_asistencias.DAO.HistorialJustificacionDao.Dto.HistorialJustificacionDetalleOutputDto;
import com.example.apkcontrol_asistencias.DAO.HistorialJustificacionDao.Dto.HistorialJustificacionListDataOutputDto;
import com.example.apkcontrol_asistencias.DAO.HistorialJustificacionDao.Dto.HistorialJustificacionListOutputDto;
import com.example.apkcontrol_asistencias.DAO.HistorialJustificacionDao.HistorialJustificacionDao;
import com.example.apkcontrol_asistencias.DAO.HorarioSemanalDao.Dto.HorarioSemanalListOutputDto;
import com.example.apkcontrol_asistencias.Model.HorarioDiaModel;
import com.example.apkcontrol_asistencias.Model.JustificacionModel;
import com.example.apkcontrol_asistencias.Util.ADPhistorialJustificacion;
import com.example.apkcontrol_asistencias.Util.ADPhorarioSemanal;
import com.example.apkcontrol_asistencias.Util.Mensaje;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HistorialJustificacionController implements HistorialJustificacionDao {
    private HttpManager http = new HttpManager();
    public ApisUrl url;
    private Mensaje ms = null;
    private Context ct;
    private HistorialJustificacionListOutputDto ListOutputDto = new HistorialJustificacionListOutputDto();
    private HistorialJustificacionDetalleOutputDto DetalleOutputDto  = new HistorialJustificacionDetalleOutputDto();
    private ListView listView;
    private TextView titulo,fecha,estado,razon,descripcion,comentario;
    public HistorialJustificacionController(Context c) {
        this.ct = c;
        this.ms = new Mensaje(ct);
    }

    @Override
    public void setListViewHistorial(ListView historial) {
        this.listView=historial;
    }

    @Override
    public void List() {
        RequestParams params=new RequestParams();
        http.addToken(ct);
        http.get(url.HISTORIAL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                ms.MProgressBarDato();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp=new String(responseBody);
                try {
                    JSONObject json = new JSONObject(resp);
                    ListOutputDto.setSuccess(json.getBoolean("success"));
                    ListOutputDto.setMessage(json.getString("message"));
                    JSONArray justificacionArray = json.getJSONArray("data");
                    List<HistorialJustificacionListDataOutputDto> justificacionList = new ArrayList<>();

                    for (int i = 0; i < justificacionArray.length(); i++) {
                        JSONObject justificacionJson = justificacionArray.getJSONObject(i);

                        int id = justificacionJson.getInt("id");
                        String titulo = justificacionJson.getString("titulo");
                        String fecha = justificacionJson.getString("fecha");
                        String estado = justificacionJson.getString("estado");
                        String razon = justificacionJson.getString("razon");

                        HistorialJustificacionListDataOutputDto Justificacion = new HistorialJustificacionListDataOutputDto(titulo,razon,estado,fecha,id);
                        justificacionList.add(Justificacion);
                    }

                    if(ListOutputDto.getSuccess()){
                        ListOutputDto.setData(justificacionList);
                        ADPhistorialJustificacion adapter = new ADPhistorialJustificacion(ct, justificacionList);
                        listView.setAdapter(adapter);
                    }else{
                        http.getToast("Mensaje: "+ListOutputDto.getMessage(), ct);
                    }


                } catch (JSONException e)
                {http.getToast("Error JSON:"+ListOutputDto.getMessage(), ct);}
                finally {ms.MCloseProgBar(true);}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ms.MCloseProgBar(true);
                if (responseBody != null) {
                    try {
                        String responseString = new String(responseBody);
                        JSONObject jsonResponse = new JSONObject(responseString);
                        String message = jsonResponse.optString("message", "Error desconocido.");
                        http.getToast("Error " + statusCode + ": " + message, ct);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        http.getToast("Error " + statusCode + ": Error al analizar la respuesta del servidor.", ct);
                    }
                } else {
                    http.getToast("Error " + statusCode + ": " + error.getMessage(), ct);
                }
            }

        });

    }
    public void Detalle(String id) {
        RequestParams params=new RequestParams();
        http.addToken(ct);
        String HistorialDetalleURL=url.HISTORIAL+"/"+id;
        http.get(HistorialDetalleURL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                ms.MProgressBarDato();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp=new String(responseBody);
                try {
                    JSONObject json = new JSONObject(resp);
                    DetalleOutputDto.setSuccess(json.getBoolean("success"));
                    DetalleOutputDto.setMessage(json.getString("message"));
                    JSONObject justificacion = json.optJSONObject("data");

                    String Jttitulo = justificacion.getString("titulo");
                    String Jtfecha = justificacion.getString("fecha");
                    String Jtestado = justificacion.getString("estado");
                    String Jtrazon = justificacion.getString("razon");
                    String Jtdescripcion = justificacion.getString("descripcion");
                    String Jtcomentario = justificacion.getString("comentario");

                    JustificacionModel Justificacion = new JustificacionModel(Jttitulo,Jtdescripcion,Jtrazon,Jtestado,Jtfecha,Jtcomentario);


                    if(DetalleOutputDto.getSuccess()){
                        DetalleOutputDto.setData(Justificacion);
                        titulo.setText(DetalleOutputDto.getData().getTitulo());
                        fecha.setText(DetalleOutputDto.getData().getFecha());

                        int color;
                        switch (DetalleOutputDto.getData().getEstado()) {
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
                        estado.setTextColor(color);
                        estado.setText(DetalleOutputDto.getData().getEstado());

                        switch (DetalleOutputDto.getData().getRazon()) {
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
                        razon.setTextColor(color);
                        razon.setText(DetalleOutputDto.getData().getRazon());


                        descripcion.setText(DetalleOutputDto.getData().getDescripcion());
                        comentario.setText(DetalleOutputDto.getData().getComentario());



                    }else{
                        http.getToast("Mensaje: "+DetalleOutputDto.getMessage(), ct);
                    }


                } catch (JSONException e)
                {http.getToast("Error JSON:"+DetalleOutputDto.getMessage(), ct);}
                finally {ms.MCloseProgBar(true);}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ms.MCloseProgBar(true);
                if (responseBody != null) {
                    try {
                        String responseString = new String(responseBody);
                        JSONObject jsonResponse = new JSONObject(responseString);
                        String message = jsonResponse.optString("message", "Error desconocido.");
                        http.getToast("Error " + statusCode + ": " + message, ct);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        http.getToast("Error " + statusCode + ": Error al analizar la respuesta del servidor.", ct);
                    }
                } else {
                    http.getToast("Error " + statusCode + ": " + error.getMessage(), ct);
                }
            }

        });

    }
    @Override
    public void setTextTitulo(TextView titulo) {
        this.titulo=titulo;
    }
    @Override
    public void setTextFecha(TextView fecha) {
        this.fecha=fecha;
    }
    @Override
    public void setTextEstado(TextView estado) {
        this.estado=estado;
    }
    @Override
    public void setTextRazon(TextView razon) {
        this.razon=razon;
    }
    @Override
    public void setTextDescripcion(TextView descripcion) {
        this.descripcion=descripcion;
    }
    @Override
    public void setTextComentario(TextView comentario) {
        this.comentario=comentario;
    }
}
