package com.example.apkcontrol_asistencias.DAO.PerfilDao;

import android.widget.TextView;
import android.graphics.Bitmap;
public interface PerfilDao {

    void GetDatosEmpleado();
    void setTxtEmail(TextView email);
    void setTxtName(TextView name);
    void setImageFoto(de.hdodenhof.circleimageview.CircleImageView image);
    void setTextTelefono(TextView telefono);
    void setTxtCargo(TextView cargo);
    void setTxtArea(TextView area);
    void updateFotoPerfil(Bitmap image);

}
