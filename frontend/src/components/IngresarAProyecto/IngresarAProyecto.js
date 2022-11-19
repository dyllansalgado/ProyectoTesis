import React, { Component} from "react";
import {Container, Col, Row, Card} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import "./IngresarAProyecto.css";
import axios from "axios";
import "../Main/NavbarLogeado.css";
import {BsArrowReturnLeft} from "react-icons/bs";


class IngresarAProyecto extends Component {
  constructor(props) {
      super(props);
      this.state = {
        usuario: [],
        id: null,
        proyecto:[],
        reunion:[]
      };
      this.node = React.createRef();
    }
  componentDidMount() {
    if (localStorage.getItem("token") == null && localStorage.getItem("id_rol") === null ){
      window.location.replace("http://localhost:3000/");
    }
    const id = localStorage.getItem('usuario');
    let idPath = window.location.pathname.split("/");
    axios.all([
      axios
          .get(
            "http://localhost:8080/usuario/"+id)
          .then((res) => {
          const usuario = res.data;
          this.setState({usuario});
          if(usuario.id_rol === 1){
              const rol = "Jefe de Proyecto";
              this.setState({rol});
          }else if(usuario.id_rol === 2){
              const rol = "Usuario";
              this.setState({rol});
          }
          })
          .catch((error) => {
            console.log(error);
      }),
      axios
        .get("http://localhost:8080/reunionProyecto/"+ idPath[2])
        .then((res) => {
          const reunion = res.data;
          this.setState({reunion});
          console.log(reunion);
        })
        .catch((error) => {
          console.log(error);
      }),
      axios
        .get("http://localhost:8080/proyecto/"+ idPath[2])
        .then((res) => {
          const proyecto = res.data;
          this.setState({ proyecto});
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
    let idPath = window.location.pathname.split("/");
    await axios
      .get("http://localhost:8080/reunionProyecto/"+ idPath[2])
      .then((res) => {
        this.setState({
          reunionesFiltro: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
      });

      let filter = e.target.value.toLowerCase();
      let filtroReuniones = this.state.reunionesFiltro.filter((e) => {

        let dataFilter = e.lugar_reunion.toLowerCase();
        let dataFiltroReuniones= e.fecha_reunion.toLowerCase();
        return (
          dataFilter
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1 ||
          dataFiltroReuniones
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1
          );
      });

    this.setState({
      reunion: filtroReuniones,
    });
  };

  render() {
    const {usuario} = this.state;
    const {proyecto} = this.state;
    const {reunion} = this.state;
    return ( 
    <div>
        <div>
          <NavbarLogeado/>
        </div>
        <div>
            <Container fluid>
              <Row>
                  <h2 className="titulo"> Reuniones disponibles: {usuario.nombre_usuario}</h2>
                  <div className="container-fluid cew-9">
                      <div className="row">
                        <div className= "subtitulo">
                          Nombre del Proyecto: {proyecto.nombre_proyecto}
                        </div>
                        <div className="subtitulo">
                          Objetivos: {proyecto.objetivo_proyecto}
                        </div>
                      </div>
                  </div>
              </Row>
              <div className="InformacionCentral">
                {usuario.id_rol === 1 && proyecto.estado_proyecto.toString() === "false" ?
                <Button className="botonCrearProyecto"   href={`/crearReunion/${proyecto.id_proyecto}`} size="lg">
                  Crear Reunion
                </Button>: ""
                }
                <Button className="botonCrearProyecto"   href="/misProyectos/" size="lg">
                  Volver
                  <BsArrowReturnLeft/> <span></span>
                </Button>
                <Col>
                  <div className="filterResponsive">
                    <div className="filterBlock">
                        <input
                          type="text"
                          onClick={this.onChange}
                          onChange={this.onUserChange}
                          placeholder="Buscar Reunion..."
                          ref={this.node}
                        />
                    </div>
                  </div>
                </Col>
              </div>
              <div>
                <Row className="ReunionList">
                  {reunion.map((meet) => (
                    <Col lg={4} key={meet.id_reunion}>
                      <Card className="bg-light text-black">
                        <Card.Body>
                            <Card.Title>Fecha de reunion: {meet.fecha_reunion}</Card.Title>
                            <Card.Subtitle>Lugar: {meet.lugar_reunion}</Card.Subtitle>
                            <p>
                              Estado: {meet.estado.toString() === 'false' ? "Disponible" : "Terminado"}
                            </p>
                            <div className="center">
                              <Button
                                variant="outline-primary" href={`/ingresarReunion/${proyecto.id_proyecto}/${meet.id_reunion}`}
                              >
                                Ingresar a reunion
                              </Button>
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
export default IngresarAProyecto;