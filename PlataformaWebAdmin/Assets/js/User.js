import AuthController from '../../Controllers/AuthController.js';
import UserController from '../../Controllers/UserController.js';
import RegisterUserInput from '../../Models/RegisterUserInput.js';
import UpdateUserInput from '../../Models/UpdateUserInput.js';

document.addEventListener('DOMContentLoaded', async function() {

    UserModule.getall();
    // Manejo del botón de cerrar sesión
    document.getElementById('logoutButton').addEventListener('click', function() {

        const response = AuthController.logout();
        if(response){
            window.location.href = 'http://localhost:8080/PlataformaWebAdmin/login.html';
        }
    });
    
});



var selectedId = null; // Variable global para almacenar el ID del área seleccionada

const UserModule = {
    register: async function() {
        const name = document.getElementById('nameNuevo').value;
        const email = document.getElementById('emailNuevo').value;
        const password = document.getElementById('passwordNuevo').value;
        const role = document.getElementById('roles').value;

        try {
            const response = await UserController.register(new RegisterUserInput(name, email, password, role));

            if (response.success) {
                // Registro exitoso, cierra el modal
                $('#modal-nuevo').modal('hide');

                // Vaciar el contenido de los inputs
                document.getElementById('nameNuevo').value = '';
                document.getElementById('emailNuevo').value = '';
                document.getElementById('passwordNuevo').value = '';

                // Actualiza la tabla
                UserModule.getall();
            } else {
                // Manejar error en el registro
                alert('Error en el registro');
            }
        } catch (error) {
            // Manejar error en la solicitud
            console.error('Error en la solicitud:', error);
            alert('Ha ocurrido un error al registrar el usuario.');
        }
    },

    getall:async function(){
        if ($.fn.DataTable.isDataTable('#example2')) {
            $('#example2').DataTable().destroy();
        }
        const response = await UserController.getall();
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
                { "data": "name", "title": "Nombre" },
                { "data": "email", "title": "Email" },
                { "data": "role", "title": "Rol" },
                {
                    "data": "id",
                    "title": "Acciones",
                    "render": function(data, type, row) {
                        return `
                            <button type="button" class="btn bg-gradient-danger" onclick="UserModule.handleDelete(${data})">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                                <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"/>
                                </svg>
                            </button>
                            <button type="button" class="btn bg-gradient-warning" onclick="UserModule.loadingEditar(${data})">
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
                { "width": "30%", "targets": 1 }, 
                { "width": "20%", "targets": 2 }, 
                { "width": "30%", "targets": 3 }, 
                { "width": "10%", "targets": 4 }, 
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
            const response = await UserController.delete(selectedId);

            if (response.success) {
                // Eliminación exitosa, cierra el modal y recarga la tabla
                $('#modal-confirm').modal('hide');
                selectedId=null;
                UserModule.getall();
            } else {
                alert('Error al eliminar el usuario: ' + response.message);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    },

    loadingEditar : async function(id) {
        selectedId = id; // Almacena el ID del área seleccionada

        const response = await UserController.get(id);
        document.getElementById('nombreEditar').value = response.data[0].name;
        document.getElementById('emailEditar').value = response.data[0].email;
        document.getElementById('passwordEditar').value = ""
        document.getElementById('rolesEditar').value = response.data[0].role;

        $('#modal-editar').modal('show'); // Muestra el modal de confirmación
    },

    actualizar: async function() {
        try {
            const name = document.getElementById('nombreEditar').value;
            const email = document.getElementById('emailEditar').value;
            const password = document.getElementById('passwordEditar').value;
            const role = document.getElementById('rolesEditar').value;
            const response = await UserController.update(new UpdateUserInput(selectedId,name,email,password,role));

            if (response.success) {
                // Eliminación exitosa, cierra el modal y recarga la tabla
                $('#modal-editar').modal('hide');
                selectedId=null;
                UserModule.getall();
                
            } else {
                alert('Error al eliminar el usuario: ' + response.message);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    },
}

// Coloca el objeto en el ámbito global
window.UserModule = UserModule;