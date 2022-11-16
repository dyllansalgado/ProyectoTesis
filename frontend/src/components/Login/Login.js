import React, { Component } from "react";
import axios from "axios";
import "./Login.css";
import swal from "sweetalert";
import { Button, Container } from "react-bootstrap";
import NavbarInicio from "./NavbarInicio.js";

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = { usuario: localStorage.getItem("token"), correo_usuario: "", contrasena_usuario: "", id_rol: "", token_usuario:""};
    this.changeName = this.changeName.bind(this);
    this.changeContrasena = this.changeContrasena.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }
    
  componentDidMount() {
    if (localStorage.getItem("token") && localStorage.getItem("id_rol") === 2 ){
      window.location.replace("http://localhost:3000/mainUsuario/");
    }
    else if (localStorage.getItem("token") && localStorage.getItem("id_rol") === 1 ){
      window.location.replace("http://localhost:3000/mainJefeProyecto/");
    }
  }
  changeName(event) {
      this.setState({ correo_usuario: event.target.value });
  }
  changeContrasena(event) {
      this.setState({ contrasena_usuario: event.target.value });
  }
  handleSubmit(event) {
    event.preventDefault();
    axios
      .post("http://localhost:8080/login/", {
        correo_usuario: this.state.correo_usuario,
        contrasena_usuario: this.state.contrasena_usuario
      })
      .then((response) => {
        if (response.data.token_usuario === 1 && response.data.contrasena_usuario === this.state.contrasena_usuario && response.data.id_rol === 2 ) {
          console.log(response.data);  
          localStorage.setItem('usuario', response.data.id_usuario);
          localStorage.setItem('id_rol', response.data.id_rol);
          swal({
            title: "Ingreso Exitoso",
            text: "Bienvenido usuario",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/main");
          }, 1000);
        }
        else if(response.data.token_usuario === 1 && response.data.contrasena_usuario === this.state.contrasena_usuario && response.data.id_rol === 1) {
          //console.log(response.data);
          localStorage.setItem('usuario', response.data.id_usuario);
          localStorage.setItem('id_rol', response.data.id_rol);
          localStorage.setItem('nombreUsuario', response.data.nombre_usuario);
          localStorage.setItem('token', response.data.nombre_usuario);
          swal({
          title: "Ingreso Exitoso",
          text: "Bienvenido jefe de proyecto",
          icon: "success",
        });
        setTimeout(() => {
          window.location.replace("http://localhost:3000/main");
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
        <div className="center">
        <h1 className="titulo">NOMBRE PROYECTO</h1>
        </div>
        <div className="center">
            <form className="loginForm" onSubmit={this.handleSubmit}>
              <div className="center">
              <h3 className="color-custom">Inicio de sesión</h3>
                <div className="form-group">
                  <label responsive>
                    Nombre de usuario:
                    <input
                      type="text"
                      value={this.state.correo_usuario}
                      onChange={this.changeName}
                      placeholder="Correo Usuario"
                      required
                    />
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Contraseña:
                    <input
                      type="password"
                      value={this.state.contrasena_usuario}
                      onChange={this.changeContrasena}
                      placeholder="*****"
                      required
                    />
                  </label>
                </div>
                <div className="text-center">
                <Button variant="secondary" className="botonIngresar" type="submit" value="Submit" size="sm">
                
                  {" "}
                  Ingresar
                </Button>
                 </div>
                <Button variant="secondary" className="botonRegistrarse" href="/registrarse" size="sm">Registrarse</Button>
                {""}
              </div>
            </form>
            </div>
          </Container>
        </div>
    );
  }
}

export default Login;