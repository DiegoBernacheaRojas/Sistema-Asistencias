import React, { useState } from "react";
import LoginController from "../../Controllers/LoginController"; // Importa el controlador
import toastr from "toastr";
export default function CardBody() {
  
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phone] = useState("51922834945");
  const [message] = useState("Hola, necesito soporte técnico.");

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const data = await LoginController.loginUser(email, password);
      if (data.success) {
        window.location.href = "/"; // Navega a la ruta Main si el login es exitoso
      } else {
        showErrorToast(data.message);
      }
    } catch (err) {
      showErrorToast(err.message);
    }
  };
  const showErrorToast = (a) => {
    toastr.error(a);
  };
  return (
    <div className="card-body">
      <br></br>
      <p className="login-box-msg">Ingrese sus credenciales para iniciar sesión</p>
      <br></br>
      <form onSubmit={handleSubmit}>
        <div className="input-group mb-3">
          <input 
            type="email" 
            className="form-control" 
            placeholder="Email" 
            value={email}
            onChange={handleEmailChange} 
            required
          />
          <div className="input-group-append">
            <div className="input-group-text">
              <span className="fas fa-envelope" />
            </div>
          </div>
        </div>
        <div className="input-group mb-3">
        <input
            type="password"
            className="form-control"
            placeholder="Password"
            value={password}
            onChange={handlePasswordChange}
            required
          />
          <div className="input-group-append">
            <div className="input-group-text">
              <span className="fas fa-lock" />
            </div>
          </div>
        </div>
        <br></br>
        <div className="row">
          {/* /.col */}
          <div className="col-12">
            <button type="submit" className="btn btn-primary btn-block">
              Login
            </button>
          </div>
          {/* /.col */}
        </div>
      </form>
      {/* /.social-auth-links */}
      <br></br>
      <p className="mb-1">
        ¿Tiene problemas para ingresar? <a href={`https://api.whatsapp.com/send?phone=${phone}&text=${message}`} target="_blank" rel="noreferrer">Soporte técnico</a>
      </p>
    </div>

  );
}
