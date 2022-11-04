import React, { Component} from "react";
import {Container, Col, Row, Card} from "react-bootstrap";
import NavbarLogeadoJP from "../Main/NavbarLogeadoJP.js";
import Button from 'react-bootstrap/Button';
import "../IngresarAProyecto/IngresarAProyecto.css";
import "./Glosario.css";
import axios from "axios";


class GlosarioJP extends Component { 
    constructor(props) {
        super(props);
        this.state = {
          usuario: [],
          id: null,
          proyecto:[],
          reunion:[],
          glosario:[]
        };
        this.node = React.createRef();
    }
    
    componentDidMount() {
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
          .get("http://localhost:8080/proyecto/"+ idPath[2])
          .then((res) => {
            const proyecto = res.data;
            this.setState({ proyecto});
          })
          .catch((error) => {
            console.log(error);
        }),
        axios
        .get("http://localhost:8080/glosarioReunion/"+ idPath[3])
        .then((res) => {
          const glosario = res.data;
          this.setState({glosario});
        })
        .catch((error) => {
          console.log(error);
        }),
        axios
        .get("http://localhost:8080/reunion/"+ idPath[3])
        .then((res) => {
          const reunion = res.data;
          this.setState({reunion});
        })
        .catch((error) => {
          console.log(error);
        }),
      ]);
    }

    render() {
        const {usuario} = this.state;
        const {proyecto} = this.state;
        const {glosario} = this.state;
        const {reunion} = this.state;
        return ( 
        <div>
            <div>
              <NavbarLogeadoJP />
            </div>
            <div className="fondoB">
                <Container fluid>
                  <Row>
                      <h2 className="centerTitulo"> Glosarios disponibles: {usuario.nombre_usuario}</h2>
                      <div className="container-fluid cew-9">
                          <div className="row">
                              <div className="col">
                                  Objetivos: {proyecto.objetivo_proyecto}
                              </div>
                          </div>
                      </div>
                  </Row>
                    <div className="InformacionCentralIngresarProyecto">
                    <Button className="botonVolverGlosario"   href={`/ingresarReunionJP/${proyecto.id_proyecto}/${reunion.id_reunion}`} size="lg">
                          Volver
                    </Button>
                    <Button className="botonIrAGlosario"  href="/GlosarioReunionJP/" size="lg">
                          Crear Glosario
                    </Button>
                    <div className= "nombreProyecto">
                        Nombre del Proyecto: {proyecto.nombre_proyecto}
                    </div>
                        <Col>
                            <div className="filterBlockIngresar">
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
                    <Row className="ReunionList">
                      {glosario.map((glosa) => (
                        <Col className="col" key={glosa.id_glosario}>
                          <Card style={{ width: "20rem" }}>
                            <Card.Body>
                                <Card.Title>Nombre Glosario: {glosa.nombre_glosario}</Card.Title>
                                <Card.Subtitle>Descripción Glosario: {glosa.descripcion_glosario}</Card.Subtitle>
                                <div className="center">
                                  <Button
                                    variant="outline-primary"
                                  >
                                    Ingresar a reunion
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

export default GlosarioJP ;