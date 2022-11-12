import React, { Component} from "react";
import {Container, Col, Row, Card} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import "../IngresarAProyecto/IngresarAProyecto.css";
import "./CrearReunion.css";
import axios from "axios";
import {BsArrowReturnLeft} from "react-icons/bs";

class IngresarReunionUsuario extends Component { 
    constructor(props) {
        super(props);
        this.state = {
          usuario: [],
          id: null,
          proyecto:[],
          reunion:[],
          tema:[],
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
          .get("http://localhost:8080/temaReunion/"+ idPath[3])
          .then((res) => {
            const tema = res.data;
          
            this.setState({tema});
            console.log(tema);
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
        const {reunion} = this.state;
        const {tema} = this.state;
        return ( 
        <div>
            <div>
              <NavbarLogeado />
            </div>
            <div className="fondoB">
                <Container fluid>
                  <Row>
                      <h2 className="centerTitulo"> Temas disponibles: {usuario.nombre_usuario}</h2>
                  </Row>
                    <div className="InformacionCentralIngresarProyecto">
                    <Button className="botonIrAGlosarioReunion"  href= {`/GlosarioReunionUsuario/${proyecto.id_proyecto}/${reunion.id_reunion}`} size="lg">
                      Ir a glosarios
                    </Button>
                    <Button className="botonIrAGlosarioReunion"  href={`/ingresarAProyectoUsuario/${proyecto.id_proyecto}`} size="lg">  
                      Volver
                      <BsArrowReturnLeft/> <span></span>
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
                      {tema.map((temas) => (
                        <Col className="col" key={temas.id_tema}>
                          <Card style={{ width: "20rem" }}>
                            <Card.Body>
                                <Card.Title>Nombre: {temas.nombre_tema}</Card.Title>
                                <Card.Subtitle>Descripcion: {temas.descripcion_tema}</Card.Subtitle>
                                <p>
                                  Estado: {temas.estado.toString() === 'false' ? "Disponible" : "Terminado"}
                                </p>
                                <div className="center">
                                  <Button
                                    variant="outline-primary" href={`/temaReunionUsuario/${proyecto.id_proyecto}/${reunion.id_reunion}/${temas.id_tema}`}
                                  >
                                    Ingresar a tema
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
export default IngresarReunionUsuario ;