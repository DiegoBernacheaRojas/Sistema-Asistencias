package com.example.apkcontrol_asistencias.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.apkcontrol_asistencias.Conexion.ApisUrl;
import com.example.apkcontrol_asistencias.Conexion.HttpManager;
import com.example.apkcontrol_asistencias.DAO.AuthDao.Dto.AuthLoginInputDto;
import com.example.apkcontrol_asistencias.DAO.AuthDao.Dto.AuthLoginOutputDto;
import com.example.apkcontrol_asistencias.DAO.JustificarAusenciaDao.Dto.JustificarAusenciaSendJustificacionInputDto;
import com.example.apkcontrol_asistencias.DAO.JustificarAusenciaDao.Dto.JustificarAusenciaSendJustificacionOutputDto;
import com.example.apkcontrol_asistencias.Util.Mensaje;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class JustificarAusenciaController {

    private HttpManager http = new HttpManager();
    public ApisUrl url;
    private Mensaje ms = null;
    private Context ct;
    private JustificarAusenciaSendJustificacionOutputDto SendJustificacionOutputDto  = new JustificarAusenciaSendJustificacionOutputDto();

    public JustificarAusenciaController(Context c) {
        this.ct = c;
        this.ms = new Mensaje(ct);
    }

    public void SendJustificacion(JustificarAusenciaSendJustificacionInputDto sendJustificacionInput){
        RequestParams params=new RequestParams();
        params.put("titulo",sendJustificacionInput.getTitulo());
        params.put("descripcion",sendJustificacionInput.getDescripcion());
        http.addToken(ct);
        http.post(url.REGISTER_AUSENCIA, params, new AsyncHttpResponseHandler() {

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
                    SendJustificacionOutputDto.setSuccess(json.getBoolean("success"));
                    SendJustificacionOutputDto.setMessage(json.getString("message"));

                    http.getToast("Mensaje: "+SendJustificacionOutputDto.getMessage(), ct);

                } catch (JSONException e)
                {http.getToast("Error JSON:"+SendJustificacionOutputDto.getMessage(), ct);}
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
