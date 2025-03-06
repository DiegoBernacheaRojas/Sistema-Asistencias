class RegisterEmpInput {
    constructor(nombres, apellidos,telefono,dni,id_area, id_horario_semanal, id_cargo, id_user) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.dni = dni;
        this.id_area = id_area;
        this.id_horario_semanal = id_horario_semanal;
        this.id_cargo = id_cargo;
        this.id_user = id_user;
    }
}

export default RegisterEmpInput;