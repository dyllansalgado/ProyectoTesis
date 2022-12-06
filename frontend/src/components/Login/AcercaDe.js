import React, { Component } from "react";
import "./Login.css";
import axios from "axios";
import { Button, Container } from "react-bootstrap";
import NavbarInicio from "./NavbarInicio.js";
import NavbarLogeado from "../Main/NavbarLogeado.js";

class AcercaDe extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id_rol: null,
            usuario:[],
        };
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
    render() {
        const {usuario} = this.state;
        return (
            <div>
                <div>
                { usuario.id_rol === 1 || usuario.id_rol === 2 ?
                <NavbarLogeado />:
                <NavbarInicio />
                }
                </div>
                <Container fluid>
                    <div className="center">
                        <form className="loginForm">
                            <h1 className="TituloAcercaDe">SCAEC de la abreviación de las iniciales de:</h1>
                            <h2 className="SubtituloAcercaDe">Software Colaborativo de Apoyo para Entrevistas en el proceso de Captura de requisitos</h2>
                                <div className="form-group">
                                    <h2 className="textoAcercaDe">
                                        Es un software desarrollado por Dyllan Salgado, como trabajo de titulación para obtener el título de Ingeniero de Ejecución en Computación e Informática de la Universidad de Santiago de Chile.
                                    </h2>
                                </div>
                                <div className="form-group">
                                    <h2 className="textoAcercaDe">
                                        Tiene como objetivo apoyar a los usuarios que utilicen el software en el proceso de preparación y formulación de entrevistas para un desarrollo adecuado en la identificación y definición de los requisitos esenciales durante la captura de requisitos de un proyecto de software.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        Versión 1.0
                                    </h2>
                                </div>
                                <div className="text-center">
                                { usuario.id_rol === 1 || usuario.id_rol === 2 ?
                                <Button variant="secondary" className="botonVolver" href="/main" size="sm">Volver</Button>
                                :
                                <Button variant="secondary" className="botonVolver" href="/" size="sm">Volver</Button>
                                }
                                </div>
                        </form>
                    </div>
                </Container>
            </div>
        );
    }
}

export default AcercaDe;