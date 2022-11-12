import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import Login from "./components/Login/Login";
import Main from "./components/Main/Main";
import MisProyectos from "./components/MisProyectos/MisProyectos";
import Registrarse from "./components/Register/Registrarse";
import VerPerfil from "./components/VerPerfil/VerPerfil";
import CrearProyecto from "./components/Proyecto/CrearProyecto";
import VerMasProyecto from "./components/Proyecto/VerMasProyecto";
import IngresarAProyecto from "./components/IngresarAProyecto/IngresarAProyecto";
import CrearReunion from "./components/Reunion/CrearReunion";
import IngresarReunion from "./components/Reunion/IngresarReunion";
import Glosario from "./components/Glosario/Glosario";
import IngresarAGlosario from "./components/Glosario/IngresarAGlosario";
import Tema from "./components/Tema/Tema";
import './App.css';

function App() {
  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/main" element={<Main />} />
        <Route path="/registrarse" element={<Registrarse />} />
        <Route path="/verPerfil" element={<VerPerfil />} />
        <Route path="/crearProyecto" element={<CrearProyecto />} />
        <Route path="/misProyectos" element={<MisProyectos />} />
        <Route exact path="/verMasProyecto/:idP" element={<VerMasProyecto />} />
        <Route exact path="/ingresarAProyecto/:idP" element={<IngresarAProyecto/>} />
        <Route exact path="/crearReunion/:idP" element={<CrearReunion />} />
        <Route exact path="/IngresarReunion/:idP/:idR" element={<IngresarReunion />} />
        <Route exact path="/GlosarioReunion/:idP/:idR" element={<Glosario />} />
        <Route exact path="/IngresarAGlosario/:idP/:idR/:idG" element={<IngresarAGlosario />} />
        <Route exact path="/TemaReunion/:idP/:idR/:idT" element={<Tema />} />
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
