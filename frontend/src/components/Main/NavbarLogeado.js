import React, { Component } from "react";
import image from "../../assets/Logo.png";
import { Navbar, Container, Nav} from "react-bootstrap";
import Button from 'react-bootstrap/Button';
import "./NavbarLogeado.css";
import swal from "sweetalert";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import {IoMdExit} from "react-icons/io";
import {BiHelpCircle} from "react-icons/bi";
import {CgProfile} from "react-icons/cg";


class NavbarLogeado extends Component{
    constructor(props) {
        super(props);
        this.state = {
            id_rol: null,
            usuario:[],
        };
        this.node = React.createRef();
    }
    componentDidMount() {
        const id = localStorage.getItem('usuario');
        axios
          .get(
            "http://localhost:8080/usuario/"+id)
          .then((res) => {
            const usuario = res.data;
            this.setState({usuario});
        });
      }

    cerrarSesion() {
        localStorage.clear();
        swal({
          title: "Atención",
          text: "¿Desea Cerrar Sesión?",
          icon: "warning",
          buttons: ["No", "Si"],
        }).then((respuesta) => {
          if (respuesta) {
            swal({
              title: "Sesión Cerrada",
              text: "Su sesión está siendo cerrada",
              icon: "success",
            });
            setTimeout(() => {
              window.location.replace("http://localhost:3000/");
            }, 2000);
          }
        });
    }
    render() {
        const {usuario} = this.state;
        return (
            <>
              <Navbar expand="lg" className="navbarLogeado">
                <Container className="ContainerNavbar">
                  { usuario.id_rol === 1 ?
                    <Navbar.Brand href="/main">
                      <img src={image} className="logo" alt="LogoTesis" />{" "}
                    </Navbar.Brand>
                    :
                    <Navbar.Brand href="/main">
                    <img src={image} className="logo" alt="LogoTesis" />{" "}
                    </Navbar.Brand>
                  }
                  <Navbar.Brand  id="nombreProyecto" className="me-auto" >SCAEC</Navbar.Brand>
                   <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Nav className="mr-auto">
                      <Navbar.Collapse id="basic-navbar-nav">
                      <Button className="botonAyudaLogeado"  href="/ayuda" size="sm">
                        ¿Cómo usar?
                        <BiHelpCircle/> <span></span>
                      </Button>
                      <Button className="botonAyudaLogeado"  href="/verPerfil" size="sm">
                          Ver perfil
                          <CgProfile/> <span></span>
                      </Button>
                      <Button id="cerrarSesion" className="botonAyudaLogeado"  onClick={() => this.cerrarSesion()} size="sm">
                        Cerrar sesión
                        <IoMdExit/> <span></span>
                      </Button>
                      </Navbar.Collapse>
                    </Nav>
                  </Container>
              </Navbar>
            </>
        );
      };
}

export default NavbarLogeado ;