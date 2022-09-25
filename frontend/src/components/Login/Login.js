import React, { Component } from "react";
import axios from "axios";
import "./Login.css";
import swal from "sweetalert";
import { Button, Container } from "react-bootstrap";
import NavbarInicio from "./NavbarInicio.js";


class Login extends Component {
    constructor(props) {
        super(props);
        this.state = { nombre_usuario: "", contrasena_usuario: "", id_rol: ""};
        this.changeName = this.changeName.bind(this);
        this.changeContrasena = this.changeContrasena.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    changeName(event) {
        this.setState({ nombre_usuario: event.target.value });
    }
    changeContrasena(event) {
        this.setState({ contrasena_usuario: event.target.value });
    }

    handleSubmit(event) {
        event.preventDefault();
    
        axios
          .post("http://localhost:8080/login/", {
            nombre_usuario: this.state.nombre_usuario,
            contrasena_usuario: this.state.contrasena_usuario
          })
          .then((response) => {
            console.log("1");
            if (response.data.contrasena_usuario === this.state.contrasena_usuario && response.data.id_rol === 2) {
                //console.log(response.data);
                swal({
                title: "Ingreso Exitoso",
                text: "Bienvenido usuario",
                icon: "success",
              });
              setTimeout(() => {
                window.location.replace("http://localhost:3000/mainUsuario/");
              }, 2000);
            }
            else if(response.data.contrasena_usuario === this.state.contrasena_usuario && response.data.id_rol === 1) {
              //console.log(response.data);
              swal({
              title: "Ingreso Exitoso",
              text: "Bienvenido jefe de proyecto",
              icon: "success",
            });
            setTimeout(() => {
              window.location.replace("http://localhost:3000/main/");
            }, 2000);
            }
            else {
              swal({
                title: "Atención",
                text: "Contraseña o Usuario incorrecto",
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
    return (
      <div>
        <div>
          <NavbarInicio />
        </div>
        <Container fluid>
        <div className="fondo">
          <div className="container_login">
            <form className="loginForm" onSubmit={this.handleSubmit}>
              <div className="center">
                <h3 className="titulo">Inicio de sesión</h3>
                <div className="form-group">
                  <label>
                    Nombre de usuario:
                    <input
                      className="inputLogin"
                      type="text"
                      value={this.state.nombre_usuario}
                      onChange={this.changeName}
                      required
                    />
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Contraseña:
                    <input
                      className="inputLogin"
                      type="password"
                      value={this.state.contrasena_usuario}
                      onChange={this.changeContrasena}
                      required
                    />
                  </label>
                </div>
                <Button className="botonIngresar" type="submit" value="Submit">
                  {" "}
                  Ingresar
                </Button>
                <Button className="botonRegistrarse" href="/registrarse/">Registrarse</Button>
                {""}
              </div>
            </form>
          </div>
        </div>
        </Container>
      </div>
    );
  }
}

export default Login;