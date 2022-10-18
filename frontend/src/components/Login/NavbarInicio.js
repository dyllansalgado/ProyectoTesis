import React from "react";
import image from "../../assets/Logo.png";
import { Navbar} from "react-bootstrap";
import Button from 'react-bootstrap/Button';
import "./Login.css";


const NavbarInicio = () => {
    return (
        <>
            <Navbar collapseOnSelect className="navbarInicio">
                <Navbar.Brand href="/">
                    <img src={image} className="logo" alt="LogoTesis" />{" "}
                </Navbar.Brand>
                <Button className="botonInicio"  href="/" size="lg">
                    Inicio
                </Button>
                <Button className="botonAyuda"  href="/" size="lg">
                    ¿Necesitas ayuda?
                </Button>
            </Navbar>
        </>
    );
};

export default NavbarInicio;