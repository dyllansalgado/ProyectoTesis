import React, { Component} from "react";
import {Container, Col, Row, Card } from "react-bootstrap";
import NavbarLogeadoJP from "./NavbarLogeadoJP.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";


class MainJefeProyecto extends Component {
  constructor(props) {
    super(props);
    this.state = {
      usuario: [],
      id: null,
      proyectos: [],
    };
    this.node = React.createRef();
  }

  componentDidMount() {
    const id = localStorage.getItem('usuario');
    axios.all([
      axios
        .get(
          "http://localhost:8080/usuario/"+id)
        .then((res) => {
          const usuario = res.data;
          this.setState({usuario});
        }),
      axios
        .get("http://localhost:8080/proyectos/")
        .then((res) => {
          const proyectos = res.data;
        
          this.setState({proyectos});
          console.log(proyectos);
        })
        .catch((error) => {
          console.log(error);
        }),
    ]);
  }

  render() {
    const {usuario} = this.state;
    const {proyectos} = this.state;
    return (
      <div>
        <div>
          <NavbarLogeadoJP />
        </div>
        <div class="fondoA" >
        <Container fluid>
            <Row>
              <Col>
                <h3 className="centerTitulo"> Bienvenido Jefe de Proyectos: {usuario.nombre_usuario}</h3>
              </Col>
            </Row>
            <div className="InformacionCentral">
              <Button className="botonCrearProyecto"  href="/crearProyecto" size="lg">
              Crear Proyecto
              </Button>
              <Button className="botonMisProyectos"  href="/misProyectosJP" size="lg">
              Mis proyectos
              </Button>
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
            </div> 
            <Row className="ProyectosList">
              {proyectos.map((proyectos) => (
                <Col className="col">
                  <Card style={{ width: "18rem" }}>
                    <Card.Body>
                        <Card.Title>{proyectos.nombre_proyecto}</Card.Title>
                        <Card.Subtitle>Fecha de inicio: {proyectos.fecha_inicio_proyecto}</Card.Subtitle>
                        <p>
                          Objetivo: {proyectos.objetivo_proyecto}
                        </p>
                        <p>
                          Estado: {proyectos.estado_proyecto}
                        </p>
                        <div className="center">
                          <Button
                            variant="outline-primary"
                          >
                            Ver más
                          </Button>
                        </div>
                      </Card.Body>
                    </Card>
                </Col>
              ))}
            </Row>
        </Container>
        </div>
      </div>
    );
  }
}

export default MainJefeProyecto;
