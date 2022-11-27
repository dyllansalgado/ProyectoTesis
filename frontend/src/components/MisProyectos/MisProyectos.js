import React, { Component} from "react";
import {Container, Col, Row, Card } from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";
import swal from "sweetalert";
import "../Main/NavbarLogeado.css";
import {BsArrowReturnLeft} from "react-icons/bs";

class MisProyectos extends Component {
  constructor(props) {
    super(props);
    this.state = {
      usuario: [],
      id: null,
      proyectosJefe: [],
      proyectosFiltro: []
    };
    this.node = React.createRef();
  }

  componentDidMount() {
    if (localStorage.getItem("token") == null && localStorage.getItem("id_rol") === null ){
      window.location.replace("http://localhost:3000/");
    }
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
  
  CerrarProyecto(id_proyecto){
    swal({
      title: "Atención",
      text: "¿Desea cerrar el proyecto seleccionado?",
      icon: "warning",
      buttons: ["No", "Si"],
    }).then((respuesta) => {
      if (respuesta) {
        axios.put("http://localhost:8080/proyecto/" + id_proyecto, {
          estado_proyecto: true,
        });
        swal({
          title: "Proyecto actualizado con éxito",
          text: "El proyecto ha sido cerrado con éxito",
          icon: "success",
        });
        setTimeout(() => {
          window.location.replace("http://localhost:3000/misProyectos");
        }, 2000);
      }
    });
  }

  EditarProyecto(id_proyecto){
    swal({
      title: "Atención",
      text: "¿Desea editar el proyecto seleccionado?",
      icon: "warning",
      buttons: ["No", "Si"],
    }).then((respuesta) => {
      if (respuesta) {
        setTimeout(() => {
          window.location.replace("http://localhost:3000/EditarProyecto/"+ id_proyecto);
        }, 2000);
      }
    });
  }

  render() {
    const {usuario} = this.state;
    const {proyectosJefe} = this.state;
    return (
      <div>
        <div>
          <NavbarLogeado />
        </div>
        <div>
        <Container fluid>
            <Row>
                {usuario.id_rol === 1 ?
                <div className="center">
                  <Col>
                      <h3 className="titulo"> Bienvenido Jefe de Proyectos: {usuario.nombre_usuario}</h3>
                  </Col>
                </div>:
                <div className="center">
                  <Col>
                      <h3 className="titulo"> Bienvenido Usuario: {usuario.nombre_usuario}</h3>
                  </Col>
                </div>
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
                  <div className="filterResponsive">
                    <div className="filterBlock">
                        <input
                            type="text"
                            onClick={this.onChange}
                            onChange={this.onUserChange}
                            placeholder="Buscar Proyecto..."
                            ref={this.node}
                        />
                    </div>
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
            <div>
              <Row className="ProyectosList">
                {proyectosJefe.map((proyectos) => (
                  <Col  lg={4} key={proyectos.id_proyecto}>
                    <Card className="bg-light text-black">
                      <Card.Body>
                          <Card.Title>{proyectos.nombre_proyecto}</Card.Title>
                          <Card.Subtitle>Fecha de inicio: {proyectos.fecha_inicio_proyecto}</Card.Subtitle>
                          <p>
                            Objetivo: {proyectos.objetivo_proyecto}
                          </p>
                          <p>
                            Estado: {proyectos.estado_proyecto === false ? "Disponible" : "Terminado"}
                          </p>
                          <p>
                            Creador Proyecto: {proyectos.creadorProyecto}
                          </p>
                          <div>
                          <Button id="ingresarAproyecto"
                              variant="outline-primary" href={`/ingresarAProyecto/${proyectos.id_proyecto}`}
                            >
                              Ingresar a proyecto
                            </Button>
                            {usuario.id_rol === 1 && usuario.correo_usuario === proyectos.correoCreador && proyectos.estado_proyecto === false ?
                              <Button className= "botonCerrar"
                                variant="outline-danger" onClick={() => this.CerrarProyecto(proyectos.id_proyecto)}
                              >
                                Cerrar Proyecto
                              </Button>:
                              ""
                            }
                            {proyectos.estado_proyecto === true ?
                              <Button className= "botonCerrar"
                                variant="secondary" disabled
                              >
                                Estado Cerrado
                              </Button>:
                              ""
                            }
                          </div>
                          <div className="center">
                          {usuario.id_rol === 1 && usuario.correo_usuario === proyectos.correoCreador && proyectos.estado_proyecto === false ?
                              <Button id="editarProyecto" className= "botonCerrar" size="sm"
                                variant="warning" onClick={() => this.EditarProyecto(proyectos.id_proyecto)}
                              >
                                Editar Proyecto
                              </Button>:
                              ""
                          }
                          </div>
                        </Card.Body>
                      </Card>
                  </Col>
                ))}
              </Row>
            </div>
        </Container>
        </div>
      </div>
    );
  }
}

export default MisProyectos;
