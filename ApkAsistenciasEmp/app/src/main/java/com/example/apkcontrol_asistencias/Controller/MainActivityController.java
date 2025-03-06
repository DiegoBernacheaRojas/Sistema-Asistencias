package com.example.apkcontrol_asistencias.Controller;

import static com.loopj.android.http.AsyncHttpClient.log;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.apkcontrol_asistencias.Conexion.ApisUrl;
import com.example.apkcontrol_asistencias.DAO.MenuDao.Dto.MenuGetEmpleadoOutputDto;
import com.example.apkcontrol_asistencias.DAO.MenuDao.Dto.MenuLogoutOutputDto;
import com.example.apkcontrol_asistencias.Util.Mensaje;

public class MainActivityController {
    private Context ct;

    public MainActivityController(Context c) {
        this.ct = c;
    }

    public void VerificarSesion(){

        SharedPreferences sharedPreferences = ct.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        /*if(token.equals("")){
            if(ct instanceof MainActivityController.MenuListener) {
                ((MainActivityController.MenuListener) ct).onLoginSuccess();
            }
        }else{
            if(ct instanceof MainActivityController.MenuListener) {
                ((MainActivityController.MenuListener) ct).onMenuSuccess();
            }
        }*/
        if(ct instanceof MainActivityController.MenuListener) {
            ((MainActivityController.MenuListener) ct).onLoginSuccess();
        }
    }
    public interface MenuListener {
        void onMenuSuccess();
        void onLoginSuccess();
    }
}
