package com.example.apkcontrol_asistencias.Controller;

import static com.loopj.android.http.AsyncHttpClient.log;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.apkcontrol_asistencias.Conexion.ApisUrl;
import com.example.apkcontrol_asistencias.Conexion.HttpManager;
import com.example.apkcontrol_asistencias.DAO.ActualizarDatosDao.ActualizarDatosDao;
import com.example.apkcontrol_asistencias.DAO.ActualizarDatosDao.Dto.ActualizarDatosUpdateDatosFacialesInputDto;
import com.example.apkcontrol_asistencias.DAO.ActualizarDatosDao.Dto.ActualizarDatosUpdateDatosFacialesOutputDto;
import com.example.apkcontrol_asistencias.DAO.JustificarAusenciaDao.Dto.JustificarAusenciaSendJustificacionInputDto;
import com.example.apkcontrol_asistencias.DAO.JustificarAusenciaDao.Dto.JustificarAusenciaSendJustificacionOutputDto;
import com.example.apkcontrol_asistencias.Util.Mensaje;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class ActualizarDatosController implements ActualizarDatosDao {


    private HttpManager http = new HttpManager();
    public ApisUrl url;
    private Mensaje ms = null;
    private Context ct;
    private ActualizarDatosUpdateDatosFacialesOutputDto UpdateDatosFacialesOutputDto  = new ActualizarDatosUpdateDatosFacialesOutputDto();

    public ActualizarDatosController(Context c) {
        this.ct = c;
        this.ms = new Mensaje(ct);
    }

    public void UpdateDatosFaciales(Bitmap image){
        RequestParams params=new RequestParams();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        params.put("facial_data", encodedImage);
        http.addToken(ct);
        http.post(url.UPDATE_FACIAL_DATA, params, new AsyncHttpResponseHandler() {

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
                    UpdateDatosFacialesOutputDto.setSuccess(json.getBoolean("success"));
                    UpdateDatosFacialesOutputDto.setMessage(json.getString("message"));
                    http.getToast(UpdateDatosFacialesOutputDto.getMessage(), ct);
                    if(ct instanceof ActualizarDatosController.actFacialListener) {
                        ((ActualizarDatosController.actFacialListener) ct).onMnActFacialSuccess();
                    }

                } catch (JSONException e)
                {http.getToast("Error JSON:"+UpdateDatosFacialesOutputDto.getMessage(), ct);}
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
    public interface actFacialListener {
        void onMnActFacialSuccess();
    }
}
