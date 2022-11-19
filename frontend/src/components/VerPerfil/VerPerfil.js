import React, { Component } from "react";
import axios from "axios";
import "./VerPerfil.css";
import { Button, Container} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";



class VerPerfil extends Component {

    constructor(props) {
        super(props);
        this.state = {  usuario: [],
            id: null,
            rol: "",
        };
    }
    componentDidMount() {
      if (localStorage.getItem("token") == null && localStorage.getItem("id_rol") === null ){
        window.location.replace("http://localhost:3000/");
      }
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
    if (usuario.id_rol === 1) {
      const rol = "Jefe de Proyecto";
      this.setState({rol});
    }else if (usuario.id_rol === 2) {
      const rol = "Usuario";
      this.setState({rol});
    } 
    return (
      <div>
        <div>
        <NavbarLogeado />
        </div>
        <div className="fondo">
        <Container fluid>
          <div className="container_register">
          <form className="verDatos">
              <div className="center">
                <h3 className="titulo">Datos de Usuario</h3>
                <div className="form-group">
                  <label>
                    Nombre de usuario: {usuario.nombre_usuario}
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Apellido de usuario: {usuario.apellido_usuario}
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Correo usuario: {usuario.correo_usuario}
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Contraseña: {usuario.contrasena_usuario}
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Tipo de usuario: {this.state.rol}
                  </label>
                </div>
                <Button className="editarDatos" type="submit" value="Submit">
                  {" "}
                  Editar datos
                </Button>
                <Button className="botonVolver" href="/main">
                    Volver
                </Button>
              </div>
            </form>
          </div>
        </Container>
        </div>
      </div>
    );
  }
}

export default VerPerfil;