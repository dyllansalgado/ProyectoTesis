import React from "react";
import image from "../../assets/Logo.png";
import { Navbar,Nav,Container} from "react-bootstrap";
import Button from 'react-bootstrap/Button';
import {BiHelpCircle} from "react-icons/bi";
import "./Login.css";

const NavbarInicio = () => {
    return (
        <>
            <Navbar expand="lg" className="navbarInicio">
                <Container className="ContainerNavbar">
                    <Navbar.Brand href="/">
                        <img src={image} className="logo" alt="LogoTesis" />{" "}
                    </Navbar.Brand>
                    <Navbar.Brand  className="me-auto">SCAEC</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Nav className="mr-auto">
                        <Navbar.Collapse id="basic-navbar-nav">
                        <Button className="botonAyudaLogeado"  href="/comoUsar" size="sm">
                            ¿Cómo usar?
                            <BiHelpCircle/> <span></span>
                        </Button>
                        <Button className="botonAyudaLogeado"  href="/acercaDe" size="sm">
                            Acerca de
                        </Button>
                        </Navbar.Collapse>
                    </Nav>
                </Container>
            </Navbar>
        </>
    );
};

export default NavbarInicio;