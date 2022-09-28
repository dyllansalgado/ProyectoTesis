import React from "react";
import image from "../../assets/Logo.png";
import { Navbar} from "react-bootstrap";
import Button from 'react-bootstrap/Button';
import "./NavbarLogeadoUsuario.css";


const NavbarLogeadoUsuario = () => {
  return (
      <>
        <Navbar collapseOnSelect className="navbarLogeadoUsuario">
            <Navbar.Brand href="/mainUsuario">
              <img src={image} className="logo" alt="LogoTesis" />{" "}
            </Navbar.Brand>
            <Button className="botonAyudaLogeado"  href="/ayuda" size="lg">
                ¿Necesitas ayuda?
            </Button>
            
            <Button className="botonAyudaLogeado"  href="/" size="lg">
                desconectarse
            </Button>
        </Navbar>
      </>
  );
};

export default NavbarLogeadoUsuario ;