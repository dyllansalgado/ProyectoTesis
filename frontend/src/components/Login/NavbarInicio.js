import React from "react";
import image from "../../assets/Logo.png";
import { Navbar } from "react-bootstrap";
import "./Login.css";


const NavbarInicio = () => {
    return (
        <>
            <Navbar collapseOnSelect className="navbarInicio">
                <Navbar.Brand href="/">
  <img src={image} className="logo" alt="LogoTesis" />{" "}
                </Navbar.Brand>
            </Navbar>
        </>
    );
};

export default NavbarInicio;