import React, { Component } from "react";
import {Button, Container} from "react-bootstrap";
import axios from "axios";
import "./VerMasProyectos.css";
import NavbarLogeadoUsuario from "../Main/NavbarLogeadoUsuario.js";


class VerMasProyecto extends Component { 
    constructor(props) {
        super(props);
        this.state = {  usuario: [],
            proyecto:[],
            id: null,
            rol: "",
            contrasena: "",
        };
        this.changeContrasenaaa = this.changeContrasenaaa.bind(this);
    }

    changeContrasenaaa(event) {
        this.setState({contrasena: event.target.value});
    }
    componentDidMount() {
        const id = localStorage.getItem('usuario');
        let idPath = window.location.pathname.split("/");
        console.log(idPath);
        axios.all([
            axios
              .get(
                "http://localhost:8080/usuario/"+id)
              .then((res) => {
                const usuario = res.data;
                this.setState({usuario});
            }),
            axios
                .get("http://localhost:8080/proyecto/"+ idPath[2])
                .then((res) => {
                  const proyecto = res.data;
                
                  this.setState({proyecto});
                })
                .catch((error) => {
                  console.log(error);
            }),
        ]);
    }

    RegistrarProyectoUsuario = (e) => {
        e.preventDefault();
        const id = localStorage.getItem('usuario');
        let idPath = window.location.pathname.split("/");
        axios
          .post("http://localhost:8080/ingresarUsuarioProyecto/create/" + id + "/" + idPath[2] , {
            contrasena: this.state.contrasena
          })
          .catch((error) => {
            console.log(error);
          });
    }

    render() { 
        const {usuario} = this.state;
        const {proyecto} = this.state; 
        if (usuario.id_rol === 1) {
          const rol = "Jefe de Proyecto";
          this.setState({rol});
        }else if (usuario.id_rol === 2) {
          const rol = "Usuario";
          this.setState({rol});
        } 
        const volver = () => {
            if (usuario.id_rol === 1) {
              window.location.replace("http://localhost:3000/mainJefeProyecto/");
            }else if (usuario.id_rol === 2) {
              window.location.replace("http://localhost:3000/mainUsuario/");
            }
        }
        return (
        <>
        <div>
            <NavbarLogeadoUsuario />
        </div>
        <div className="fondo">
        <Container fluid>
            <div className="container_register">
                <form className="verDatosProyectos">
                    <div className="center">
                    <h3 className="tituloUsuario"> Bienvenido {this.state.rol}: {usuario.nombre_usuario}</h3>
                        <h3 className="titulo">Datos de Proyecto</h3>
                        <div className="form-group">
                            <label>
                                Nombre de proyecto: {proyecto.nombre_proyecto}
                            </label>
                        </div>
                        <div className="form-group">
                            <label>
                                Fecha de inicio: {proyecto.fecha_inicio_proyecto}
                            </label>
                        </div>
                        <div className="form-group">
                            <label>
                                Estado proyecto: {proyecto.estado_proyecto}
                            </label>
                        </div>
                        <div className="form-group">
                            <label>
                                Objetivo: {proyecto.objetivo_proyecto}
                            </label>
                        </div>
                        <div className="form-group">
                        <label>
                            Nombre de usuario:
                            <input
                              className="inputLogin"
                              type="text"
                              value= {this.state.contrasena}
                              onChange={this.changeContrasenaaa}
                              placeholder="Usuario...."
                              required
                            />
                          </label>
                        </div>
                        <Button className="ingresarProyecto" type="submit" value="Submit">
                            {" "}
                            Ingresar Proyecto
                        </Button>
                        <Button className="botonVolverMain"  onClick={() => volver()} >
                            Volver
                        </Button>
                    </div>
                </form>
            </div>
        </Container>
        </div>
        </>
        );
    }
}

export default VerMasProyecto;