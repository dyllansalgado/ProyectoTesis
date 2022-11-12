import React, { Component} from "react";
import {Container, Col, Row, Card } from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";
import {BsArrowReturnLeft} from "react-icons/bs";

class MisProyectos extends Component {
  constructor(props) {
    super(props);
    this.state = {
      usuario: [],
      id: null,
      proyectosJefe: [],
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
          const proyectosJefe = res.data;
        
          this.setState({proyectosJefe});
          console.log(proyectosJefe);
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
      proyectosJefe: filtroProyectos,
    });
  };
  
  render() {
    const {usuario} = this.state;
    const {proyectosJefe} = this.state;
    return (
      <div>
        <div>
          <NavbarLogeado />
        </div>
        <div className="fondoB" >
        <Container fluid>
            <Row>
                {usuario.id_rol === 1 ?
                <Col>
                    <h3 className="centerTitulo"> Bienvenido Jefe de Proyectos: {usuario.nombre_usuario}</h3>
                </Col>:
                <Col>
                    <h3 className="centerTitulo"> Bienvenido Usuario: {usuario.nombre_usuario}</h3>
                </Col>
                }
            </Row>
            <div className="InformacionCentral">
                {usuario.id_rol === 1 ?
                <Button className="botonCrearProyecto"  href="/crearProyecto" size="lg">
                Crear Proyecto
                </Button>:
                <h3 className="centerTitulo"> Proyectos disponibles</h3>
                }
                {usuario.id_rol === 1 ?
                <Button className="botonMisProyectos"  href="/main" size="lg">
                    Volver
                    <BsArrowReturnLeft/> <span></span>
                </Button>:
                <Button className="botonMisProyectosUsuario"  href="/main" size="lg">
                    Volver
                    <BsArrowReturnLeft/> <span></span>
                </Button>
                }
                {usuario.id_rol === 1 ?
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
                </Col>:
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
                }
            </div> 
            <Row className="ProyectosList">
              {proyectosJefe.map((proyectos) => (
                <Col className="col" key={proyectos.id_proyecto}>
                  <Card style={{ width: "18rem" }}>
                    <Card.Body>
                        <Card.Title>{proyectos.nombre_proyecto}</Card.Title>
                        <Card.Subtitle>Fecha de inicio: {proyectos.fecha_inicio_proyecto}</Card.Subtitle>
                        <p>
                            Objetivo: {proyectos.objetivo_proyecto}
                        </p>
                        <p>
                            Estado: {proyectos.estado_proyecto.toString() === 'false' ? "Disponible" : "Terminado"}
                        </p>
                        <div className="center">
                        {usuario.id_rol === 1 ?
                            <Button
                                variant="outline-primary" href={`/ingresarAProyecto/${proyectos.id_proyecto}`}
                            >
                                Ingresar a proyecto
                            </Button>:
                            <Button
                              variant="outline-primary" href={`/ingresarAProyecto/${proyectos.id_proyecto}`}
                            >
                              Ingresar a proyecto
                            </Button>
                        }
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

export default MisProyectos;