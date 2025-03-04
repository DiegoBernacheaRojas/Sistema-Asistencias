import AuthController from "../../Controllers/AuthController.js";
import EmpleadoController from "../../Controllers/EmpleadoController.js";
import AreaController from "../../Controllers/AreaController.js";
import CargoController from "../../Controllers/CargoController.js";
import HorarioController from "../../Controllers/HorarioController.js";
import UserController from "../../Controllers/UserController.js";
import RegisterEmpInput from "../../Models/RegisterEmpInput.js";
import UpdateEmpInput from "../../Models/UpdateEmpInput.js";

document.addEventListener("DOMContentLoaded", async function () {
  EmpleadoModule.getall();
  // Manejo del botón de cerrar sesión
  document
    .getElementById("logoutButton")
    .addEventListener("click", function () {
      const response = AuthController.logout();
      if (response) {
        window.location.href = "http://localhost:8080/PlataformaWebAdmin/login.html";
      }
    });

  try {
    // Llamadas a las API
    const areasResponse = await AreaController.getall();
    const usersResponse = await UserController.getUnusedUsers();
    const cargosResponse = await CargoController.getall();
    const horariosResponse = await HorarioController.getall();

    // Asegúrate de que la respuesta de los usuarios sea exitosa
    if (usersResponse.success) {
      const users = usersResponse.data;
      const usersSelect = document.getElementById("users");
      const userEditSelect = document.getElementById("usersEditar");

      // Limpia el select por si tiene opciones previas
      usersSelect.innerHTML =
        '<option value="null">Seleccione un usuario</option>';

      // Llena el select con las opciones de usuarios
      users.forEach((user) => {
        const option = document.createElement("option");
        option.value = user.id;
        option.textContent = user.name;
        usersSelect.appendChild(option);

        const option2 = document.createElement("option");
        option2.value = user.id;
        option2.textContent = user.name;
        userEditSelect.appendChild(option2);
      });
    }
    if (areasResponse.success) {
      const areas = areasResponse.data;
      const areasSelect = document.getElementById("areas");
      const areasEditSelect = document.getElementById("areasEditar");
      // Limpia el select por si tiene opciones previas
      areasSelect.innerHTML =
        '<option value="null">Seleccione un área</option>';

      // Llena el select con las opciones de usuarios
      areas.forEach((area) => {
        const option = document.createElement("option");
        option.value = area.id;
        option.textContent = area.nombre;
        areasSelect.appendChild(option);

        const option2 = document.createElement("option");
        option2.value = area.id;
        option2.textContent = area.nombre;
        areasEditSelect.appendChild(option2);
      });
    }
    if (horariosResponse.success) {
      const horarios = horariosResponse.data;
      const horariosSelect = document.getElementById("horarios");
      const horariosEditSelect = document.getElementById("horariosEditar");
      // Limpia el select por si tiene opciones previas
      horariosSelect.innerHTML =
        '<option value="null">Seleccione un horario</option>';

      // Llena el select con las opciones de usuarios
      horarios.forEach((horario) => {
        const option = document.createElement("option");
        option.value = horario.id;
        option.textContent = horario.descripcion;
        horariosSelect.appendChild(option);

        const option2 = document.createElement("option");
        option2.value = horario.id;
        option2.textContent = horario.descripcion;
        horariosEditSelect.appendChild(option2);
      });
    }
    if (cargosResponse.success) {
      const cargos = cargosResponse.data;
      const cargosSelect = document.getElementById("cargos");
      const cargosEditSelect = document.getElementById("cargosEditar");
      // Limpia el select por si tiene opciones previas
      cargosSelect.innerHTML =
        '<option value="null">Seleccione un cargo</option>';

      // Llena el select con las opciones de usuarios
      cargos.forEach((cargo) => {
        const option = document.createElement("option");
        option.value = cargo.id;
        option.textContent = cargo.nombre;
        cargosSelect.appendChild(option);

        const option2 = document.createElement("option");
        option2.value = cargo.id;
        option2.textContent = cargo.nombre;
        cargosEditSelect.appendChild(option2);
      });
    }
  } catch (error) {
    console.error("Error fetching data:", error);
  }
});

var selectedEmpId = null; // Variable global para almacenar el ID del área seleccionada

