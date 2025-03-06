package com.example.apkcontrol_asistencias.Conexion;

import static com.loopj.android.http.AsyncHttpClient.log;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;

public class HttpManager {
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    public static void addToken(Context context) {
        // Recuperar el token de las preferencias compartidas
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        client.addHeader("Authorization", "Bearer " + token);
    }

    public void getToast(String men, Context ct){
        Toast.makeText(ct,men,Toast.LENGTH_SHORT).show();
    }

    public void getJSON(String resp, Context ct){
        try {
            JSONArray json=new JSONArray(resp);
            for (int i = 0; i < json.length(); i++) {
                json.getJSONObject(i).getString("Success");
            }
        } catch (JSONException e)
        {getToast("Error JSON:"+e.getMessage(), ct);}
    }
}
