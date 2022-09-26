import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import Login from "./components/Login/Login";
import MainJefeProyecto from "./components/Main/MainJefeProyecto";
import MainUsuario from "./components/Main/MainUsuario";
import Registrarse from "./components/Register/Registrarse";
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
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