const EmpleadoModule = {
  register: async function () {
    const nombres = document.getElementById("nombresNuevo").value;
    const apellidos = document.getElementById("apellidosNuevo").value;
    const telefono = document.getElementById("telefonoNuevo").value;
    const dni = document.getElementById("dniNuevo").value;
    const area = document.getElementById("areas").value;
    const horario = document.getElementById("horarios").value;
    const cargo = document.getElementById("cargos").value;
    const user = document.getElementById("users").value;

    try {
      if (
        user != "null" &&
        cargo != "null" &&
        horario != "null" &&
        area != "null"
      ) {
        const response = await EmpleadoController.register(
          new RegisterEmpInput(
            nombres,
            apellidos,
            telefono,
            dni,
            area,
            horario,
            cargo,
            user
          )
        );

        if (response.success) {
          // Registro exitoso, cierra el modal
          $("#modal-nuevo").modal("hide");

          const usersResponse = await UserController.getUnusedUsers();

          // Asegúrate de que la respuesta de los usuarios sea exitosa
          if (usersResponse.success) {
            const users = usersResponse.data;
            const usersSelect = document.getElementById("users");
            const userEditSelect = document.getElementById("usersEditar");

            // Vaciar el select "users"
            usersSelect.innerHTML = "";

            // Vaciar el select "usersEditar"
            userEditSelect.innerHTML = "";

            // Limpia el select por si tiene opciones previas
            usersSelect.innerHTML =
              '<option value="null">Seleccione un usuario</option>';

            // Llena el select con las opciones de usuarios
            users.forEach((user) => {
              const option = document.createElement("option");
              option.value = user.id;
              option.textContent = user.name;
              usersSelect.appendChild(option);

              const option2 = document.createElement("option");
              option2.value = user.id;
              option2.textContent = user.name;
              userEditSelect.appendChild(option2);
            });
          }

          // Vaciar el contenido de los inputs
          document.getElementById("nombresNuevo").value = "";
          document.getElementById("apellidosNuevo").value = "";
          document.getElementById("telefonoNuevo").value = "";
          document.getElementById("dniNuevo").value = "";
          document.getElementById("areas").value = null;
          document.getElementById("horarios").value = null;
          document.getElementById("cargos").value = null;
          document.getElementById("users").value = null;

          // Actualiza la tabla
          EmpleadoModule.getall();
        } else {
          $("#modal-nuevo").modal("hide");
          // Manejar error en el registro
          toastr.error(response.message);
        }
      } else {
        $("#modal-nuevo").modal("hide");
        toastr.error("No se puede registrar sin un usuario.");
      }
    } catch (error) {
      // Manejar error en la solicitud
      toastr.error(error);
    }
  },

  getall: async function () {
    if ($.fn.DataTable.isDataTable("#example2")) {
      $("#example2").DataTable().destroy();
    }
    const response = await EmpleadoController.getall();
    const empleadosData = response.data;
    $("#example2").DataTable({
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
      data: empleadosData,
      columns: [
        { data: "id", title: "ID" },
        { data: "nombres", title: "Nombres" },
        { data: "apellidos", title: "Apellidos" },
        { data: "telefono", title: "Telefono" },
        { data: "dni", title: "DNI" },
        { data: "area.nombre", title: "Área" },
        { data: "cargo.nombre", title: "Cargo" },
        {
          data: "id",
          title: "Acciones",
          render: function (data, type, row) {
            return `
                            <button type="button" class="btn bg-gradient-danger" onclick="EmpleadoModule.handleDelete(${data})">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                                <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"/>
                                </svg>
                            </button>
                            <button type="button" class="btn bg-gradient-warning" onclick="EmpleadoModule.loadingEditar(${data})">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">
                                <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.5.5 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11z"/>
                                </svg>
                            </button>
                        `;
          },
        },
      ],
      columnDefs: [
        { width: "10%", targets: 0 },
        { width: "17%", targets: 1 },
        { width: "17%", targets: 2 },
        { width: "10%", targets: 3 },
        { width: "10%", targets: 4 },
        { width: "13%", targets: 5 },
        { width: "13%", targets: 6 },
        { width: "10%", targets: 7 },
      ],
      createdRow: function (row, data, dataIndex) {
        $(row).find("td").addClass("align-middle");
      },
    });
  },

  handleDelete: function (id) {
    selectedEmpId = id; // Almacena el ID del área seleccionada
    $("#modal-confirm").modal("show"); // Muestra el modal de confirmación
  },

  confirmDelete: async function () {
    try {
      const response = await EmpleadoController.delete(selectedEmpId);

      if (response.success) {
        // Eliminación exitosa, cierra el modal y recarga la tabla
        $("#modal-confirm").modal("hide");
        selectedEmpId = null;
        EmpleadoModule.getall();
      } else {
        alert("Error al eliminar al Empleado: " + response.message);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  },

  loadingEditar: async function (id) {
    selectedEmpId = id; // Almacena el ID del área seleccionada
    const userEditSelect = document.getElementById("usersEditar");
    const option = document.createElement("option");

    const response = await EmpleadoController.get(selectedEmpId);
    const userResponse = await UserController.get(response.data.id_user);

    document.getElementById("nombresEditar").value = response.data.nombres;
    document.getElementById("apellidosEditar").value = response.data.apellidos;
    document.getElementById("telefonoEditar").value = response.data.telefono;
    document.getElementById("dniEditar").value = response.data.dni;
    option.value = userResponse.data[0].id;
    option.textContent = userResponse.data[0].name;
    userEditSelect.appendChild(option);
    userEditSelect.value = userResponse.data[0].id;

    document.getElementById("areasEditar").value = response.data.area.id;
    document.getElementById("horariosEditar").value =
      response.data.horario_semanal.id;
    document.getElementById("cargosEditar").value = response.data.cargo.id;

    $("#modal-editar").modal("show"); // Muestra el modal de confirmación
  },

  actualizar: async function () {
    try {
      const nombres = document.getElementById("nombresEditar").value;
      const apellidos = document.getElementById("apellidosEditar").value;
      const telefono = document.getElementById("telefonoEditar").value;
      const dni = document.getElementById("dniEditar").value;
      const id_user = document.getElementById("usersEditar").value;
      const id_area = document.getElementById("areasEditar").value;
      const id_horario_semanal =
        document.getElementById("horariosEditar").value;
      const id_cargo = document.getElementById("cargosEditar").value;

      const response = await EmpleadoController.update(
        new UpdateEmpInput(
          selectedEmpId,
          nombres,
          apellidos,
          telefono,
          dni,
          id_area,
          id_horario_semanal,
          id_cargo,
          id_user
        )
      );

      if (response.success) {
        $("#modal-editar").modal("hide");
        const usersResponse = await UserController.getUnusedUsers();

        // Asegúrate de que la respuesta de los usuarios sea exitosa
        if (usersResponse.success) {
          const users = usersResponse.data;
          const usersSelect = document.getElementById("users");
          const userEditSelect = document.getElementById("usersEditar");

          // Vaciar el select "users"
          usersSelect.innerHTML = "";

          // Vaciar el select "usersEditar"
          userEditSelect.innerHTML = "";

          // Limpia el select por si tiene opciones previas
          usersSelect.innerHTML =
            '<option value="null">Seleccione un usuario</option>';

          // Llena el select con las opciones de usuarios
          users.forEach((user) => {
            const option = document.createElement("option");
            option.value = user.id;
            option.textContent = user.name;
            usersSelect.appendChild(option);

            const option2 = document.createElement("option");
            option2.value = user.id;
            option2.textContent = user.name;
            userEditSelect.appendChild(option2);
          });
        }
        selectedEmpId = null;
        EmpleadoModule.getall();
      } else {
        alert("Error al actualizar el empleado: " + response.message);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  },
};

const modalEditar = document.getElementById("modal-editar");
const userEditSelect = document.getElementById("usersEditar");
// Escucha el evento 'hidden.bs.modal' cuando el modal se cierra
$(modalEditar).on("hidden.bs.modal", async function () {
  const response = await EmpleadoController.get(selectedEmpId);
  const id_user = response.data.id_user;

  // Encuentra y elimina la opción con el valor correspondiente a id_user
  const optionToRemove = userEditSelect.querySelector(
    `option[value="${id_user}"]`
  );
  if (optionToRemove) {
    optionToRemove.remove();
  }
});

// Coloca el objeto en el ámbito global
window.EmpleadoModule = EmpleadoModule;
