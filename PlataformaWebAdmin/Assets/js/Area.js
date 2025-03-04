import AuthController from '../../Controllers/AuthController.js';
import AreaController from '../../Controllers/AreaController.js';
import RegisterAreaInput from '../../Models/RegisterAreaInput.js';
import UpdateAreaInput from '../../Models/UpdateAreaInput.js';

document.addEventListener('DOMContentLoaded', async function() {

    AreaModule.getall();
    // Manejo del botón de cerrar sesión
    document.getElementById('logoutButton').addEventListener('click', function() {

        const response = AuthController.logout();
        if(response){
            window.location.href = 'http://localhost:8080/PlataformaWebAdmin/login.html';
        }
    });
});



var selectedAreaId = null; // Variable global para almacenar el ID del área seleccionada

const AreaModule = {
    register: async function() {
        const nombre = document.getElementById('nombreNuevo').value;
        const descripcion = document.getElementById('descripcionNuevo').value;

        try {
            const response = await AreaController.register(new RegisterAreaInput(nombre, descripcion));

            if (response.success) {
                // Registro exitoso, cierra el modal
                $('#modal-nuevo').modal('hide');

                // Vaciar el contenido de los inputs
                document.getElementById('nombreNuevo').value = '';
                document.getElementById('descripcionNuevo').value = '';

                // Actualiza la tabla
                AreaModule.getall();
            } else {
                // Manejar error en el registro
                alert('Error en el registro');
            }
        } catch (error) {
            // Manejar error en la solicitud
            console.error('Error en la solicitud:', error);
            alert('Ha ocurrido un error al registrar el área.');
        }
    },

    getall:async function(){
        if ($.fn.DataTable.isDataTable('#example2')) {
            $('#example2').DataTable().destroy();
        }
        const response = await AreaController.getall();
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
                { "data": "nombre", "title": "Nombre" },
                { "data": "descripcion", "title": "Descripción" },
                {
                    "data": "id",
                    "title": "Acciones",
                    "render": function(data, type, row) {
                        return `
                            <button type="button" class="btn bg-gradient-danger" onclick="AreaModule.handleDelete(${data})">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                                <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"/>
                                </svg>
                            </button>
                            <button type="button" class="btn bg-gradient-warning" onclick="AreaModule.loadingEditar(${data})">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">
                                <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.5.5 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11z"/>
                                </svg>
                            </button>
                        `;
                    }
                }
            ],
            "columnDefs": [
                { "width": "10%", "targets": 0 }, // Ancho del 10% para la primera columna
                { "width": "30%", "targets": 1 }, // Ancho del 30% para la segunda columna
                { "width": "50%", "targets": 2 }, // Ancho del 50% para la segunda columna
                { "width": "10%", "targets": 3 }, // Ancho del 10% para la segunda columna
            ],
            "createdRow": function(row, data, dataIndex) {
                $(row).find('td').addClass('align-middle');
            }
        });
    },

    handleDelete: function(id) {
        selectedAreaId = id; 
        $('#modal-confirm').modal('show'); 
    },

    confirmDelete: async function() {
        try {
            const response = await AreaController.delete(selectedAreaId);

            if (response.success) {

                $('#modal-confirm').modal('hide');
                selectedAreaId=null;
                AreaModule.getall();
            } else {
                alert('Error al eliminar el área: ' + response.message);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    },

    loadingEditar : async function(id) {
        selectedAreaId = id; 

        const response = await AreaController.get(id);
        document.getElementById('nombreEditar').value = response.data[0].nombre;
        document.getElementById('descripcionEditar').value = response.data[0].descripcion;
        $('#modal-editar').modal('show'); 
    },

    actualizar: async function() {
        try {
            const nombre = document.getElementById('nombreEditar').value;
            const descripcion = document.getElementById('descripcionEditar').value;
            const response = await AreaController.update(new UpdateAreaInput(selectedAreaId,nombre,descripcion));

            if (response.success) {

                $('#modal-editar').modal('hide');
                selectedAreaId=null;
                AreaModule.getall();
                
            } else {
                alert('Error al actualizar el área: ' + response.message);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    },
}

// Coloca el objeto en el ámbito global
window.AreaModule = AreaModule;