import React, { Component} from "react";
import {Button,Container, Col, Row, Card} from "react-bootstrap";
import NavbarLogeadoUsuario from "./NavbarLogeadoUsuario.js";
import "bootstrap/dist/css/bootstrap.min.css";
import "./MainUsuario.css";
import axios from "axios";

class MainUsuario extends Component {
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
            //console.log(proyectos);
          
            this.setState({proyectos});
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
          <NavbarLogeadoUsuario />
        </div>
        <div className="fondoA" >
        <Container fluid>
          <Row>
            <Col>
              <h3 className="centerTituloUsuario"> Bienvenido Usuario: {usuario.nombre_usuario}</h3>
            </Col>
          </Row>
          <div className="InformacionCentralUsuario">
          <h3 className="centerTitulo"> Proyectos disponibles</h3>
          <Button className="botonMisProyectosUsuario"  href="/misProyectos" size="lg">
                Mis proyectos
            </Button>
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
                            variant="outline-primary" href={`/verProyecto/${proyectos.id_proyecto}`}
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

export default MainUsuario;