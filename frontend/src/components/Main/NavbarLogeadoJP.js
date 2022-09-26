import React from "react";
import image from "../../assets/Logo.png";
import { Navbar} from "react-bootstrap";
import Button from 'react-bootstrap/Button';
import "./NavbarLogeadoJP.css";


const NavbarLogeadoJP = () => {
  return (
      <>
        <Navbar collapseOnSelect className="navbarLogeado">
            <Navbar.Brand href="/mainJefeProyecto">
              <img src={image} className="logo" alt="LogoTesis" />{" "}
            </Navbar.Brand>
            <Button className="botonAyudaLogeado"  href="/ayuda" size="lg">
                ¿Necesitas ayuda?
            </Button>
        </Navbar>
      </>
  );
};

export default NavbarLogeadoJP ;


