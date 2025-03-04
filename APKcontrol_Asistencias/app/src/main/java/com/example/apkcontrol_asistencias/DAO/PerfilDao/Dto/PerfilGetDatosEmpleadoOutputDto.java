package com.example.apkcontrol_asistencias.DAO.PerfilDao.Dto;

import com.example.apkcontrol_asistencias.Model.EmpleadoModel;

public class PerfilGetDatosEmpleadoOutputDto {
        private boolean success;
        private String message;
        private EmpleadoModel data;

        public boolean getSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public EmpleadoModel getData() {
            return data;
        }

        public void setData(EmpleadoModel data) {
            this.data = data;
        }

}
