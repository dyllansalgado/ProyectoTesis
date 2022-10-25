import React, { Component } from "react";
import {Button, Container} from "react-bootstrap";
import axios from "axios";
import swal from "sweetalert";
import "./VerMasProyectos.css";
import NavbarLogeadoUsuario from "../Main/NavbarLogeadoUsuario.js";


class VerMasProyecto extends Component { 
    constructor(props) {
        super(props);
        this.state = {  usuario: [],
            proyecto:[],
            id: null,
            rol: "",
            contrasena: ""
        };
        this.changeName = this.changeName.bind(this);
    }

    changeName(event) {
        this.setState({ contrasena: event.target.value });
    }

    componentDidMount() {
        const id = localStorage.getItem('usuario');
        let idPath = window.location.pathname.split("/");
        axios.all([
            axios
                .get(
                  "http://localhost:8080/usuario/"+id)
                .then((res) => {
                const usuario = res.data;
                this.setState({usuario});
                if(usuario.id_rol === 1){
                    const rol = "Jefe de Proyecto";
                    this.setState({rol});
                }else if(usuario.id_rol === 2){
                    const rol = "Usuario";
                    this.setState({rol});
                }
                })
                .catch((error) => {
                  console.log(error);
            }),
            axios
                .get("http://localhost:8080/proyecto/"+ idPath[2])
                .then((res) => {
                  const proyecto = res.data;
                  this.setState({ proyecto});
                })
                .catch((error) => {
                  console.log(error);
            }),
        ]);
    }

    RegistrarProyectoUsuario () {
        const id = localStorage.getItem('usuario');
        let idPath = window.location.pathname.split("/");
        axios
        .post("http://localhost:8080/ingresarUsuarioProyecto/create/" + id + "/" + idPath[2] , {
          contrasena: this.state.contrasena
        })
        .then((response) => {
            console.log("soy el id " +id);
            if (response.data.contrasena === this.state.contrasena && id === 1 ) {
              swal({
                title: "Contraseña correcta",
                text: "Se ha asignado el proyecto en sus proyectos",
                icon: "success",
              });
              setTimeout(() => {
                window.location.replace("http://localhost:3000/mainJefeProyecto/");
              }, 1000);
            }
            else if(response.data.contrasena === this.state.contrasena && id  === 2) {
              swal({
                  title: "Contraseña correcta",
                  text: "Se ha asignado el proyecto en sus proyectos",
                  icon: "success",
              });
              setTimeout(() => {
                window.location.replace("http://localhost:3000/mainUsuario/");
              }, 2000);
            }
            else {
              swal({
                title: "Atención",
                text: "La contraseña del proyecto es incorrecta",
                icon: "warning",
                button: "Aceptar",
                timer: "2000",
              });
            }
        })
        .catch((error) => {
          console.log(error);
        });
    }
    render() { 
        const {usuario} = this.state;
        const {rol} = this.state;
        const contrasena = this.state.contrasena;
        const {proyecto} = this.state;
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
                    <h3 className="tituloUsuario"> Bienvenido {rol} :{usuario.nombre_usuario}</h3>
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
                            Ingrese Contraseña:
                            <input
                                className="inputRegister"
                                type="password"
                                value={contrasena}
                                name="contrasena"
                                onChange={this.changeName}
                                placeholder="*****"
                                required
                            />
                          </label>
                        </div>
                        <Button className="ingresarProyecto" onClick={() => this.RegistrarProyectoUsuario()}>
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