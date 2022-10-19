import React, { Component} from "react";
import {Container, Col, Row, Card } from "react-bootstrap";
import NavbarLogeadoUsuario from "../Main/NavbarLogeadoUsuario.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";

class MisProyectosUsuario extends Component {
  constructor(props) {
    super(props);
    this.state = {
      usuario: [],
      id: null,
      proyectosUsuario: [],
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
        .get("http://localhost:8080/usuarioProyectos/"+id)
        .then((res) => {
          const proyectosUsuario = res.data;
        
          this.setState({proyectosUsuario});
          console.log(proyectosUsuario);
        })
        .catch((error) => {
          console.log(error);
        }),
    ]);
  }

  //Barra de busqueda
  onChange = (e) => {
    if (this.node.current.contains(e.target)) {
      return;
    }
    this.setState({
      proyectosFiltro: [],
    });
  };
  onUserChange = async (e) => {
    const id = localStorage.getItem('usuario');
    await axios
      .get("http://localhost:8080/usuarioProyectos/"+id)
      .then((res) => {
        this.setState({
          proyectosFiltro: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
      });

      let filter = e.target.value.toLowerCase();
      let filtroProyectos = this.state.proyectosFiltro.filter((e) => {

        let dataFilter = e.nombre_proyecto.toLowerCase();
        let dataFecha = e.fecha_inicio_proyecto.toLowerCase();
        return (
          dataFilter
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1 ||
          dataFecha
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1
          );
      });

    this.setState({
      proyectosUsuario: filtroProyectos,
    });
  };

  render() {
    const {usuario} = this.state;
    const {proyectosUsuario} = this.state;
    return (
      <div>
        <div>
          <NavbarLogeadoUsuario />
        </div>
        <div class="fondoA" >
        <Container fluid>
            <Row>
              <Col>
                <h3 className="centerTitulo"> Bienvenido Usuario: {usuario.nombre_usuario}</h3>
              </Col>
            </Row>
            <div className="InformacionCentralUsuario">
              <h3 className="centerTitulo"> Proyectos disponibles</h3>
              <Button className="botonMisProyectosUsuario"  href="/mainUsuario" size="lg">
                    Volver
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
              {proyectosUsuario.map((proyectosUsuario) => (
                <Col className="col">
                  <Card style={{ width: "18rem" }}>
                    <Card.Body>
                        <Card.Title>{proyectosUsuario.nombre_proyecto}</Card.Title>
                        <Card.Subtitle>Fecha de inicio: {proyectosUsuario.fecha_inicio_proyecto}</Card.Subtitle>
                        <p>
                          Objetivo: {proyectosUsuario.objetivo_proyecto}
                        </p>
                        <p>
                          Estado: {proyectosUsuario.estado_proyecto}
                        </p>
                        <div className="center">
                          <Button
                            variant="outline-primary"
                          >
                            Ingresar a proyecto
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

export default MisProyectosUsuario;
