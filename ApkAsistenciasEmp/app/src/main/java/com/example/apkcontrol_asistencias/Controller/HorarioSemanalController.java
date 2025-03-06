package com.example.apkcontrol_asistencias.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apkcontrol_asistencias.Conexion.ApisUrl;

import com.example.apkcontrol_asistencias.Conexion.HttpManager;
import com.example.apkcontrol_asistencias.DAO.AuthDao.Dto.AuthLoginOutputDto;
import com.example.apkcontrol_asistencias.DAO.HorarioSemanalDao.Dto.HorarioSemanalListOutputDto;
import com.example.apkcontrol_asistencias.DAO.HorarioSemanalDao.HorarioSemanalDao;
import com.example.apkcontrol_asistencias.Model.HorarioDiaModel;
import com.example.apkcontrol_asistencias.Util.ADPhorarioSemanal;
import com.example.apkcontrol_asistencias.Util.Mensaje;
import com.example.apkcontrol_asistencias.View.MnMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HorarioSemanalController implements HorarioSemanalDao {

    private HttpManager http = new HttpManager();
    public ApisUrl url;
    private Mensaje ms = null;
    private Context ct;
    private HorarioSemanalListOutputDto HorarioSemanalList = new HorarioSemanalListOutputDto();
    private ListView listView;

    public HorarioSemanalController(Context c, ListView listView) {
        this.ct = c;
        this.ms = new Mensaje(ct);
        this.listView = listView;
    }

    @Override
    public void List() {
        RequestParams params=new RequestParams();
        http.addToken(ct);
        http.get(url.GET_HORARIO, params, new AsyncHttpResponseHandler() {

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
                    HorarioSemanalList.setSuccess(json.getBoolean("success"));
                    HorarioSemanalList.setMessage(json.getString("message"));
                    JSONArray horarioDiasArray = json.getJSONArray("horarioSemanal");
                    List<HorarioDiaModel> horarioDiaList = new ArrayList<>();

                    for (int i = 0; i < horarioDiasArray.length(); i++) {
                        JSONObject horarioDiaJson = horarioDiasArray.getJSONObject(i);

                        String dia = horarioDiaJson.getString("dia");
                        String horaEntrada = horarioDiaJson.getString("hora_entrada");
                        String horaSalida = horarioDiaJson.getString("hora_salida");
                        boolean feriado = horarioDiaJson.getBoolean("feriado");
                        boolean vacaciones = horarioDiaJson.getBoolean("vacaciones");
                        String mensaje = horarioDiaJson.getString("mensaje");

                        HorarioDiaModel horarioDia = new HorarioDiaModel(dia, horaEntrada, horaSalida, mensaje,feriado,vacaciones);
                        horarioDiaList.add(horarioDia);
                    }

                    HorarioSemanalList.setHorarioDias(horarioDiaList);
                    ADPhorarioSemanal adapter = new ADPhorarioSemanal(ct, horarioDiaList);
                    listView.setAdapter(adapter);

                } catch (JSONException e)
                {http.getToast("Error JSON:"+HorarioSemanalList.getMessage(), ct);}
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


}