package com.example.apkcontrol_asistencias.Controller;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.apkcontrol_asistencias.Conexion.ApisUrl;
import com.example.apkcontrol_asistencias.Conexion.HttpManager;
import com.example.apkcontrol_asistencias.DAO.HorarioSemanalDao.Dto.HorarioSemanalListOutputDto;
import com.example.apkcontrol_asistencias.DAO.MenuDao.Dto.MenuGetEmpleadoOutputDto;
import com.example.apkcontrol_asistencias.DAO.MenuDao.Dto.MenuLogoutOutputDto;
import com.example.apkcontrol_asistencias.DAO.MenuDao.MenuDao;
import com.example.apkcontrol_asistencias.Model.AreaModel;
import com.example.apkcontrol_asistencias.Model.CargoModel;
import com.example.apkcontrol_asistencias.Model.EmpleadoModel;
import com.example.apkcontrol_asistencias.Model.HorarioDiaModel;
import com.example.apkcontrol_asistencias.Model.UserModel;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.Util.ADPhorarioSemanal;
import com.example.apkcontrol_asistencias.Util.Mensaje;
import com.example.apkcontrol_asistencias.View.MnLogin;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class MenuController implements MenuDao {

    private HttpManager http = new HttpManager();
    public ApisUrl url;
    private Mensaje ms = null;
    private Context ct;
    private MenuGetEmpleadoOutputDto MenuGetEmpleado = new MenuGetEmpleadoOutputDto();
    private MenuLogoutOutputDto MenuLogout = new MenuLogoutOutputDto();
    private de.hdodenhof.circleimageview.CircleImageView image;
    private TextView nombrecompleto;

    public MenuController(Context c) {
        this.ct = c;
        this.ms = new Mensaje(ct);
    }

    @Override
    public void GetEmpleado() {
        RequestParams params=new RequestParams();
        http.addToken(ct);
        http.get(url.GET_EMPLOYEE, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                ms.MProgressBarDato();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp=new String(responseBody);
                try {
                    int id=0;
                    String foto="";
                    String nombres="";
                    String apellidos="";
                    String telefono="";
                    String dni="";

                    JSONObject area;
                    int areaId=0;
                    String areaImage="";
                    String areaNombre="";
                    String areaDescripcion="";

                    JSONObject cargo;
                    int cargoId=0;
                    String cargoNombre="";
                    String cargoDescripcion="";

                    JSONObject user;
                    int userId=0;
                    String userName="";
                    String userEmail="";
                    String userRole="";

                    JSONObject json = new JSONObject(resp);
                    MenuGetEmpleado.setSuccess(json.getBoolean("success"));
                    MenuGetEmpleado.setMessage(json.getString("message"));
                    JSONObject data = json.optJSONObject("data");
                    if(data != null) {
                         id = data.getInt("id");
                         foto = url.BASE_URL + data.getString("foto");
                         nombres = data.getString("nombres");
                         apellidos = data.getString("apellidos");
                         telefono = data.getString("telefono");
                         dni = data.getString("dni");
                         area = data.getJSONObject("area");
                        if (area != null) {
                             areaId = area.getInt("id");
                             areaImage = url.BASE_URL + area.getString("image");
                             areaNombre = area.getString("nombre");
                             areaDescripcion = area.getString("descripcion");
                        }
                         cargo = data.getJSONObject("cargo");
                        if (cargo != null) {
                             cargoId = cargo.getInt("id");
                             cargoNombre = cargo.getString("nombre");
                             cargoDescripcion = cargo.getString("descripcion");
                        }
                         user = data.getJSONObject("user");
                        if (user != null) {
                             userId = user.getInt("id");
                             userName = user.getString("name");
                             userEmail = user.getString("email");
                             userRole = user.getString("role");
                        }
                    }

                    AreaModel areaM=new AreaModel(areaId,areaImage,areaNombre,areaDescripcion);
                    CargoModel cargoM=new CargoModel(cargoId,cargoNombre,cargoDescripcion);
                    UserModel userM=new UserModel(userId,userName,userEmail,userRole);
                    EmpleadoModel empleado = new EmpleadoModel(id,foto,nombres,apellidos,telefono,dni,areaM,cargoM,userM);
                    MenuGetEmpleado.setData(empleado);

                    Picasso.get()
                            .load(MenuGetEmpleado.getData().getFoto())
                            .placeholder(R.drawable.perfil)
                            .error(R.drawable.perfil)
                            .into(image);

                    String nombreCompleto = MenuGetEmpleado.getData().getNombres();
                    String[] nombresSeparados = nombreCompleto.split(" "); // Separar el nombre completo por espacios

                    String primerNombre = "";
                    String apellido = "";

                    if (nombresSeparados.length > 0) {
                        primerNombre = nombresSeparados[0]; // Tomar el primer nombre
                    }

                    String apellidoCompleto = MenuGetEmpleado.getData().getApellidos();
                    String[] apellidosSeparados = apellidoCompleto.split(" "); // Separar el apellido completo por espacios

                    if (apellidosSeparados.length > 0) {
                        apellido = apellidosSeparados[0]; // Tomar el primer apellido
                    }

                    nombrecompleto.setText(primerNombre + " " + apellido);

                } catch (JSONException e)
                {http.getToast("Error JSON:"+MenuGetEmpleado.getMessage(), ct);}
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
    public void setimage(CircleImageView image) {
        this.image=image;
    }

    @Override
    public void setNombreCompleto(TextView nombreCompleto) {
        this.nombrecompleto = nombreCompleto;
    }

    @Override
    public void Logout() {
        RequestParams params=new RequestParams();
        http.addToken(ct);
        http.post(url.LOGOUT_ACCOUNT, params, new AsyncHttpResponseHandler() {

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
                    MenuLogout.setSuccess(json.getBoolean("success"));
                    MenuLogout.setMessage(json.getString("message"));
                    if(MenuLogout.getSuccess()){
                        SharedPreferences sharedPreferences = ct.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        if(ct instanceof LogoutListener) {
                            ((LogoutListener) ct).onLogoutSuccess();
                        }
                    }

                } catch (JSONException e)
                {http.getToast("Error JSON:"+MenuLogout.getMessage(), ct);}
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
    public interface LogoutListener {
        void onLogoutSuccess();
    }

}
