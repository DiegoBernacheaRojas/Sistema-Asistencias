package com.example.apkcontrol_asistencias.Controller;

import static com.loopj.android.http.AsyncHttpClient.log;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.apkcontrol_asistencias.Conexion.ApisUrl;
import com.example.apkcontrol_asistencias.Conexion.HttpManager;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaSendJustificacionOutputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaUpdateEvidenciaOutputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaVerificarFacialDataInputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaGenerarQROutputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaRegisterOutputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaSendJustificacionInputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaVerificarFacialDataOutputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaVerificarQRInputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.Dto.MarcarAsistenciaVerificarQROutputDto;
import com.example.apkcontrol_asistencias.DAO.MarcarAsistenciaDao.MarcarAsistenciaDao;
import com.example.apkcontrol_asistencias.Model.JustificacionModel;
import com.example.apkcontrol_asistencias.Model.RegistroMarcacionModel;
import com.example.apkcontrol_asistencias.Util.Mensaje;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class MarcarAsistenciaController implements MarcarAsistenciaDao {
    private HttpManager http = new HttpManager();
    public ApisUrl url;
    private Mensaje ms = null;
    private Context ct;
    private MarcarAsistenciaGenerarQROutputDto generarQROutputDto  = new MarcarAsistenciaGenerarQROutputDto();
    private MarcarAsistenciaVerificarQROutputDto verificarQROutputDto   = new MarcarAsistenciaVerificarQROutputDto();
    private MarcarAsistenciaRegisterOutputDto RegisterOutputDto = new MarcarAsistenciaRegisterOutputDto();
    private MarcarAsistenciaVerificarFacialDataOutputDto VerificarFacialDataOutputDto = new MarcarAsistenciaVerificarFacialDataOutputDto();
    private MarcarAsistenciaUpdateEvidenciaOutputDto UpdateEvidenciaOutputDto= new MarcarAsistenciaUpdateEvidenciaOutputDto();
    private MarcarAsistenciaSendJustificacionOutputDto SendJustificacionOutputDto = new MarcarAsistenciaSendJustificacionOutputDto();
    public MarcarAsistenciaController(Context c) {
        this.ct = c;
        this.ms = new Mensaje(ct);
    }

    public void VerificarQR(MarcarAsistenciaVerificarQRInputDto verificarQRInputDto){
        RequestParams params=new RequestParams();
        params.put("code_qr",verificarQRInputDto.getCodeQR());
        http.addToken(ct);
        http.post(url.VERIFICAR_QR, params, new AsyncHttpResponseHandler() {

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
                    verificarQROutputDto.setSuccess(json.getBoolean("success"));
                    verificarQROutputDto.setMessage(json.getString("message"));

                    if(!verificarQROutputDto.getSuccess()){
                        http.getToast("Mensaje: "+verificarQROutputDto.getMessage(), ct);
                    }else{
                        if(ct instanceof MarcarAsistenciaController.ScanQRListener) {
                            ((MarcarAsistenciaController.ScanQRListener) ct).onMnFacialSuccess();
                        }
                    }

                } catch (JSONException e)
                {http.getToast("Error JSON:"+verificarQROutputDto.getMessage(), ct);}
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
    public void GenerarQR() {
        RequestParams params=new RequestParams();
        http.addToken(ct);
        http.post(url.GENERAR_QR, params, new AsyncHttpResponseHandler() {

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
                    generarQROutputDto.setSuccess(json.getBoolean("success"));
                    generarQROutputDto.setMessage(json.getString("message"));

                    if(!generarQROutputDto.getSuccess()){
                        http.getToast("Mensaje: "+generarQROutputDto.getMessage(), ct);
                    }

                } catch (JSONException e)
                {http.getToast("Error JSON:"+generarQROutputDto.getMessage(), ct);}
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
    public void Register() {
        RequestParams params=new RequestParams();

        http.addToken(ct);
        http.post(url.REGISTER_ASISTENCIA, params, new AsyncHttpResponseHandler() {

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
                    RegisterOutputDto.setSuccess(json.getBoolean("success"));
                    RegisterOutputDto.setMessage(json.getString("message"));

                    if(RegisterOutputDto.getSuccess()){
                        JSONObject marcacion = json.optJSONObject("data");
                        String estado = marcacion.getString("estado");
                        RegistroMarcacionModel  registroMarcacion = new RegistroMarcacionModel(estado);

                        RegisterOutputDto.setData(registroMarcacion);
                        if(RegisterOutputDto.getData().getEstado().equals("PUNTUAL") || RegisterOutputDto.getData().getEstado().equals("SALIDA")){
                            if(ct instanceof MarcarAsistenciaController.ScanFacialListener) {
                                ((MarcarAsistenciaController.ScanFacialListener) ct).onMnDarEvidenciaSuccess();
                            }
                        } else if (RegisterOutputDto.getData().getEstado().equals("TARDE") || RegisterOutputDto.getData().getEstado().equals("ANTES")) {
                            if(ct instanceof MarcarAsistenciaController.ScanFacialListener) {
                                ((MarcarAsistenciaController.ScanFacialListener) ct).onMnOpcionJustSuccess();
                            }
                        }
                    }else{
                        http.getToast("Mensaje: "+RegisterOutputDto.getMessage(), ct);
                        if(ct instanceof MarcarAsistenciaController.ScanFacialListener) {
                            ((MarcarAsistenciaController.ScanFacialListener) ct).onMnMenuSuccess();
                        }
                    }

                } catch (JSONException e)
                {http.getToast("Error JSON:"+e, ct);}
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
    public void UpdateEvidencia(Bitmap image) {
        RequestParams params=new RequestParams();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        params.put("evidencia", encodedImage);
        http.addToken(ct);
        http.post(url.UPDATE_EVIDENCIA, params, new AsyncHttpResponseHandler() {

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
                    UpdateEvidenciaOutputDto.setSuccess(json.getBoolean("success"));
                    UpdateEvidenciaOutputDto.setMessage(json.getString("message"));

                    http.getToast("Mensaje: "+UpdateEvidenciaOutputDto.getMessage(), ct);
                    if(UpdateEvidenciaOutputDto.getSuccess()){
                        if(ct instanceof MarcarAsistenciaController.MenuListener) {
                            ((MarcarAsistenciaController.MenuListener) ct).onMnMenuSuccess();
                        }
                    }

                } catch (JSONException e)
                {http.getToast("Error JSON:"+UpdateEvidenciaOutputDto.getMessage(), ct);}
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
    public void SendJustifiacion(MarcarAsistenciaSendJustificacionInputDto sendJustificacionInputDto) {
        RequestParams params=new RequestParams();
        params.put("titulo",sendJustificacionInputDto.getTitulo());
        params.put("descripcion",sendJustificacionInputDto.getDescripcion());
        http.addToken(ct);
        http.post(url.REGISTRAR_JUSTIFICACION, params, new AsyncHttpResponseHandler() {

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
                    if(SendJustificacionOutputDto.getSuccess()){
                        if(ct instanceof MarcarAsistenciaController.MenuListener) {
                            ((MarcarAsistenciaController.MenuListener) ct).onMnMenuSuccess();
                        }
                    }

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

    @Override
    public void VerificarFacialData(Bitmap image) {
        RequestParams params=new RequestParams();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        log.d("facial_data",encodedImage);
        params.put("facial_data", encodedImage);

        http.addToken(ct);
        http.post(url.VERIFICAR_FACIAL_DATA, params, new AsyncHttpResponseHandler() {

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
                    VerificarFacialDataOutputDto.setSuccess(json.getBoolean("success"));
                    VerificarFacialDataOutputDto.setMessage(json.getString("message"));
                    VerificarFacialDataOutputDto.setVerificado(json.getBoolean("verificado"));

                    if(VerificarFacialDataOutputDto.getSuccess()){
                        if(VerificarFacialDataOutputDto.getVerificado()){
                            http.getToast("Mensaje: "+VerificarFacialDataOutputDto.getMessage(), ct);
                            Register();
                        }
                        else {
                            if(ct instanceof MarcarAsistenciaController.ScanFacialListener) {
                                ((MarcarAsistenciaController.ScanFacialListener) ct).onMnMenuSuccess();
                            }
                            http.getToast("Mensaje: "+VerificarFacialDataOutputDto.getMessage(), ct);
                        }

                    }else {
                        if(ct instanceof MarcarAsistenciaController.ScanFacialListener) {
                            ((MarcarAsistenciaController.ScanFacialListener) ct).onMnMenuSuccess();
                        }
                        http.getToast("Mensaje: "+VerificarFacialDataOutputDto.getMessage(), ct);
                    }


                } catch (JSONException e)
                {http.getToast("Error JSON:"+VerificarFacialDataOutputDto.getMessage(), ct);}
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

    public interface ScanQRListener {
        void onMnFacialSuccess();
    }
    public interface ScanFacialListener {
        void onMnDarEvidenciaSuccess();
        void onMnOpcionJustSuccess();
        void onMnMenuSuccess();
    }
    public interface MenuListener{
        void onMnMenuSuccess();
    }
}
