import React, { Component} from "react";
import {Container, Col, Row } from "react-bootstrap";
import NavbarLogeadoUsuario from "./NavbarLogeadoUsuario.js";


class MainUsuario extends Component {

    render() {
        return (
          <div>
            <div>
              <NavbarLogeadoUsuario />
            </div>
            <div class="fondoA" >
            <Container fluid>
                <Row>
                  <Col>
                    <h3 className="centerTitulo"> Bienvenido Usuario</h3>
                  </Col>
                </Row>
                <div className="InformacionCentralUsuario">
                <h3 className="centerTitulo"> Proyectos disponibles</h3>
                <Col>
                    <div className="filterBlockUsuario">
                      <input
                        type="text"
                        onClick={this.onChange}
                        onChange={this.onUserChange}
                        placeholder="Buscar Proyecto..."
                        ref={this.node}
                      />
                    </div>
                  </Col>
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

export default MainUsuario;