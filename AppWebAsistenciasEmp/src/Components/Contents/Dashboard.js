import React from "react";

export default function Content() {
  return (
    <div>
      {/* Content Header (Page header) */}
      <div className="content-header">
        <div className="container-fluid">
          <div className="row mb-2">
            <div className="col-sm-6">
              <h1 className="m-0">Información</h1>
            </div>
            {/* /.col */}
            <div className="col-sm-6">
              <ol className="breadcrumb float-sm-right">
                <li className="breadcrumb-item active">Información</li>
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
      <div className="content">
        <div className="container-fluid">
          <div className="row">
            <div className="col-md-6">
              <div className="card card-info">
                <div className="card-header">
                  <h3 className="card-title">Introducción</h3>
                </div>
                {/* /.card-header */}
                <div className="card-body">
                  {/* we are adding the accordion ID so Bootstrap's collapse plugin detects it */}
                  <div id="accordion">
                    <div className="card card-primary">
                      <div className="card-header">
                        <h4 className="card-title w-100">
                          <a
                            className="d-block w-100"
                            data-toggle="collapse"
                            href="#collapseOne"
                          >
                            Geor Asistencia Empleado
                          </a>
                        </h4>
                      </div>
                      <div
                        id="collapseOne"
                        className="collapse show"
                        data-parent="#accordion"
                      >
                        <div className="card-body">
                        ¡Bienvenido al sistema Geor Asistencia Empleado! Esta innovadora aplicación web está diseñada para simplificar y agilizar el proceso de registro de asistencia de los empleados. Centrada en la eficiencia y la comodidad, nuestro sistema se basa en la utilización de códigos QR para registrar la presencia de los trabajadores de manera rápida y precisa.<br></br><br></br>

Nuestro principal objetivo es proporcionar a empleadores y empleados una herramienta intuitiva y confiable que facilite la gestión de la asistencia laboral. Con Geor Asistencia Empleado, usted podrá acceder a las siguientes funciones:<br></br><br></br>

<strong>Código QR:</strong> Esta función le permite generar y visualizar el código QR único necesario para marcar su asistencia con su dispositivo móvil. Con solo escanear el código desde su smartphone, podrá registrar de manera instantánea su entrada o salida del lugar de trabajo.<br></br><br></br>

<strong>Registro de Asistencias:</strong> En esta sección, usted podrá consultar de manera detallada todas las marcas de asistencia que haya realizado. Con una interfaz fácil de usar, podrá visualizar su historial de asistencias, incluyendo fechas, horas de entrada y salida, y cualquier observación relevante.<br></br><br></br>

Nuestro sistema está diseñado pensando en la comodidad y la seguridad de nuestros usuarios. Utilizando tecnología de vanguardia, garantizamos la integridad y confidencialidad de los datos de cada empleado.<br></br><br></br>

En Geor Asistencia Empleado, estamos comprometidos con la excelencia en la gestión de recursos humanos. Creemos que una herramienta efectiva de registro de asistencia es fundamental para el éxito y la productividad de cualquier organización.<br></br><br></br>

¡Esperamos que encuentre útiles estas herramientas para gestionar su asistencia de manera más eficiente y eficaz! Si tiene alguna pregunta o necesita asistencia, no dude en ponerse en contacto con nuestro equipo de soporte técnico. ¡Estamos aquí para ayudarle en todo momento!
                          
                        </div>
                      </div>
                    </div>
                    <div className="card card-danger">
                      <div className="card-header">
                        <h4 className="card-title w-100">
                          <a
                            className="d-block w-100"
                            data-toggle="collapse"
                            href="#collapseTwo"
                          >
                            Aplicación móvil
                          </a>
                        </h4>
                      </div>
                      <div
                        id="collapseTwo"
                        className="collapse"
                        data-parent="#accordion"
                      >
                        <div className="card-body">
                        La aplicación móvil de asistencia de Geor es una herramienta indispensable para todos nuestros empleados. No solo le permite registrar su presencia en el trabajo de manera rápida y sencilla, sino que también ofrece una amplia gama de funcionalidades diseñadas para mejorar su experiencia laboral, especialmente en el contexto del trabajo a distancia.<br></br><br></br>

En resumen, la aplicación móvil de asistencia de Geor es una herramienta esencial para todos nuestros empleados. Además de facilitar el registro de asistencia, ofrece una serie de funcionalidades adicionales que mejoran su experiencia laboral y le ayudan a mantener una mayor presencia y participación, incluso cuando trabaje de forma remota.<br></br><br></br>

¡No dude en aprovechar todas las ventajas que nuestra aplicación móvil de asistencia tiene para ofrecer! Si tiene alguna pregunta o necesita asistencia, no dude en ponerse en contacto con nuestro equipo de Recursos Humanos. Estamos aquí para ayudarle en todo momento y garantizar que su experiencia laboral sea lo más cómoda y satisfactoria posible.<br></br><br></br>
                        </div>
                      </div>
                    </div>
                    <div className="card card-success">
                      <div className="card-header">
                        <h4 className="card-title w-100">
                          <a
                            className="d-block w-100"
                            data-toggle="collapse"
                            href="#collapseThree"
                          >
                            Normas de horario laboral
                          </a>
                        </h4>
                      </div>
                      <div
                        id="collapseThree"
                        className="collapse"
                        data-parent="#accordion"
                      >
                        <div className="card-body">
                        Las normas laborales son un conjunto de regulaciones y leyes que establecen los derechos y obligaciones tanto de los empleadores como de los trabajadores en el ámbito laboral. Estas normas están diseñadas para proteger los intereses de ambas partes y garantizar un ambiente de trabajo seguro, justo y equitativo.<br></br><br></br>

Entre las normas laborales más comunes se encuentran las relacionadas con el salario mínimo, el horario de trabajo, las vacaciones, los días de descanso, la seguridad y salud ocupacional, la igualdad de oportunidades y el derecho a la sindicalización.<br></br><br></br>

Es importante cumplir con estas normas laborales no solo por obligación legal, sino también por el bienestar y la estabilidad de los trabajadores y las empresas. El respeto a estas regulaciones contribuye a mantener relaciones laborales armoniosas y a promover la productividad y el desarrollo económico.<br></br><br></br>

Además, las normas laborales pueden variar según el país y la industria, por lo que es fundamental que tanto empleadores como trabajadores estén familiarizados con las leyes y regulaciones laborales específicas que les apliquen.
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                {/* /.card-body */}
              </div>
              {/* /.card */}
            </div>

            {/* /.col */}
            <div className="col-md-6">
              <div className="card card-warning">
                <div className="card-header">
                  <h3 className="card-title text-white">Aplicación móvil</h3>
                </div>
                {/* /.card-header */}
                <div className="card-body">
                  <div
                    id="carouselExampleIndicators"
                    className="carousel slide"
                    data-ride="carousel"
                  >
                    <ol className="carousel-indicators">
                      <li
                        data-target="#carouselExampleIndicators"
                        data-slide-to={0}
                        className="active"
                      />
                      <li
                        data-target="#carouselExampleIndicators"
                        data-slide-to={1}
                      />
                      <li
                        data-target="#carouselExampleIndicators"
                        data-slide-to={2}
                      />
                    </ol>
                    <div className="carousel-inner">
                      <div className="carousel-item active">
                        <img
                          className="d-block w-100"
                          src="img1.PNG"
                          alt="First slide"
                        />
                      </div>
                      <div className="carousel-item">
                        <img
                          className="d-block w-100"
                          src="img2.PNG"
                          alt="Second slide"
                        />
                      </div>
                      <div className="carousel-item">
                        <img
                          className="d-block w-100"
                          src="img3.PNG"
                          alt="Third slide"
                        />
                      </div>
                    </div>
                    <a
                      className="carousel-control-prev"
                      href="#carouselExampleIndicators"
                      role="button"
                      data-slide="prev"
                    >
                      <span
                        className="carousel-control-custom-icon"
                        aria-hidden="true"
                      >
                        <i className="fas fa-chevron-left" />
                      </span>
                      <span className="sr-only">Previous</span>
                    </a>
                    <a
                      className="carousel-control-next"
                      href="#carouselExampleIndicators"
                      role="button"
                      data-slide="next"
                    >
                      <span
                        className="carousel-control-custom-icon"
                        aria-hidden="true"
                      >
                        <i className="fas fa-chevron-right" />
                      </span>
                      <span className="sr-only">Next</span>
                    </a>
                  </div>
                </div>
                {/* /.card-body */}
              </div>
              {/* /.card */}
            </div>
            {/* /.col */}
          </div>
          {/* /.row */}
        </div>
        {/* /.container-fluid */}
      </div>
      {/* /.content */}
    </div>
  );
}
