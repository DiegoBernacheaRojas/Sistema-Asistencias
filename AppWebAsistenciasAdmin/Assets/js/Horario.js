import AuthController from '../../Controllers/AuthController.js';
import HorarioController from '../../Controllers/HorarioController.js';
import RegisterHorarioInput from '../../Models/RegisterHorarioInput.js';
import RegisterHorarioInputDia from '../../Models/RegisterHorarioInputDia.js';
import UpdateHorarioInput from '../../Models/UpdateHorarioInput.js';

document.addEventListener('DOMContentLoaded', async function() {

    HorarioModule.getall();
    // Manejo del botón de cerrar sesión
    document.getElementById('logoutButton').addEventListener('click', function() {

        const response = AuthController.logout();
        if(response){
            window.location.href = 'http://localhost:8080/PlataformaWebAdmin/login.html';
        }
    });
});



var selectedId = null; // Variable global para almacenar el ID del área seleccionada

const HorarioModule = {
    register: async function() {
        const descripcion = document.getElementById('descripcionNuevo').value;
    
        // Crear array de días
        const dias = [];
    
        // Listado de días y sus correspondientes IDs de entrada y salida
        const diasInfo = [
            { dia: 'Lunes', entradaId: 'LunesEntradaN', salidaId: 'LunesSalidaN', switchId: 'SwitchLunesN' },
            { dia: 'Martes', entradaId: 'MartesEntradaN', salidaId: 'MartesSalidaN', switchId: 'SwitchMartesN' },
            { dia: 'Miércoles', entradaId: 'MiercolesEntradaN', salidaId: 'MiercolesSalidaN', switchId: 'SwitchMiercolesN' },
            { dia: 'Jueves', entradaId: 'JuevesEntradaN', salidaId: 'JuevesSalidaN', switchId: 'SwitchJuevesN' },
            { dia: 'Viernes', entradaId: 'ViernesEntradN', salidaId: 'ViernesSalidaN', switchId: 'SwitchViernesN' },
            { dia: 'Sábado', entradaId: 'SabadoEntradaN', salidaId: 'SabadoSalidaN', switchId: 'SwitchSabadoN' },
            { dia: 'Domingo', entradaId: 'DomingoEntradaN', salidaId: 'DomingoSalidaN', switchId: 'SwitchDomingoN' }
        ];
    
        diasInfo.forEach(info => {
            const isActivated = document.getElementById(info.switchId).checked;
            if (isActivated) {
                let horaEntrada = document.getElementById(info.entradaId).value;
                let horaSalida = document.getElementById(info.salidaId).value;
    
                // Añadir ":00" al final de las horas si no tienen segundos
                horaEntrada = `${horaEntrada}:00`;
                horaSalida = `${horaSalida}:00`;
    
                dias.push(new RegisterHorarioInputDia(info.dia, horaEntrada, horaSalida));
            }
        });
    
        try {
            const response = await HorarioController.register(new RegisterHorarioInput(descripcion, dias));
    
            if (response.success) {
                // Registro exitoso, cierra el modal
                $('#modal-nuevo').modal('hide');
    
                // Vaciar el contenido de los inputs
                document.getElementById('descripcionNuevo').value = '';
                diasInfo.forEach(info => {
                    document.getElementById(info.entradaId).value = '00:00';
                    document.getElementById(info.salidaId).value = '00:00';
                    document.getElementById(info.switchId).checked = false;
                });
    
                // Actualiza la tabla
                HorarioModule.getall();
            } else {
                // Manejar error en el registro
                alert('Error en el registro');
            }
        } catch (error) {
            // Manejar error en la solicitud
            console.error('Error en la solicitud:', error);
            alert('Ha ocurrido un error al registrar el horario.');
        }
    },

    getall:async function(){
        if ($.fn.DataTable.isDataTable('#example2')) {
            $('#example2').DataTable().destroy();
        }
        const response = await HorarioController.getall();
        const areasData = response.data;
        $('#example2').DataTable({
            "paging": true,
            "lengthChange": true,
            "searching": true,
            "ordering": true,
            "info": true,
            "autoWidth": false,
            "responsive": true,
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
            },
            "data": areasData,
            "columns": [
                { "data": "id", "title": "ID" },
                { "data": "descripcion", "title": "Descripción" },
                {
                    "data": "id",
                    "title": "Acciones",
                    "render": function(data, type, row) {
                        return `
                            <button type="button" class="btn bg-gradient-danger" onclick="HorarioModule.handleDelete(${data})">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                                <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"/>
                                </svg>
                            </button>
                            <button type="button" class="btn bg-gradient-warning" onclick="HorarioModule.loadingEditar(${data})">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">
                                <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.5.5 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11z"/>
                                </svg>
                            </button>
                        `;
                    }
                }
            ],
            "columnDefs": [
                { "width": "10%", "targets": 0 }, 
                { "width": "10%", "targets": 2 }, 
            ],
            "createdRow": function(row, data, dataIndex) {
                $(row).find('td').addClass('align-middle');
            }
        });
    },

    handleDelete: function(id) {
        selectedId = id; // Almacena el ID del área seleccionada
        $('#modal-confirm').modal('show'); // Muestra el modal de confirmación
    },

    confirmDelete: async function() {
        try {
            const response = await HorarioController.delete(selectedId);

            if (response.success) {
                // Eliminación exitosa, cierra el modal y recarga la tabla
                $('#modal-confirm').modal('hide');
                selectedId=null;
                HorarioModule.getall();
            } else {
                alert('Error al eliminar el Horario: ' + response.message);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    },

    loadingEditar: async function(id) {
        selectedId = id; // Almacena el ID del área seleccionada
    
        const response = await HorarioController.get(id);
    
        // Establecer el valor de la descripción
        document.getElementById('descripcionEditar').value = response.data.descripcion;
    
        // Inicializar todas las entradas y switches
        const diasInfo = [
            { dia: 'Lunes', entradaId: 'LunesEntradaE', salidaId: 'LunesSalidaE', switchId: 'SwitchLunesE' },
            { dia: 'Martes', entradaId: 'MartesEntradaE', salidaId: 'MartesSalidaE', switchId: 'SwitchMartesE' },
            { dia: 'Miércoles', entradaId: 'MiercolesEntradaE', salidaId: 'MiercolesSalidaE', switchId: 'SwitchMiercolesE' },
            { dia: 'Jueves', entradaId: 'JuevesEntradaE', salidaId: 'JuevesSalidaE', switchId: 'SwitchJuevesE' },
            { dia: 'Viernes', entradaId: 'ViernesEntradE', salidaId: 'ViernesSalidaE', switchId: 'SwitchViernesE' },
            { dia: 'Sábado', entradaId: 'SabadoEntradaE', salidaId: 'SabadoSalidaE', switchId: 'SwitchSabadoE' },
            { dia: 'Domingo', entradaId: 'DomingoEntradaE', salidaId: 'DomingoSalidaE', switchId: 'SwitchDomingoE' }
        ];
    
        // Desactivar todos los switches e inicializar horas
        diasInfo.forEach(info => {
            document.getElementById(info.entradaId).value = "00:00";
            document.getElementById(info.salidaId).value = "00:00";
            document.getElementById(info.switchId).checked = false;
        });
    
        // Activar y establecer horas para los días que están en la respuesta
        response.data.horariosDias.forEach(diaData => {
            const diaInfo = diasInfo.find(info => info.dia === diaData.dia);
            if (diaInfo) {
                document.getElementById(diaInfo.entradaId).value = diaData.hora_entrada.substring(0, 5);
                document.getElementById(diaInfo.salidaId).value = diaData.hora_salida.substring(0, 5);
                document.getElementById(diaInfo.switchId).checked = true;
            }
        });
    
        // Mostrar el modal
        $('#modal-editar').modal('show');
    },

    actualizar: async function() {
        try {
            const descripcion = document.getElementById('descripcionEditar').value;
    
            // Crear array de días actualizados
            const dias = [];
    
            // Listado de días y sus correspondientes IDs de entrada y salida
            const diasInfo = [
                { dia: 'Lunes', entradaId: 'LunesEntradaE', salidaId: 'LunesSalidaE', switchId: 'SwitchLunesE' },
                { dia: 'Martes', entradaId: 'MartesEntradaE', salidaId: 'MartesSalidaE', switchId: 'SwitchMartesE' },
                { dia: 'Miércoles', entradaId: 'MiercolesEntradaE', salidaId: 'MiercolesSalidaE', switchId: 'SwitchMiercolesE' },
                { dia: 'Jueves', entradaId: 'JuevesEntradaE', salidaId: 'JuevesSalidaE', switchId: 'SwitchJuevesE' },
                { dia: 'Viernes', entradaId: 'ViernesEntradE', salidaId: 'ViernesSalidaE', switchId: 'SwitchViernesE' },
                { dia: 'Sábado', entradaId: 'SabadoEntradaE', salidaId: 'SabadoSalidaE', switchId: 'SwitchSabadoE' },
                { dia: 'Domingo', entradaId: 'DomingoEntradaE', salidaId: 'DomingoSalidaE', switchId: 'SwitchDomingoE' }
            ];
    
            // Llenar el array de días activados
            diasInfo.forEach(info => {
                const isActivated = document.getElementById(info.switchId).checked;
                if (isActivated) {
                    let horaEntrada = document.getElementById(info.entradaId).value;
                    let horaSalida = document.getElementById(info.salidaId).value;
    
                    // Asegurar que las horas tengan el formato HH:MM:SS
                    if (horaEntrada.length === 5) {
                        horaEntrada += ':00';
                    }
                    if (horaSalida.length === 5) {
                        horaSalida += ':00';
                    }
    
                    dias.push(new RegisterHorarioInputDia(info.dia, horaEntrada, horaSalida));
                }
            });
    
            // Realizar la actualización utilizando el controlador
            const response = await HorarioController.update(new UpdateHorarioInput(selectedId, descripcion, dias));
    
            if (response.success) {
                // Actualización exitosa, cierra el modal y recarga la tabla
                $('#modal-editar').modal('hide');
                selectedId = null;
                HorarioModule.getall();
            } else {
                alert('Error al actualizar el horario: ' + response.message);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    },
}

// Coloca el objeto en el ámbito global
window.HorarioModule = HorarioModule;