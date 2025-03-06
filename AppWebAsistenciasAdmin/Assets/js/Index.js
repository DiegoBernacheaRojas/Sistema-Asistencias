import AuthController from '../../Controllers/AuthController.js';
import EmpleadoController from '../../Controllers/EmpleadoController.js';
import JustificacionController from '../../Controllers/JustificacionController.js';
import UpdateJustificacionInput from '../../Models/UpdateJustificacionInput.js';
import AsistenciasController from '../../Controllers/AsistenciasController.js';
import apiUrls from '../../Utils/ApisURL.js';
document.addEventListener('DOMContentLoaded', async function() {
    try {
        // Obtener las justificaciones del día y llenar la tabla
        const justificacionResponse = await JustificacionController.getallday();
        if (justificacionResponse.success) {
            populateJustificacionesTable(justificacionResponse.data);
        } else {
            console.error('Error fetching justificaciones:', justificacionResponse.message);
        }

        // Obtener datos de empleados presentes y no presentes y graficar
        const empleadoResponse = await EmpleadoController.getEmpPresentes();
        if (empleadoResponse.success) {
            const { presente, no_presente } = empleadoResponse.data;
            var donutChartCanvas = $('#donutChart').get(0).getContext('2d');
            var donutData = {
                labels: ['No Presente', 'Presente'],
                datasets: [{
                    data: [no_presente, presente],
                    backgroundColor: ['#00c0ef', '#3c8dbc'],
                }]
            };
            var donutOptions = {
                maintainAspectRatio: false,
                responsive: true,
            };
            new Chart(donutChartCanvas, {
                type: 'doughnut',
                data: donutData,
                options: donutOptions
            });
        } else {
            console.error('Error fetching data:', empleadoResponse.message);
        }


        try {
            const response = await AsistenciasController.getallday();
    
            if (response.success) {
                const data = response.data;
    
                // Destruir la tabla existente si ya está inicializada
                if ($.fn.DataTable.isDataTable('#example2')) {
                    $('#example2').DataTable().destroy();
                }
    
                // Inicializar DataTable
                $('#example2').DataTable({
                    "paging": true,
                    "lengthChange": false,
                    "searching": true,
                    "ordering": true,
                    "info": false,
                    "autoWidth": false,
                    "responsive": true,
                    "language": {
                        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
                    },
                    "data": data,
                    "columns": [
                        { 
                            "data": "Foto_Empleado", 
                            "title": "Foto",
                            "render": function(data, type, row) {
                                const defaultUrl = 'Assets/img/perfil.png';
                                const imgUrl = data ? `${apiUrls.DOMINIO}${data}` : defaultUrl;
                                return `<img src="${imgUrl}"class="img-circle elevation-2" alt="Foto de ${row.Nombre_Empleado}" width="50">`;
                            }
                        },
                        { "data": "Nombre_Empleado", "title": "Nombre Empleado" },
                        { "data": "hora_entrada", "title": "Hora Entrada" },
                        { "data": "estado_entrada", "title": "Estado Entrada" },
                        { "data": "justificacion_entrada", "title": "Justificación Entrada" },
                        { "data": "hora_salida", "title": "Hora Salida" },
                        { "data": "estado_salida", "title": "Estado Salida" },
                        { "data": "justificacion_salida", "title": "Justificación Salida" }
                    ],
                    "columnDefs": [
                        { "width": "10%", "targets": 0 }, // Ancho del 10% para la primera columna (Foto)
                        { "width": "20%", "targets": 1 }, // Ancho del 20% para la segunda columna (Nombre Empleado)
                        { "width": "10%", "targets": 2 }, // Ancho del 10% para la tercera columna (Hora Entrada)
                        { "width": "10%", "targets": 3 }, // Ancho del 10% para la cuarta columna (Estado Entrada)
                        { "width": "10%", "targets": 4 }, // Ancho del 10% para la quinta columna (Justificación Entrada)
                        { "width": "10%", "targets": 5 }, // Ancho del 10% para la sexta columna (Hora Salida)
                        { "width": "10%", "targets": 6 }, // Ancho del 10% para la séptima columna (Estado Salida)
                        { "width": "10%", "targets": 7 }, // Ancho del 10% para la octava columna (Justificación Salida)
                    ],
                    "createdRow": function(row, data, dataIndex) {
                        $(row).find('td').addClass('align-middle text-center');
                    }
                });
            } else {
                console.error('Error fetching data:', response.message);
            }
        } catch (error) {
            console.error('An error occurred:', error);
        }

        // Manejo del botón de cerrar sesión
        document.getElementById('logoutButton').addEventListener('click', async function() {
            const response = await AuthController.logout();
            if (response) {
                window.location.href = 'http://localhost:8080/PlataformaWebAdmin/login.html';
            }
        });
    } catch (error) {
        console.error('An error occurred:', error);
    }
});

