import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import Login from "./components/Login/Login";
import MainJefeProyecto from "./components/Main/MainJefeProyecto";
import MainUsuario from "./components/Main/MainUsuario";
import Registrarse from "./components/Register/Registrarse";
import VerPerfil from "./components/VerPerfil/VerPerfil";
import CrearProyecto from "./components/Proyecto/CrearProyecto";
import VerProyecto from "./components/Proyecto/VerProyecto";
import MisProyectosJP from "./components/MisProyectos/MisProyectosJP";
import './App.css';

function App() {
  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/mainJefeProyecto" element={<MainJefeProyecto />} />
        <Route path="/mainUsuario" element={<MainUsuario />} />
        <Route path="/registrarse" element={<Registrarse />} />
        <Route path="/verPerfil" element={<VerPerfil />} />
        <Route path="/crearProyecto" element={<CrearProyecto />} />
        <Route path="/misproyectosjp" element={<MisProyectosJP />} />
        <Route exact path="/verProyecto/:idP" element={<VerProyecto />} />
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
