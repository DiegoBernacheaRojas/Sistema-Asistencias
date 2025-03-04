package com.example.apkcontrol_asistencias.Controller;

import static com.loopj.android.http.AsyncHttpClient.log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.telephony.PhoneNumberUtils;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.apkcontrol_asistencias.Conexion.ApisUrl;
import com.example.apkcontrol_asistencias.Conexion.HttpManager;
import com.example.apkcontrol_asistencias.DAO.MenuDao.Dto.MenuGetEmpleadoOutputDto;
import com.example.apkcontrol_asistencias.DAO.MenuDao.Dto.MenuLogoutOutputDto;
import com.example.apkcontrol_asistencias.DAO.PerfilDao.Dto.PerfilGetDatosEmpleadoOutputDto;
import com.example.apkcontrol_asistencias.DAO.PerfilDao.Dto.PerfilUpdateFotoPerfilOutputDto;
import com.example.apkcontrol_asistencias.DAO.PerfilDao.PerfilDao;
import com.example.apkcontrol_asistencias.Model.AreaModel;
import com.example.apkcontrol_asistencias.Model.CargoModel;
import com.example.apkcontrol_asistencias.Model.EmpleadoModel;
import com.example.apkcontrol_asistencias.Model.UserModel;
import com.example.apkcontrol_asistencias.R;
import com.example.apkcontrol_asistencias.Util.Mensaje;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilController implements PerfilDao {

    private HttpManager http = new HttpManager();
    public ApisUrl url;
    private Mensaje ms = null;
    private Context ct;
    private PerfilGetDatosEmpleadoOutputDto PerfilGetEmpleado = new PerfilGetDatosEmpleadoOutputDto();
    private PerfilUpdateFotoPerfilOutputDto PerfilUpdateFotoPerfi= new PerfilUpdateFotoPerfilOutputDto();
    private de.hdodenhof.circleimageview.CircleImageView image;
    private TextView nombrecompleto, telefono, email, area, cargo;

    public PerfilController(Context c) {
        this.ct = c;
        this.ms = new Mensaje(ct);
    }

    @Override
    public void GetDatosEmpleado() {
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
                    String nomb="";
                    String apel="";
                    String tele="";
                    String dni="";

                    JSONObject areaJ;
                    int areaId=0;
                    String areaImage="";
                    String areaNombre="";
                    String areaDescripcion="";

                    JSONObject cargoJ;
                    int cargoId=0;
                    String cargoNombre="";
                    String cargoDescripcion="";

                    JSONObject userJ;
                    int userId=0;
                    String userName="";
                    String userEmail="";
                    String userRole="";

                    JSONObject json = new JSONObject(resp);
                    PerfilGetEmpleado.setSuccess(json.getBoolean("success"));
                    PerfilGetEmpleado.setMessage(json.getString("message"));
                    JSONObject data = json.optJSONObject("data");
                    if(data != null) {
                        id = data.getInt("id");
                        foto = url.BASE_URL + data.getString("foto");
                        nomb = data.getString("nombres");
                        apel = data.getString("apellidos");
                        tele = data.getString("telefono");
                        dni = data.getString("dni");
                        areaJ = data.getJSONObject("area");
                        if (areaJ != null) {
                            areaId = areaJ.getInt("id");
                            areaImage = url.BASE_URL + areaJ.getString("image");
                            areaNombre = areaJ.getString("nombre");
                            areaDescripcion = areaJ.getString("descripcion");
                        }
                        cargoJ = data.getJSONObject("cargo");
                        if (cargoJ != null) {
                            cargoId = cargoJ.getInt("id");
                            cargoNombre = cargoJ.getString("nombre");
                            cargoDescripcion = cargoJ.getString("descripcion");
                        }
                        userJ = data.getJSONObject("user");
                        if (userJ != null) {
                            userId = userJ.getInt("id");
                            userName = userJ.getString("name");
                            userEmail = userJ.getString("email");
                            userRole = userJ.getString("role");
                        }
                    }

                    AreaModel areaM=new AreaModel(areaId,areaImage,areaNombre,areaDescripcion);
                    CargoModel cargoM=new CargoModel(cargoId,cargoNombre,cargoDescripcion);
                    UserModel userM=new UserModel(userId,userName,userEmail,userRole);
                    EmpleadoModel empleado = new EmpleadoModel(id,foto,nomb,apel,tele,dni,areaM,cargoM,userM);
                    PerfilGetEmpleado.setData(empleado);

                    Picasso.get()
                            .load(PerfilGetEmpleado.getData().getFoto())
                            .placeholder(R.drawable.perfil)
                            .error(R.drawable.perfil)
                            .into(image);

                    String nombreCompleto = PerfilGetEmpleado.getData().getNombres();
                    String apellidos =PerfilGetEmpleado.getData().getApellidos();

                    nombrecompleto.setText(nombreCompleto + " " + apellidos);

                    String numeroFormateado = PhoneNumberUtils.formatNumber(PerfilGetEmpleado.getData().getTelefono(), "PE");
                    telefono.setText(numeroFormateado);

                    email.setText(PerfilGetEmpleado.getData().getUser().getEmail());

                    cargo.setText(PerfilGetEmpleado.getData().getCargo().getNombre());

                    area.setText(PerfilGetEmpleado.getData().getArea().getNombre());

                } catch (JSONException e)
                {http.getToast("Error JSON:"+PerfilGetEmpleado.getMessage(), ct);}
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
    public void setTxtEmail(TextView email) {
        this.email=email;
    }

    @Override
    public void setTxtName(TextView name) {
        this.nombrecompleto=name;
    }

    @Override
    public void setImageFoto(de.hdodenhof.circleimageview.CircleImageView image) {
        this.image=image;
    }

    @Override
    public void setTextTelefono(TextView telefono) {
        this.telefono=telefono;
    }

    @Override
    public void setTxtCargo(TextView cargo) {
        this.cargo=cargo;
    }

    @Override
    public void setTxtArea(TextView area) {
        this.area=area;
    }

    @Override
    public void updateFotoPerfil(Bitmap image) {
        RequestParams params=new RequestParams();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        params.put("foto", encodedImage);
        http.addToken(ct);
        http.post(url.UPDATE_FOTO_PERFIL, params, new AsyncHttpResponseHandler() {

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
                    PerfilUpdateFotoPerfi.setSuccess(json.getBoolean("success"));
                    PerfilUpdateFotoPerfi.setMessage(json.getString("message"));

                    Intent intent = new Intent("actualizar_imagen");
                    LocalBroadcastManager.getInstance(ct).sendBroadcast(intent);
                } catch (JSONException e)
                {http.getToast("Error JSON:"+PerfilUpdateFotoPerfi.getMessage(), ct);}
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
