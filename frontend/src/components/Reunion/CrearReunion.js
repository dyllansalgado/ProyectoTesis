import React, { Component} from "react";
import {Container, Col, Row, Card } from "react-bootstrap";
import NavbarLogeadoJP from "../Main/NavbarLogeadoJP.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";


class CrearReunion extends Component {
    constructor(props) {
        super(props);
        this.state = { fecha_reunion: "", 
        lugar_reunion: "", 
        estado_proyecto: true,
        fecha: new Date("2018", "06", "22"),
        };
    }
    render() {
        return (
            <div>
              <NavbarLogeadoJP />
              <Container fluid>
              <div className="fondo">
                <div className="container_register">
                <form className="registrarseForm" onSubmit={this.CrearReunion}>
                    <div className="center">
                      <h3 className="titulo">Creación de Reunion</h3>
                      <div className="form-group">
                        <label>
                          Fecha de reunion:
                          <div className="contenedorDate">
                            <div className="centerDate">
                            <DatePicker selected={this.state.fecha_inicio_proyecto} onChange={this.onChange} locale = "es" className="pickers" dateFormat="dd-MM-yyyy"/>
                            </div>
                          </div>
                        </label>
                      </div>
                      <div className="form-group">
                        <label>
                          Lugar de reunion:
                          <input
                            className="inputRegister"
                            type="text"
                            value={objetivo_proyecto}
                            name="objetivo_proyecto"
                            onChange={this.changeHandler}
                            placeholder="aaaa"
                            required
                          />
                        </label>
                      </div>
                      <Button type="submit" value="Submit">
                        {" "}
                        Crear Reunion
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
export default CrearReunion ;