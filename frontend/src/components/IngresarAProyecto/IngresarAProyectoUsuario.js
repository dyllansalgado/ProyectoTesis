import React, { Component} from "react";
import {Container, Col, Row} from "react-bootstrap";
import NavbarLogeadoJP from "../Main/NavbarLogeadoJP.js";
import Button from 'react-bootstrap/Button';
import "./IngresarAProyecto.css";
import axios from "axios";


class IngresarAProyectoUsuario extends Component {
    constructor(props) {
        super(props);
        this.state = {
          usuario: [],
          id: null,
          proyecto:[],
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
              .get("http://localhost:8080/usuarioProyectos/"+id)
              .then((res) => {
                const proyectosUsuario = res.data;
            
                this.setState({proyectosUsuario});
                console.log(proyectosUsuario);
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
    render() {
        const {usuario} = this.state;
        const {proyecto} = this.state;
        return ( 
        <div>
            <div>
              <NavbarLogeadoJP />
            </div>
            <div class="fondoB">
                <Container fluid>
                    <Row>
                        <h2 className="centerTitulo"> Reuniones disponibles: {usuario.nombre_usuario}</h2>
                        <div className="container-fluid cew-9">
                            <div className="row">
                                <div className="col">
                                    Objetivos: {proyecto.objetivo_proyecto}
                                </div>
                            </div>
                        </div>
                    </Row>
                        <div className="InformacionCentralIngresarProyecto">
                        <Button className="botonIrAGlosarioUser"  href="/" size="lg">
                              Ir a glosario
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
                </Container>
            </div>
        </div>
        );
    }
}
export default IngresarAProyectoUsuario;