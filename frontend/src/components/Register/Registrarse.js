import React, { Component } from "react";
import axios from "axios";
import "./Registrarse.css";
import swal from "sweetalert";
import { Button, Container } from "react-bootstrap";
import NavbarInicio from "../Login/NavbarInicio.js";


class Registrarse extends Component {
  render() {
    return (
      <div>
        <NavbarInicio />
        <Container fluid>
        <div className="fondo">
          <div className="container_register">
          <form className="registrarseForm" onSubmit={this.handleSubmit}>
              <div className="center">
                <h3 className="titulo">Registrar usuario</h3>
                <div className="form-group">
                  <label>
                    Nombre:
                    <input
                      className="inputRegister"
                      type="text"
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
                      required
                    />
                  </label>
                </div>
                <div className="form-group">
                  <label>
                    Tipo de usuario:
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