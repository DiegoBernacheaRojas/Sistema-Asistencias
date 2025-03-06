package com.example.apkcontrol_asistencias.DAO.HorarioSemanalDao.Dto;

import com.example.apkcontrol_asistencias.Model.HorarioDiaModel;

import java.util.List;

public class HorarioSemanalListOutputDto {
        private boolean success;
        private String message;
        private List<HorarioDiaModel> horarioDias;
        
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

        public List<HorarioDiaModel> getHorarioDias() {
                return horarioDias;
        }

        public void setHorarioDias(List<HorarioDiaModel> horarioDias) {
                this.horarioDias = horarioDias;
        }
}
