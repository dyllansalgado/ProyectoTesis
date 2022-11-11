import React, { Component} from "react";
import {Container, Col, Row, Card, Modal, Form, ModalHeader, ModalBody,} from "react-bootstrap";
import NavbarLogeadoJP from "../Main/NavbarLogeadoJP.js";
import Button from 'react-bootstrap/Button';
import "../IngresarAProyecto/IngresarAProyecto.css";
import axios from "axios";
import swal from "sweetalert";
class IngresarReunionJP extends Component { 
    constructor(props) {
        super(props);
        this.state = {
          usuario: [],
          id: null,
          proyecto:[],
          reunion:[],
          tema:[],
          estado: false,
          nombreTema: "",
          descripcionTema: "",
        };
        this.node = React.createRef();
        this.handleModal = this.handleModal.bind(this);
      }

    handleModal() {
        this.setState({ showModal: !this.state.showModal });
    };
    
    changeHandler = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    };
    
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

    IngresarNuevoTema = (e) => {
        let idPath = window.location.pathname.split("/");
        e.preventDefault();
        if (
            this.state.nombreTema !== "" &&
            this.state.descripcionTema !== ""){
            axios.post("http://localhost:8080/tema/create", {
                nombre_tema: this.state.nombreTema,
                descripcion_tema: this.state.descripcionTema,
                estado: this.state.estado,
                id_reunion: idPath[3]
            });
    
            swal({
                title: "Tema creado con exito",
                text: "Se ha creado correctamente el tema",
                icon: "success",
            });
            setTimeout(() => {
                window.location.replace("http://localhost:3000/ingresarReunionJP/"+ idPath[2] + "/" + idPath[3]);
            }, 2000);
            }
            else {
                swal({
                  title: "Error al crear el tema",
                  text: "falla",
                  icon: "warning",
                });
            }
        };
    render() {
        const {usuario} = this.state;
        const {proyecto} = this.state;
        const {tema} = this.state;
        const nombreTema = this.state.nombre_tema;
        const descripcionTema = this.state.descripcion_tema;
        const {reunion}= this.state;
        return ( 
        <div>
            <div>
              <NavbarLogeadoJP />
            </div>
            <div className="fondoB">
                <Container fluid>
                    <Row>
                        <h2 className="centerTitulo"> Temas disponibles: {usuario.nombre_usuario}</h2>
                    </Row>
                    <div className="InformacionCentralIngresarProyecto">
                    <Button
                        className="botonCrearTema"  
                        onClick={() => this.handleModal()}
                        size="lg">
                        Crear tema
                    </Button>
                    <Modal
                        name="formato"
                        show={this.state.showModal}
                        onHide={() => this.handleModal()}
                    >
                        <ModalHeader closeButton>
                            Creando tema de proyecto {proyecto.nombre_proyecto}
                        </ModalHeader>
                        <ModalBody>
                            <Form onSubmit={this.IngresarNuevoTema}>
                                <p> Tema </p>
                                <input
                                    type="text"
                                    value={nombreTema}
                                    className="form-control"
                                    name="nombreTema"
                                    onChange={this.changeHandler}
                                    placeholder="Nombre de tema..."
                                    required
                                />
                                <p> Descripción </p>
                                <input
                                    id="descripcion"
                                    type="text"
                                    placeholder="Descripción"
                                    className="form-control"
                                    value={descripcionTema}
                                    name="descripcionTema"
                                    onChange={this.changeHandler}
                                    required
                                />
                                <Button
                                    id="crearTema"
                                    name="botonCrearTema"
                                    type="submit"
                                >
                                    {" "}
                                  Crear
                                </Button>
                            </Form>
                        </ModalBody>
                    </Modal>
                    <Button className="botonIrAGlosario" href= {`/GlosarioReunionJP/${proyecto.id_proyecto}/${reunion.id_reunion}`} size="lg">
                          Ir a glosarios
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
                                  Estado: {temas.estado.toString()}
                                </p>
                                <div className="center">
                                  <Button
                                    variant="outline-primary" href={`/temaReunionJP/${proyecto.id_proyecto}/${reunion.id_reunion}/${temas.id_tema}`}
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
export default IngresarReunionJP ;