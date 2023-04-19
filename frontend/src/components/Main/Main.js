import React, { Component} from "react";
import {Container, Col, Row, Card } from "react-bootstrap";
import NavbarLogeado from "./NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";
import swal from "sweetalert";
import "./NavbarLogeado.css"
import "bootstrap/dist/css/bootstrap.min.css";


class Main extends Component {
  constructor(props) {
    super(props);
    this.state = {
      usuario: [],
      id: null,
      proyectos: [],
      proyectosFiltro: []
    };
    this.node = React.createRef();
  }

  componentDidMount() {
    if (localStorage.getItem("token") == null && localStorage.getItem("id_rol") == null ){
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
        .get("http://localhost:8080/usuarioProyectosGeneral/"+id)
        .then((res) => {
          const proyectos = res.data;
          this.setState({proyectos});
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
      .get("http://localhost:8080/usuarioProyectosGeneral/"+id)
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
      proyectos: filtroProyectos,
    });
  };

  eliminarProyecto(id_proyecto) {
    let idPath = window.location.pathname.split("/");
    swal({
      title: "Atención si selecciona sí el proyecto se eliminará",
      text: "¿Desea eliminar el proyecto seleccionado?",
      icon: "warning",
      buttons: ["No", "Si"],
    }).then((respuesta) => {
      if (respuesta) {
        axios.delete("http://localhost:8080/proyecto/" + id_proyecto).then((res) => {
          swal({
            title: "Proyecto eliminado",
            text: "El proyecto ha sido eliminado con éxito",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/main");
          }, 2000);
        });
      }
    });
  }

  render() {
    const {usuario} = this.state;
    const {proyectos} = this.state;
    return (
      <div>
        <div>
          <NavbarLogeado />
        </div>
        <div>
        <Container fluid>
            {usuario.id_rol === 3 ?
            <div>
                <Col>
                  <h3 className="titulo"> Bienvenido administrador: {usuario.nombre_usuario}</h3>
                </Col>
            </div>
            :
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
            }
            {usuario.id_rol === 3 ?
              <div className="InformacionCentral">
                <h3 className="centerTitulo"> Proyectos disponibles</h3>
                <Button id="UsuarioRegistrados" className="botonCrearProyectoUsuario"  href="/usuarios" size="lg">
                Usuarios
                </Button>
                <Col>
                <div className="filterResponsive">
                    <div className="filterBlockUsuario">
                      <input
                        type="text"
                        onClick={this.onChange}
                        onChange={this.onUserChange}
                        placeholder="Buscar Proyecto..."
                        ref={this.node}
                      />
                    </div>
                  </div>
                </Col>

            </div> 
            :
            <div className="InformacionCentral">
                {usuario.id_rol === 1 ?
                <Button id="crearProyecto" className="botonCrearProyecto"  href="/crearProyecto" size="lg">
                Crear Proyecto
                </Button>:
                <h3 className="centerTitulo"> Proyectos disponibles</h3>
                }
                {usuario.id_rol === 1 ?
                <Button id="misProyectos" className="botonCrearProyecto"  href="/misProyectos" size="lg">
                Mis proyectos
                </Button>:
                <Button id="misProyectos" className="botonCrearProyectoUsuario"  href="/misProyectos" size="lg">
                Mis proyectos
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
                  <div className="filterResponsive">
                    <div className="filterBlockUsuario">
                      <input
                        type="text"
                        onClick={this.onChange}
                        onChange={this.onUserChange}
                        placeholder="Buscar Proyecto..."
                        ref={this.node}
                      />
                    </div>
                    </div>
                </Col>
                }
            </div> 
            }
            <div>
            <Container fluid>
            <Row className="ProyectosList">
              {proyectos.map((proyecto) => (
                <Col lg={4} key={proyecto.id_proyecto}>
                  <Card className="bg-light text-black">
                    <Card.Body>
                        <Card.Title>{proyecto.nombre_proyecto}</Card.Title>
                        <Card.Subtitle>Fecha de inicio: {proyecto.fecha_inicio_proyecto}</Card.Subtitle>
                        <p>
                          Objetivo: {proyecto.objetivo_proyecto}
                        </p>
                        <p>
                          Estado: {proyecto.estado_proyecto === false ? "Disponible" : "Terminado"} 
                        </p>
                        <p>
                          Creador: {proyecto.creadorProyecto} 
                        </p>
                        {usuario.id_rol === 3 ?
                        <div className="center">
                        <Button id= "EliminarProyecto"
                           variant="danger"
                          onClick={() => this.eliminarProyecto(proyecto.id_proyecto)}
                        >
                          Eliminar Proyecto
                        </Button>
                        </div>
                
                        :
                        <div className="center">
                          <Button id= "verMasProyecto"
                            variant="outline-primary" href={`/verMasProyecto/${proyecto.id_proyecto}`}
                          >
                            Ver más
                          </Button>
                        </div>
                        }
                      </Card.Body>
                    </Card>
                </Col>
              ))}
            </Row>
            </Container>
            </div>
        </Container>
        </div>
      </div>
    );
  }
}

export default Main;
