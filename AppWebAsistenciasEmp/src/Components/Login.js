import React from "react";
import CardHeader from "./Login/CardHeader";
import CardBody from "./Login/CardBody";

export default function Login() {
  return (
    <div className="hold-transition login-page">
      <div className="login-box">
        <div class="card card-outline card-primary">
            <CardHeader />
            <CardBody />
        </div>
      </div>
    </div>
  );
}
