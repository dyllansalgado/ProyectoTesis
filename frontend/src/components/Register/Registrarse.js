import React, { Component } from "react";
import axios from "axios";
import "./Registrarse.css";
import swal from "sweetalert";
import { Button, Container} from "react-bootstrap";
import NavbarInicio from "../Login/NavbarInicio.js";
import PasswordChecklist from "react-password-checklist"


class Registrarse extends Component {
  constructor(props) {
    super(props);
    this.state = { nombre_usuario: "", 
    apellido_usuario: "", 
    correo_usuario: "",
    contrasena_usuario: "",
    contrasena_usuario2: "",
    roles:[],
    id_rol:2,
    };
  }

  changeHandler = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };
  RegistrarUsuario = (e) => {
    e.preventDefault();
    if (
      this.state.nombre_usuario !== "" &&
      this.state.apellido_usuario !== "" &&
      this.state.correo_usuario !== "" &&
      this.state.contrasena_usuario !== "" && 
      this.state.contrasena_usuario === this.state.contrasena_usuario2)
      {
        axios.post("http://localhost:8080/usuario/create", {
        nombre_usuario: this.state.nombre_usuario,
        apellido_usuario: this.state.apellido_usuario,
        correo_usuario: this.state.correo_usuario,
        contrasena_usuario: this.state.contrasena_usuario,
        id_rol: 2 ,
        }).then(response => {
          if(response.data){
            swal({
              title: "Usuario creado con éxito",
              text: "Se ha creado correctamente el usuario",
              icon: "success",
            });
            setTimeout(() => {
              window.location.replace("http://localhost:3000/");
            }, 2000);
          }
          else if(response.data === false){
            swal({
              title: "El correo se encuentra utilizado",
              text: "Por favor utilice un correo nuevo",
              icon: "warning",
            });
          }
        })
    }
    else if (this.state.contrasena_usuario !== this.state.contrasena_usuario2) {
      swal({
        title: "Las contraseñas no son identicas",
        text: "Las contraseñas deben ser identicas para crear el usuario",
        icon: "warning",
      });
    }
    else {
    swal({
      title: "Error al crear el usuario",
      text: "Ingrese todos los datos de manera correcta",
      icon: "warning",
    });
    
    }
  };

  componentDidMount(){
    axios
    .get("http://localhost:8080/roles/")
    .then((res) => {
      const roles = res.data;
      this.setState({roles});
      roles.pop();
      console.log(roles);
    })
    .catch((error) => {
      console.log(error);
    })
  }

  render() {
    const nombre_usuario = this.state.nombre_usuario;
    const apellido_usuario = this.state.apellido_usuario;
    const correo_usuario = this.state.correo_usuario;
    const contrasena_usuario = this.state.contrasena_usuario;
    const contrasena_usuario2 = this.state.contrasena_usuario2;

    return (
      <div>
        <NavbarInicio />
        <div>
        <Container fluid>
          <div className="center">
          <form className="loginForm" onSubmit={this.RegistrarUsuario}>
              <div className="center">
                <h3 className="color-custom">Registrar usuario</h3>
                <div className="form-group">
                  <label>
                    Nombre:
                    <input
                      type="text"
                      value={nombre_usuario}
                      name="nombre_usuario"
                      onChange={this.changeHandler}
                      placeholder="Nombre de Usuario"
                      required
                    />
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Apellido:
                    <input
                      type="text"
                      value={apellido_usuario}
                      name="apellido_usuario"
                      onChange={this.changeHandler}
                      placeholder="Apellido de Usuario"
                      required
                    />
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Correo:
                    <div>
                    <input
                      type="email"
                      value={correo_usuario}
                      name="correo_usuario"
                      onChange={this.changeHandler}
                      placeholder="ejemplo@gmail.com"
                      required
                    />
                    </div>
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Contraseña:
                    <input
                      type="password"
                      value={contrasena_usuario}
                      name="contrasena_usuario"
                      rules={["minLength","specialChar","number","capital","match"]}
				              minLength={8}
                      onChange={this.changeHandler}
                      placeholder="Contraseña"
                      required
                    />
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Repetir Contraseña:
                    <input
                      type="password"
                      value={contrasena_usuario2}
                      name="contrasena_usuario2"
                      onChange={this.changeHandler}
                      placeholder="Repetir Contraseña"
                      required
                    />
                  </label>
                  
                </div>
                <PasswordChecklist
				            rules={["minLength","specialChar","number","capital","match"]}
				            minLength={8}
				            value={contrasena_usuario}
                    valueAgain={contrasena_usuario2}
                    messages={{
                      minLength: "La contraseña tiene más de 8 caracteres.",
                      specialChar: "La contraseña tiene caracteres especiales.",
                      number: "La contraseña tiene un número.",
                      capital: "La contraseña tiene una letra mayúscula.",
                      match: "Las contraseñas coinciden."
                    }}
			              />
                <div className="text-center">
                <Button title = "registerButton" variant="secondary" className="botonIngresar" type="submit" value="Submit" size="sm">
                  {" "}
                  Registrar usuario
                </Button>
                <Button variant="secondary" className="botonRegistrarse" href="/" size="sm">Volver</Button>
                {""}
                </div>
              </div>
            </form>
          </div>
        </Container>
        </div>
      </div>
    );
  }
}

export default Registrarse;