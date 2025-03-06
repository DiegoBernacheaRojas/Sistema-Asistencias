import React from "react";

export default function Preloader() {
  return (
    <div className="preloader flex-column justify-content-center align-items-center">
      <img
        className="animation__shake"
        src="imageCarga.png"
        alt="GeorLogo"
        height={150}
        width={150}
      />
    </div>
  );
}
