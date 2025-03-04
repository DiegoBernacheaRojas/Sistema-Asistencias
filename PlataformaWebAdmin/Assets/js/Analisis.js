import AuthController from "../../Controllers/AuthController.js";
import AsistenciasController from "../../Controllers/AsistenciasController.js";
import AreaController from "../../Controllers/AreaController.js";
import apiUrls from "../../Utils/ApisURL.js";

document.addEventListener("DOMContentLoaded", async function () {
  await AnalisisModule.selectAreaTrabajo();
  AnalisisModule.graficaAsistPorArea();
  AnalisisModule.loadingTop();
  AnalisisModule.getHorasTrabajadas();
  AnalisisModule.getTendencias();
  // Manejo del botón de cerrar sesión
  document
    .getElementById("logoutButton")
    .addEventListener("click", function () {
      const response = AuthController.logout();
      if (response) {
        window.location.href = "http://localhost:8080/PlataformaWebAdmin/login.html";
      }
    });
});

var barChartInstance = null;

const AnalisisModule = {
  getTendencias: async function () {
    // Obtener los datos de tendencia desde el controlador
    const response = await AsistenciasController.getTendencia();

    // Extraer los datos del response
    const tendenciaData = response.data;

    // Traducción de meses en inglés a español
    const meses = {
      January: "Enero",
      February: "Febrero",
      March: "Marzo",
      April: "Abril",
      May: "Mayo",
      June: "Junio",
      July: "Julio",
      August: "Agosto",
      September: "Septiembre",
      October: "Octubre",
      November: "Noviembre",
      December: "Diciembre",
    };

    // Crear etiquetas para los meses en español
    const labels = Object.keys(tendenciaData).map((mes) => meses[mes]);

    var areaChartCanvas = $("#tendenciaAreaChart").get(0).getContext("2d");
    // Configuración de los datos del gráfico
    var areaChartData = {
      labels: labels,
      datasets: [
        {
          label: "PUNTUAL",
          backgroundColor: "rgba(40,167,69,0.7)", // Aumentar la saturación
          borderColor: "rgba(40,167,69,1)", // Hacer el borde más brillante
          pointRadius: false,
          pointColor: "rgba(40,167,69,1)",
          pointStrokeColor: "rgba(40,167,69,1)",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(40,167,69,1)",
          data: Object.keys(tendenciaData).map(
            (mes) => tendenciaData[mes].PUNTUAL
          ),
        },
        {
          label: "TARDE",
          backgroundColor: "rgba(220,53,69,0.7)", // Aumentar la saturación
          borderColor: "rgba(220,53,69,1)", // Hacer el borde más brillante
          pointRadius: false,
          pointColor: "rgba(220,53,69,1)",
          pointStrokeColor: "rgba(220,53,69,1)",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(220,53,69,1)",
          data: Object.keys(tendenciaData).map(
            (mes) => tendenciaData[mes].TARDE
          ),
        },
        {
          label: "SALIDA",
          backgroundColor: "rgba(0,123,255,0.7)", // Aumentar la saturación
          borderColor: "rgba(0,123,255,1)", // Hacer el borde más brillante
          pointRadius: false,
          pointColor: "rgba(0,123,255,1)",
          pointStrokeColor: "rgba(0,123,255,1)",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(0,123,255,1)",
          data: Object.keys(tendenciaData).map(
            (mes) => tendenciaData[mes].SALIDA
          ),
        },
        {
          label: "ANTES",
          backgroundColor: "rgba(102,16,242,0.7)", // Aumentar la saturación
          borderColor: "rgba(102,16,242,1)", // Hacer el borde más brillante
          pointRadius: false,
          pointColor: "rgba(102,16,242,1)",
          pointStrokeColor: "rgba(102,16,242,1)",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(102,16,242,1)",
          data: Object.keys(tendenciaData).map(
            (mes) => tendenciaData[mes].ANTES
          ),
        },
        {
          label: "JUSTIFICADO",
          backgroundColor: "rgba(60,141,188,0.7)", // Aumentar la saturación
          borderColor: "rgba(60,141,188,1)", // Hacer el borde más brillante
          pointRadius: false,
          pointColor: "rgba(60,141,188,1)",
          pointStrokeColor: "rgba(60,141,188,1)",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(60,141,188,1)",
          data: Object.keys(tendenciaData).map(
            (mes) => tendenciaData[mes].JUSTIFICADO
          ),
        },
        {
          label: "AUSENTE",
          backgroundColor: "rgba(23,162,184,0.7)", // Aumentar la saturación
          borderColor: "rgba(23,162,184,1)", // Hacer el borde más brillante
          pointRadius: false,
          pointColor: "rgba(23,162,184,1)",
          pointStrokeColor: "rgba(23,162,184,1)",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(23,162,184,1)",
          data: Object.keys(tendenciaData).map(
            (mes) => tendenciaData[mes].AUSENTE
          ),
        },
      ],
    };

    // Opciones del gráfico
    var areaChartOptions = {
      maintainAspectRatio: false,
      responsive: true,
      legend: {
        display: true,
      },
      scales: {
        xAxes: [
          {
            gridLines: {
              display: false,
            },
          },
        ],
        yAxes: [
          {
            gridLines: {
              display: false,
            },
            ticks: {
              beginAtZero: true,
            },
          },
        ],
      },
    };

    // Crear el gráfico
    new Chart(areaChartCanvas, {
      type: "line", // Puedes cambiar 'bar' a 'line' si prefieres un gráfico de líneas
      data: areaChartData,
      options: areaChartOptions,
    });
  },

  getHorasTrabajadas: async function () {
    if ($.fn.DataTable.isDataTable("#horastrabajo")) {
      $("#horastrabajo").DataTable().destroy();
    }
    // Obtener el año y el mes actual
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth() + 1; // getMonth() devuelve de 0 a 11, por lo que sumamos 1

    // Obtener el primer día del mes actual
    const startDate = `${currentYear}-${String(currentMonth).padStart(
      2,
      "0"
    )}-01`;

    // Obtener el último día del mes actual
    const endDate = new Date(currentYear, currentMonth, 0)
      .toISOString()
      .slice(0, 10);

    const response = await AsistenciasController.getHorasTrabajadas(
      startDate,
      endDate
    );
    const horasTrabajadasData = response.data;
    $("#horastrabajo").DataTable({
      paging: true,
      lengthChange: true,
      searching: true,
      ordering: true,
      info: true,
      autoWidth: false,
      responsive: true,
      language: {
        url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json",
      },
      data: horasTrabajadasData,
      columns: [
        { data: "id", title: "ID", className: "text-center" },
        {
          data: "foto",
          title: "Foto",
          render: function (data, type, row) {
            // Define el dominio para las imágenes
            const dominio = apiUrls.DOMINIO;

            // Define la ruta de la imagen por defecto
            const imagenPorDefecto = "../Assets/img/perfil.png";

            // Construye la URL de la foto, usa la imagen por defecto si `data` es null
            const fotoUrl = data ? `${dominio}${data}` : imagenPorDefecto;

            return `<img src="${fotoUrl}" alt="Foto" class="img-circle elevation-2" style="width: 50px; height: 50px;">`;
          },
          className: "text-center",
        },
        { data: "area", title: "Área" },
        { data: "nombre", title: "Empleado" },
        {
          data: "horas",
          title: "Horas",
          render: function (data, type, row) {
            // Redondea las horas a 2 decimales y agrega 'h' al final
            return parseFloat(data).toFixed(2) + "h";
          },
        },
      ],
      columnDefs: [
        { width: "3%", targets: 0 },
        { width: "5%", targets: 1 },
        { width: "20%", targets: 2 },
        { width: "30%", targets: 3 },
        { width: "5%", targets: 4 },
      ],
      createdRow: function (row, data, dataIndex) {
        $(row).find("td").addClass("align-middle");
      },
    });
  },

  loadingTop: async function () {
    try {
      const response = await AsistenciasController.getTopMensual();

      if (response.success) {
        const tops = response.data;
        // Iterar desde 1 hasta 10, según los valores esperados
        for (let i = 1; i <= 10; i++) {
          const top = tops[`top${i}`];
          document.getElementById("Top" + i).innerText = top.nombre;
          document.getElementById("AsisTop" + i).innerText = top.posipoint;
          //const a = document.getElementById("Top" + i).innerText;
          //console.log(a);
        }
      } else {
        console.error("Error fetching data:", response.message);
      }
    } catch (error) {
      // Manejar error en la solicitud
      console.error("Error en la solicitud:", error);
      alert("Ha ocurrido un error.");
    }
  },

  selectAreaTrabajo: async function () {
    try {
      const response = await AreaController.getall();
      if (response.success) {
        const slctAreaTrabajo = document.getElementById("slctAreaTrabajo");
        const areas = response.data;

        // Llena el select con las opciones de usuarios
        areas.forEach((area) => {
          const option = document.createElement("option");
          option.value = area.id;
          option.textContent = area.nombre;
          slctAreaTrabajo.appendChild(option);
        });
        // Selecciona automáticamente el primer elemento si hay opciones disponibles
        if (slctAreaTrabajo.options.length > 0) {
          slctAreaTrabajo.selectedIndex = 0;
        }
      } else {
        console.error("Error fetching data:", response.message);
      }
    } catch (error) {
      // Manejar error en la solicitud
      console.error("Error en la solicitud:", error);
      alert("Ha ocurrido un error.");
    }
  },

  graficaAsistPorArea: async function () {
    try {
      const selectedValue = document.getElementById("slctAreaTrabajo").value;
      const areaId = Number(selectedValue);

      // Obtener datos de la asistencias de empleados por área
      const Response = await AsistenciasController.getForArea(areaId);
      if (Response.success) {
        // Extraer las etiquetas (nombres de los empleados) y los valores para cada estado
        var labels = Response.data.map((item) => item.empleado);
        var puntualData = Response.data.map((item) => item.PUNTUAL);
        var tardeData = Response.data.map((item) => item.TARDE);
        var salidaData = Response.data.map((item) => item.SALIDA);
        var antesData = Response.data.map((item) => item.ANTES);
        var justificadoData = Response.data.map((item) => item.JUSTIFICADO);
        var ausenteData = Response.data.map((item) => item.AUSENTE);

        // Configuración de los datos del gráfico
        var barChartData = {
          labels: labels,
          datasets: [
            {
              label: "PUNTUAL",
              backgroundColor: "rgba(40,167,69,0.9)",
              borderColor: "rgba(40,167,69,0.8)",
              data: puntualData,
            },
            {
              label: "TARDE",
              backgroundColor: "rgba(220,53,69,0.9)",
              borderColor: "rgba(220,53,69,0.8)",
              data: tardeData,
            },
            {
              label: "SALIDA",
              backgroundColor: "rgba(0,123,255,0.9)",
              borderColor: "rgba(0,123,255,0.8)",
              data: salidaData,
            },
            {
              label: "ANTES",
              backgroundColor: "rgba(102,16,242,0.9)",
              borderColor: "rgba(102,16,242,0.8)",
              data: antesData,
            },
            {
              label: "JUSTIFICADO",
              backgroundColor: "rgba(60,141,188,0.9)",
              borderColor: "rgba(60,141,188,0.8)",
              data: justificadoData,
            },
            {
              label: "AUSENTE",
              backgroundColor: "rgba(23,162,184,0.9)",
              borderColor: "rgba(23,162,184,0.8)",
              data: ausenteData,
            },
          ],
        };

        if (barChartInstance) {
          // Si ya existe un gráfico, actualiza sus datos
          barChartInstance.data.labels = labels;
          barChartInstance.data.datasets = barChartData.datasets;
          barChartInstance.update();
        } else {
          // Opciones del gráfico de barras
          var barChartOptions = {
            responsive: true,
            maintainAspectRatio: false,
            datasetFill: false,
            areaId: areaId, // Almacena el areaId en las opciones del gráfico
          };

          // Renderizar el gráfico de barras
          var barChartCanvas = $("#barChart").get(0).getContext("2d");
          barChartInstance = new Chart(barChartCanvas, {
            type: "bar",
            data: barChartData,
            options: barChartOptions,
          });
        }
      } else {
        console.error("Error fetching data:", Response.message);
      }
    } catch (error) {
      console.error("Error en la solicitud:", error);
      alert("Ha ocurrido un error.");
    }
  },
};

// Coloca el objeto en el ámbito global
window.AnalisisModule = AnalisisModule;
