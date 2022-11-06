import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import Login from "./components/Login/Login";
import MainJefeProyecto from "./components/Main/MainJefeProyecto";
import MainUsuario from "./components/Main/MainUsuario";
import Registrarse from "./components/Register/Registrarse";
import VerPerfil from "./components/VerPerfil/VerPerfil";
import CrearProyecto from "./components/Proyecto/CrearProyecto";
import VerMasProyecto from "./components/Proyecto/VerMasProyecto";
import MisProyectosJP from "./components/MisProyectos/MisProyectosJP";
import MisProyectosUsuario from "./components/MisProyectos/MisProyectosUsuario";
import IngresarAProyectoJP from "./components/IngresarAProyecto/IngresarAProyectoJP";
import IngresarAProyectoUsuario from "./components/IngresarAProyecto/IngresarAProyectoUsuario";
import CrearReunion from "./components/Reunion/CrearReunion";
import IngresarReunionJP from "./components/Reunion/IngresarReunionJP";
import IngresarReunionUsuario from "./components/Reunion/IngresarReunionUsuario";
import GlosarioJP from "./components/Glosario/GlosarioJP";
import GlosarioUsuario from "./components/Glosario/GlosarioUsuario";
import IngresarAGlosario from "./components/Glosario/IngresarAGlosario";
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
        <Route path="/misProyectosJP" element={<MisProyectosJP />} />
        <Route path="/misProyectosUsuario" element={<MisProyectosUsuario />} />
        <Route exact path="/verMasProyecto/:idP" element={<VerMasProyecto />} />
        <Route exact path="/ingresarAProyectoJP/:idP" element={<IngresarAProyectoJP />} />
        <Route exact path="/ingresarAProyectoUsuario/:idP" element={<IngresarAProyectoUsuario />} />
        <Route exact path="/crearReunion/:idP" element={<CrearReunion />} />
        <Route exact path="/IngresarReunionJP/:idP/:idR" element={<IngresarReunionJP />} />
        <Route exact path="/IngresarReunionUsuario/:idP/:idR" element={<IngresarReunionUsuario />} />
        <Route exact path="/GlosarioReunionJP/:idP/:idR" element={<GlosarioJP />} />
        <Route exact path="/GlosarioReunionUsuario/:idP/:idR" element={<GlosarioUsuario />} />
        <Route exact path="/IngresarAGlosario/:idP/:idR/:idG" element={<IngresarAGlosario />} />
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
