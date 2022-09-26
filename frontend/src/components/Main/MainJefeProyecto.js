import React, { Component} from "react";
import {Container, Col, Row } from "react-bootstrap";
import NavbarLogeadoJP from "./NavbarLogeadoJP.js";
import Button from 'react-bootstrap/Button';


class MainJefeProyecto extends Component {


    render() {
        return (
          <div>
            <div>
              <NavbarLogeadoJP />
            </div>
            <div class="fondoA" >
            <Container fluid>
                <Row>
                  <Col>
                    <h3 className="centerTitulo"> Bienvenido Jefe de Proyectos </h3>
                  </Col>
                  <Col>
                    <div className="filterBlock">
                      <input
                        type="text"
                        onClick={this.onChange}
                        onChange={this.onUserChange}
                        placeholder="Buscar Proyecto..."
                        ref={this.node}
                      />
                    </div>
                  </Col>
                </Row>
                <div className="InformacionCentral">
                <Button className="botonCrearProyecto"  href="/crearProyecto" size="lg">
                Crear Proyecto
                </Button>
                <Button className="botonMisProyectos"  href="/misProyectosJP" size="lg">
                Mis proyectos
                </Button>
              </div>
              <div className="centertable"></div>
                <table responsive ALIGN="center" className="table" id="listaProyectos">
                <thead>
                  <tr>
                    <th width="100">Nombre proyecto</th>
                    <th width="100">Fecha de Inicio</th>
                    <th width="100">Estado</th>
                  </tr>
                </thead>
            </table>
            
            </Container>
            </div>
          </div>
        );
      }
}

export default MainJefeProyecto;
