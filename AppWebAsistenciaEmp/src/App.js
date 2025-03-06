import React, { useState } from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Aside from "./Components/Complements/Aside";
import Dashboard from "./Components/Contents/Dashboard";
import Header from "./Components/Complements/Header";
import Footer from "./Components/Complements/Footer";
import ControlSidebar from "./Components/Complements/ControlSidebar";
import CodeQR from "./Components/Contents/CodeQR";
import Asistencias from "./Components/Contents/Asistencias";
import Preloader from "./Components/Complements/Preloader";
import Login from './Components/Login';
import Cookies from 'js-cookie';


function App() {
  
  const [token] = useState(Cookies.get('token')); // Estado para verificar si el usuario está autenticado

  // Si no hay token, redirige al componente Login
  if (!token) {
    return <Login />;
  }

  return (
    <div className="wrapper">
      <BrowserRouter>
        <Preloader />
        <Header />
        <Aside />
        <div className="content-wrapper">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/qrcode" element={<CodeQR />} />
            <Route path="/asistencias" element={<Asistencias />} />
          </Routes>
        </div>
        <ControlSidebar />
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;