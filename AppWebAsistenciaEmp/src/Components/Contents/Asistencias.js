import React, { useEffect, useRef } from "react";
import $ from "jquery";
import "datatables.net";
import "datatables.net-buttons-bs4";
import 'datatables.net-buttons-dt';
import 'datatables.net-buttons-dt/css/buttons.dataTables.css'; // Estilos de los botones de DataTables
import 'datatables.net-buttons/js/dataTables.buttons';
import AppController from "../../Controllers/AppController"; // Importa el controlador

const Asistencias = () => {
  const table2Ref = useRef(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        const response = await AppController.getAsistencias();
        if (response && response.success && response.data) {
          const registros = [];

          Object.keys(response.data).forEach((fecha) => {
            const registrosPorFecha = response.data[fecha];
            const entrada = registrosPorFecha.find((registro) => registro.tipo === "ENTRADA");
            const salida = registrosPorFecha.find((registro) => registro.tipo === "SALIDA");

            const fila = [
              fecha,
              entrada ? (entrada.hora || "--:--:--") : "--:--:--",
              entrada && entrada.estado ? entrada.estado : "",
              entrada && entrada.justificacion && entrada.justificacion.estado ? entrada.justificacion.estado : "",
              salida ? (salida.hora || "--:--:--") : "--:--:--",
              salida && salida.estado ? salida.estado : "",
              salida && salida.justificacion && salida.justificacion.estado ? salida.justificacion.estado : ""
            ];

            registros.push(fila);
          });

          // Si la tabla ya ha sido inicializada, destrúyela antes de inicializarla nuevamente
          if ($.fn.DataTable.isDataTable(table2Ref.current)) {
            $(table2Ref.current).DataTable().clear().destroy();
          }

          $(table2Ref.current).DataTable({
            paging: true,
            lengthChange: true,
            searching: true,
            ordering: true,
            info: false,
            autoWidth: false,
            responsive: true,
            language: {
              url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json",
            },
            data: registros,
            createdRow: function (row, data) {
              // Para las celdas de Estado Entrada (data[2])
              if (data[2]) {
                let bgClass = "";
                if (data[2] === "JUSTIFICADO") bgClass = "bg-lightblue";
                if (data[2] === "AUSENTE") bgClass = "bg-info";
                if (data[2] === "PUNTUAL") bgClass = "bg-success";
                if (data[2] === "TARDE") bgClass = "bg-red";
            
                $('td', row).eq(2).html(`<h4><span class="badge ${bgClass}">${data[2]}</span></h4>`);
              }
            
              // Para las celdas de Estado Salida (data[5])
              if (data[5]) {
                let bgClass = "";
                if (data[5] === "JUSTIFICADO") bgClass = "bg-lightblue";
                if (data[5] === "AUSENTE") bgClass = "bg-info";
                if (data[5] === "ANTES") bgClass = "bg-purple";
                if (data[5] === "SALIDA") bgClass = "bg-primary";
            
                $('td', row).eq(5).html(`<h4><span class="badge ${bgClass}">${data[5]}</span></h4>`);
              }
            
              // Para las celdas de Justificación Entrada (data[3])
              if (data[3]) {
                let bgClass = "";
                if (data[3] === "ESPERA") bgClass = "bg-gray";
                if (data[3] === "APROBADO") bgClass = "bg-lime";
                if (data[3] === "DENEGADO") bgClass = "bg-maroon";
            
                $('td', row).eq(3).html(`<h4><span class="badge ${bgClass}">${data[3]}</span></h4>`);
              }
            
              // Para las celdas de Justificación Salida (data[6])
              if (data[6]) {
                let bgClass = "";
                if (data[6] === "ESPERA") bgClass = "bg-gray";
                if (data[6] === "APROBADO") bgClass = "bg-lime";
                if (data[6] === "DENEGADO") bgClass = "bg-maroon";
            
                $('td', row).eq(6).html(`<h4><span class="badge ${bgClass}">${data[6]}</span></h4>`);
              }
            }
          });
        } else {
          console.error("Error en la respuesta o datos vacíos.");
        }
      } catch (error) {
        console.error("Error al obtener los registros de asistencias", error);
      }
    };

    loadData();
  }, []);

  return (
    <div>
      {/* Content Header (Page header) */}
      <div className="content-header">
        <div className="container-fluid">
          <div className="row mb-2">
            <div className="col-sm-6">
              <h1 className="m-0">Asistencias</h1>
            </div>
            {/* /.col */}
            <div className="col-sm-6">
              <ol className="breadcrumb float-sm-right">
                <li className="breadcrumb-item active">Asistencias</li>
              </ol>
            </div>
            {/* /.col */}
          </div>
          {/* /.row */}
        </div>
        {/* /.container-fluid */}
      </div>
      {/* /.content-header */}
      <div className="content">
        <div className="container-fluid">
          <div className="row">
            <div className="col-12">
              <div className="card card-navy">
                <div className="card-header">
                  <h3 className="card-title">Registros de Marcación de Asistencias</h3>
                </div>
                <div className="card-body">
                  <table ref={table2Ref} className="table table-hover text-center">
                    <thead>
                      <tr>
                        <th>Fecha</th>
                        <th>Hora Entrada</th>
                        <th>Estado Entrada</th>
                        <th>Justificación Entrada</th>
                        <th>Hora Salida</th>
                        <th>Estado Salida</th>
                        <th>Justificación Salida</th>
                      </tr>
                    </thead>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Asistencias;
