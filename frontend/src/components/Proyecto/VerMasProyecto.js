import React, { Component } from "react";
import {Button, Container} from "react-bootstrap";
import axios from "axios";
import swal from "sweetalert";
import "./VerMasProyectos.css";
import NavbarLogeado from "../Main/NavbarLogeado.js";


class VerMasProyecto extends Component { 
    constructor(props) {
        super(props);
        this.state = {  usuario: [],
            proyecto:[],
            id: null,
            rol: "",
            id_rol: "",
            contrasena: ""
        };
        this.changePass = this.changePass.bind(this);
    }

    changePass(event) {
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
            console.log(response.data);
            console.log(this.state.contrasena);
            if (response.data === true) {
              swal({
                title: "Contraseña correcta",
                text: "Se ha asignado el proyecto en sus proyectos",
                icon: "success",
              });
                setTimeout(() => {
                    window.location.replace("http://localhost:3000/main/");
                }, 1000);
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
        return (
        <>
        <div>
            <NavbarLogeado />
        </div>
        <div>
        <Container fluid>
            <div className="center">
                <h1 className="titulo">Bienvenido {rol} :{usuario.nombre_usuario}</h1>
            </div>
            <div className="center">
                <form className="ProyectoInformacion">
                    <div className="center">
                        <h3 className="titulo">Datos de Proyecto</h3>
                        <div className="form-group2">
                            <label>
                                Nombre de proyecto: {proyecto.nombre_proyecto}
                            </label>
                        </div>
                        <div className="form-group2">
                            <label>
                                Fecha de inicio: {proyecto.fecha_inicio_proyecto}
                            </label>
                        </div>
                        <div className="form-group2">
                            <label>
                                Estado proyecto: {String(proyecto.estado_proyecto) === 'false' ? "Disponible" : "Terminado"}
                            </label>
                        </div>
                        <div className="form-group2">
                            <label>
                                Objetivo: {proyecto.objetivo_proyecto}
                            </label>
                        </div>
                        <div className="form-group2">
                            <label>
                                Creador Proyecto: {proyecto.creadorProyecto}
                            </label>
                        </div>
                        <div className="form-group2">
                        <label>
                            Ingrese Contraseña:
                            <input
                                className="inputRegister"
                                type="password"
                                value={contrasena}
                                name="contrasena"
                                onChange={this.changePass}
                                placeholder="*****"
                                required
                            />
                          </label>
                        </div>
                        <Button className="botonVolverMain" onClick={() => this.RegistrarProyectoUsuario()}>
                            {" "}
                            Ingresar Proyecto
                        </Button>
                        <Button className="botonVolverMain"  href="/main/" >
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