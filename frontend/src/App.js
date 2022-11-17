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
import PreguntaSeleccionadas from "./components/PreguntasSeleccionadas/PreguntasSeleccionadas"
import Respuesta from "./components/Respuesta/Respuesta";
import EditarRespuesta from "./components/Respuesta/EditarRespuesta";
import Requisitos from "./components/Requisitos/Requisitos";
import CrearRequisitoPR from "./components/Requisitos/CrearRequisitoPR";
import CrearRequisitoP from "./components/Requisitos/CrearRequisitoP";
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
        <Route exact path="/PreguntasSeleccionadas/:idP/:idR/:idT" element={<PreguntaSeleccionadas />} />
        <Route exact path="/PreguntaRespuesta/:idP/:idR/:idT/:idPreg" element={<Respuesta />} />
        <Route exact path="/EditarRespuesta/:idP/:idR/:idT/:idPreg/:idResp" element={<EditarRespuesta />} />
        <Route exact path="/RequisitosCreados/:idP/:idR/:idT" element={<Requisitos />} />
        <Route exact path="/CrearRequisitoPR/:idP/:idR/:idT/:idPreg/:idResp" element={<CrearRequisitoPR />} />
        <Route exact path="/CrearRequisitoP/:idP/:idR/:idT/:idPreg" element={<CrearRequisitoP />} />
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
