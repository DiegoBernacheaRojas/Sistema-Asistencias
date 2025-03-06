import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import url from '../../ApiUrls';
export default function CodeQR() {
  const [imageSrc, setImageSrc] = useState('');

  useEffect(() => {
    const fetchImage = async () => {
      try {
        const response = await fetch(url.GET_QR, {
          headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`, // Obtener el token de las cookies y añadirlo a los headers
          },
          method: 'GET',
        });
        const blob = await response.blob();
        const imageUrl = URL.createObjectURL(blob);
        setImageSrc(imageUrl);
      } catch (error) {
        console.error('Error al obtener los datos del empleado:', error);
      }
    };

    fetchImage();

    // Cleanup function to revoke object URL
    return () => {
      if (imageSrc) {
        URL.revokeObjectURL(imageSrc);
      }
    };
  }, []);

  return (
    <div>
      {/* Content Header (Page header) */}
      <div className="content-header">
        <div className="container-fluid">
          <div className="row mb-2">
            <div className="col-sm-6">
              <h1 className="m-0"> Codigo QR</h1>
            </div>
            {/* /.col */}
            <div className="col-sm-6">
              <ol className="breadcrumb float-sm-right">
                <li className="breadcrumb-item active"> Codigo QR</li>
              </ol>
            </div>
            {/* /.col */}
          </div>
          {/* /.row */}
        </div>
        {/* /.container-fluid */}
      </div>
      {/* /.content-header */}
      {/* Main content */}
      <br></br>
      <div className="content">
        <div className="container-fluid">
        <div className="row justify-content-center mt-4">
          <div className="col-lg-3">
            <div className="card card-primary">
              <div className="card-header border-1 text-center">
                <h2 className="card-title">Marcar Asistencia</h2>
              </div>
              <div className="card-body d-flex justify-content-center align-items-center">
              {imageSrc ? (
                    <img
                      src={imageSrc}
                      alt="Codigo QR"
                      style={{ maxHeight: '100%', maxWidth: '100%' }}
                    />
                  ) : (
                    <p>Cargando...</p>
                  )}
              </div>
            </div>
          </div>
        </div>
          {/* /.row */}
        </div>
        {/* /.container-fluid */}
      </div>
      {/* /.content */}
    </div>
  );
}