function populateJustificacionesTable(data) {
    const tbody = document.getElementById('justificaciones-tbody');
    let rows = '';  // Generamos todo el contenido HTML de una vez

    data.forEach(justificacion => {
        // Determinar la clase de la razón
        let bgClass = '';
        switch (justificacion.razon) {
            case 'TARDE':
                bgClass = 'bg-danger';
                break;
            case 'ANTES':
                bgClass = 'bg-purple';
                break;
            case 'AUSENCIA':
                bgClass = 'bg-info';
                break;
            default:
                bgClass = 'bg-secondary';  // Clase por defecto para otras razones
        }

        // Construir el contenido HTML para cada fila
        rows += `
            <tr>
                <td class="align-middle">${justificacion.dni}</td>
                <td class="align-middle">${justificacion.nombre_completo}</td>
                <td class="align-middle">${justificacion.titulo}</td>
                <td class="align-middle">
                    <h4><span class="badge ${bgClass}">${justificacion.razon}</span></h4>
                </td>
                <td class="align-middle">
                    <button type="button" class="btn bg-gradient-indigo align-middle" onclick="IndexModule.loadingEditar(${justificacion.id})">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-clipboard2-check-fill" viewBox="0 0 16 16">
                            <path d="M10 .5a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5.5.5 0 0 1-.5.5.5.5 0 0 0-.5.5V2a.5.5 0 0 0 .5.5h5A.5.5 0 0 0 11 2v-.5a.5.5 0 0 0-.5-.5.5.5 0 0 1-.5-.5"/>
                            <path d="M4.085 1H3.5A1.5 1.5 0 0 0 2 2.5v12A1.5 1.5 0 0 0 3.5 16h9a.5.5 0 0 0 1.5-1.5v-12A1.5 1.5 0 0 0 12.5 1h-.585q.084.236.085.5V2a1.5 1.5 0 0 1-1.5 1.5h-5A1.5 1.5 0 0 1 4 2v-.5q.001-.264.085-.5m6.769 6.854-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7.5 9.793l2.646-2.647a.5.5 0 0 1 .708.708"/>
                        </svg>
                    </button>
                </td>
            </tr>
        `;
    });

    // Insertar todo el contenido generado de una sola vez
    tbody.innerHTML = rows;
}

var selectedJustificacionId = null;
const IndexModule = {
    loadingEditar: async function(id) {
        selectedJustificacionId = id; // Almacena el ID de la justificación seleccionada

        const response = await JustificacionController.detalle(id);
        if (response.success) {
            document.getElementById('tituloEditar').value = response.data.titulo;
            document.getElementById('descripcionEditar').value = response.data.descripcion;
            document.getElementById('fechaEditar').value = response.data.fecha;
            document.getElementById('razonEditar').value = response.data.razon;
            $('#modal-editar').modal('show'); // Muestra el modal de confirmación
        } else {
            console.error('Error fetching justificacion:', response.message);
        }
    },

    actualizar: async function(estado) {
        try {
            const comentario = document.getElementById('comentarioEditar').value;
            const response = await JustificacionController.update(new UpdateJustificacionInput(selectedJustificacionId,estado, comentario));

            if (response.success) {
                // Actualización exitosa, cierra el modal y recarga la tabla
                $('#modal-editar').modal('hide');
                selectedJustificacionId = null;
                const justificacionResponse = await JustificacionController.getallday();
                if (justificacionResponse.success) {
                    populateJustificacionesTable(justificacionResponse.data);
                } else {
                    console.error('Error fetching justificaciones:', justificacionResponse.message);
                }
            } else {
                alert('Error al actualizar la justificación: ' + response.message);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    },
};

// Coloca el objeto en el ámbito global
window.IndexModule = IndexModule;