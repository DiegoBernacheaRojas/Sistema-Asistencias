import AuthController from '../../Controllers/AuthController.js';
import LoginInput from '../../Models/LoginInput.js';

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('loginForm').addEventListener('submit', async function(event) {
        event.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        const response = await AuthController.login(new LoginInput(email,password));

        if (response.success) {
            // Redirigir a la página de inicio o dashboard
            window.location.href = 'http://localhost:8080/PlataformaWebAdmin';
        } else {
            // Mostrar mensaje de error
            toastr.error(response.message)
            //console.error('Error login:', response.message);
        }
    });

});