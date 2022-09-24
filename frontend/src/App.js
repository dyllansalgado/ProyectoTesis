import logo from './logo.svg';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import Login from "./components/Login/Login";
import Main from "./components/Main/Main";
import MainUsuario from "./components/Main/MainUsuario";
import Registrarse from "./components/Register/Registrarse";
import './App.css';

function App() {
  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/main" element={<Main />} />
        <Route path="/mainUsuario" element={<MainUsuario />} />
        <Route path="/registrarse" element={<Registrarse />} />
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
