package com.example.apkcontrol_asistencias.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.apkcontrol_asistencias.Conexion.ApisUrl;
import com.example.apkcontrol_asistencias.Conexion.HttpManager;
import com.example.apkcontrol_asistencias.DAO.AuthDao.AuthDao;
import com.example.apkcontrol_asistencias.DAO.AuthDao.Dto.AuthLoginInputDto;
import com.example.apkcontrol_asistencias.DAO.AuthDao.Dto.AuthLoginOutputDto;
import com.example.apkcontrol_asistencias.View.MnMenu;
import com.example.apkcontrol_asistencias.Util.Mensaje;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AuthController implements AuthDao {

    private HttpManager http = new HttpManager();
    public ApisUrl url;
    private Mensaje ms = null;
    private Context ct;
    private AuthLoginOutputDto authLoginOutputDto = new AuthLoginOutputDto();

    public AuthController(Context c) {
        this.ct = c;
        this.ms = new Mensaje(ct);
    }

    public void ValidAccount(AuthLoginInputDto authLoginInputDto){
        RequestParams params=new RequestParams();
        params.put("email",authLoginInputDto.getEmail());
        params.put("password",authLoginInputDto.getPassword());

        http.post(url.LOGIN_ACCOUNT, params, new AsyncHttpResponseHandler() {

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
                    authLoginOutputDto.setToken(json.getString("token"));
                    authLoginOutputDto.setSuccess(json.getBoolean("success"));
                    authLoginOutputDto.setMessage(json.getString("message"));

                    if(authLoginOutputDto.getSuccess()){
                        SharedPreferences sharedPreferences = ct.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", authLoginOutputDto.getToken());
                        editor.apply();

                        if(ct instanceof AuthController.menuListener) {
                            ((AuthController.menuListener) ct).onMenuSuccess();
                        }

                    }
                } catch (JSONException e)
                {http.getToast("Error JSON:"+authLoginOutputDto.getMessage(), ct);}
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
    public void ServicioTecnico(){
        String phoneNumber = "+51922834945"; // Reemplaza con el número de teléfono de WhatsApp al que deseas enviar el mensaje
        String message = "Hola, necesito soporte técnico."; // Mensaje opcional

        // Crear el Intent
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // Definir el número de teléfono y el mensaje (opcional)
        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message;

        // Establecer el data del Intent con el URL
        intent.setData(Uri.parse(url));

        // Obtener el PackageManager desde el contexto ct
        PackageManager packageManager = ct.getPackageManager();

        // Comprobar si WhatsApp está instalado en el dispositivo
        if (intent.resolveActivity(packageManager) != null) {
            ct.startActivity(intent);
        } else {
            // Si WhatsApp no está instalado, mostrar un mensaje al usuario
            http.getToast("WhatsApp no está instalado en este dispositivo.", ct);
        }
    }
    public interface menuListener {
        void onMenuSuccess();
    }

}
