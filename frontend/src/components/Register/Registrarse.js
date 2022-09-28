import React, { Component } from "react";
import axios from "axios";
import "./Registrarse.css";
import swal from "sweetalert";
import { Button, Container, Form} from "react-bootstrap";
import NavbarInicio from "../Login/NavbarInicio.js";


class Registrarse extends Component {
  constructor(props) {
    super(props);
    this.state = { nombre_usuario: "", 
    apellido_usuario: "", 
    correo_usuario: "",
    contrasena_usuario: "", 
    roles:[],
    id_rol:1,
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
      this.state.id_rol !== "")
      {
        axios.post("http://localhost:8080/usuario/create", {
        nombre_usuario: this.state.nombre_usuario,
        apellido_usuario: this.state.apellido_usuario,
        correo_usuario: this.state.correo_usuario,
        contrasena_usuario: this.state.contrasena_usuario,
        id_rol: this.state.id_rol,
        });

        swal({
          title: "Usuario creado con exito",
          text: "Se ha creado correctamente el usuario",
          icon: "success",
        });
        setTimeout(() => {
          window.location.replace("http://localhost:3000/");
        }, 2000);
      }
    else {
      swal({
        title: "Error al crear el usuario",
        text: "falla",
        icon: "warning",
      });

    }
  };

  componentDidMount(){
    axios
    .get("http://localhost:8080/roles/")
    .then((res) => {
      const roles = res.data;
      console.log(roles);
      this.setState({ roles: res.data });
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
    const roles = this.state.roles;

    return (
      <div>
        <NavbarInicio />
        <Container fluid>
        <div className="fondo">
          <div className="container_register">
          <form className="registrarseForm" onSubmit={this.RegistrarUsuario}>
              <div className="center">
                <h3 className="titulo">Registrar usuario</h3>
                <div className="form-group">
                  <label>
                    Nombre:
                    <input
                      className="inputRegister"
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
                      className="inputRegister"
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
                    <input
                      className="inputRegister"
                      type="email"
                      value={correo_usuario}
                      name="correo_usuario"
                      onChange={this.changeHandler}
                      placeholder="ejemplo@gmail.com"
                      required
                    />
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Contraseña:
                    <input
                      className="inputRegister"
                      type="password"
                      value={contrasena_usuario}
                      name="contrasena_usuario"
                      onChange={this.changeHandler}
                      placeholder="*****"
                      required
                    />
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Tipo de usuario:
                    <Form.Select
                      aria-label="Select id_rol"
                      name="id_rol"
                      onChange={this.changeHandler}
                      required
                    >
                    {roles.map((rol) => (
                      <option type="number" value={rol.id_rol}>
                        {rol.tipo_rol}
                      </option>
                    ))}
                    </Form.Select>
                  </label>
                </div>
                <Button type="submit" value="Submit">
                  {" "}
                  Registrar usuario
                </Button>
              </div>
            </form>
          </div>
        </div>
        </Container>
      </div>
    );
  }
}

export default Registrarse;