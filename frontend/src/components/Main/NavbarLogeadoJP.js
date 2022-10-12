import React from "react";
import image from "../../assets/Logo.png";
import { Navbar} from "react-bootstrap";
import Button from 'react-bootstrap/Button';
import "./NavbarLogeadoJP.css";
import swal from "sweetalert";

const cerrarSesion = () => {
  localStorage.clear();
  swal({
    title: "Atención",
    text: "¿Desea Cerrar Sesión?",
    icon: "warning",
    buttons: ["No", "Si"],
  }).then((respuesta) => {
    if (respuesta) {
      swal({
        title: "Sesion Cerrada",
        text: "Su sesión está siendo cerrada",
        icon: "success",
      });
      setTimeout(() => {
        window.location.replace("http://localhost:3000/");
      }, 2000);
    }
  });
};

const NavbarLogeadoJP = () => {
  return (
      <>
        <Navbar collapseOnSelect className="navbarLogeado">
            <Navbar.Brand href="/mainJefeProyecto">
              <img src={image} className="logo" alt="LogoTesis" />{" "}
            </Navbar.Brand>
            <Button className="botonAyudaLogeado"  href="/ayuda" size="sm">
                ¿Necesitas ayuda?
            </Button>
            <Button className="botonCerrarSesion"  href="/verPerfil" size="sm">
                Ver perfil
            </Button>
            <Button className="botonCerrarSesion"  onClick={() => cerrarSesion()} size="sm">
                Cerrar sesión
            </Button>
        </Navbar>
      </>
  );
};

export default NavbarLogeadoJP ;


