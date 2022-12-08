import React, { Component} from "react";
import {Container, Col, Row, Table, Modal, Form, ModalHeader, ModalBody,} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";
import "../Glosario/Glosario.css";
import swal from "sweetalert";
import "../Main/NavbarLogeado.css";
import {BsArrowReturnLeft } from "react-icons/bs";


class ComentarPregunta extends Component {
    constructor(props) {
        super(props);
        this.state = {
          usuario: [],
          id: null,
          proyecto:[],
          reunion:[],
          pregunta:[],
          comentarios:[],
          comentario:"",
          tema:[],
        };
        this.node = React.createRef();
        this.changeComentario = this.changeComentario.bind(this);
        this.handleModal = this.handleModal.bind(this);
    }

    handleModal() {
        this.setState({ showModal: !this.state.showModal });
    };
  
    changeHandler = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }; 

    changeComentario(event) {
        this.setState({comentario: event.target.value });
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
                    this.setState({ reunion});
                })
                .catch((error) => {
                    console.log(error);
            }),
            axios
            .get("http://localhost:8080/tema/"+ idPath[4])
            .then((res) => {
              const tema = res.data;
              this.setState({tema});
            })
            .catch((error) => {
              console.log(error);
            }),
            axios
            .get("http://localhost:8080/comentarioPregunta/"+ idPath[5])
            .then((res) => {
              const comentarios = res.data;
              this.setState({comentarios});
            })
            .catch((error) => {
            console.log(error);
            }),
            axios
            .get("http://localhost:8080/pregunta/"+ idPath[5])
            .then((res) => {
                const pregunta = res.data;
                this.setState({ pregunta});
            })
            .catch((error) => {
                console.log(error);
            
        }),
        ]);
    }
    IngresarNuevoComentario = (e) => {
        let idPath = window.location.pathname.split("/");
        e.preventDefault();
        if (
        this.state.comentario !== ""){
        axios.post("http://localhost:8080/comentario/create/"+ idPath[5] + "/" + localStorage.getItem('usuario'), {
            comentario: this.state.comentario
        });
        swal({
            title: "Comentario creado con éxito",
            text: "Se ha creado correctamente el comentario",
            icon: "success",
        });
        setTimeout(() => {
            window.location.replace("http://localhost:3000/comentarPregunta/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4] + "/" + idPath[5] );
        }, 2000);
        }
        else {
            swal({
              title: "Error al crear el comentario",
              text: "falla",
              icon: "warning",
            });
        }
    };

    deleteComentario(id_comentario) {
        let idPath = window.location.pathname.split("/");
        swal({
          title: "Atención",
          text: "¿Desea eliminar el comentario seleccionado?",
          icon: "warning",
          buttons: ["No", "Si"],
        }).then((respuesta) => {
          if (respuesta) {
            axios.delete("http://localhost:8080/comentario/" + id_comentario).then((res) => {
              swal({
                title: "Comentario borrado",
                text: "El comentario ha sido borrado con éxito",
                icon: "success",
              });
              setTimeout(() => {
                window.location.replace("http://localhost:3000/comentarPregunta/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4] + "/" + idPath[5] );
              }, 2000);
            });
          }
        });
    }
    editarComentario(id_comentario){
        let idPath = window.location.pathname.split("/");
        swal({
          title: "Atención",
          text: "¿Desea modificar el comentario seleccionado?",
          icon: "warning",
          buttons: ["No", "Si"],
        }).then((respuesta) => {
          if (respuesta) {
            setTimeout(() => {
              window.location.replace("http://localhost:3000/EditarComentario/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]+ "/" + idPath[5] + "/" + id_comentario);
            }, 2000);
          }
        });
      }
    render() { 
        const {proyecto} = this.state;
        const {reunion} = this.state;
        const {pregunta} = this.state;
        const {usuario}= this.state;
        const {tema} = this.state;
        const {comentarios} = this.state;
        return(
            <div>
                <div>
                    <NavbarLogeado />
                </div>
                <div>
                    <Container fluid>
                        <Row>
                          <h2 className="titulo"> Pregunta seleccionada: {pregunta.pregunta}</h2>
                          <div className="container-fluid cew-9">
                              <div className="row">
                                  <div className="subtitulo">
                                      Fecha de reunion: {reunion.fecha_reunion} / Lugar de reunion : {reunion.lugar_reunion}
                                  </div>
                              </div>
                          </div>
                        </Row>
                        <div className="InformacionCentral">
                            {proyecto.estado_proyecto === false ?
                            <Button
                                id="crearComentario"
                                className="botonCrearProyecto"  
                                onClick={() => this.handleModal()}
                                size="lg">
                                Crear Comentario
                            </Button>:
                            ""

                            }
                            <Modal
                                name="formato"
                                show={this.state.showModal}
                                onHide={() => this.handleModal()}
                            >
                                <ModalHeader closeButton>
                                    Creando comentario para pregunta: {pregunta.pregunta}
                                </ModalHeader>
                                <ModalBody>
                                    <Form onSubmit={this.IngresarNuevoComentario}>
                                        <p> Comentario </p>
                                        <input
                                            type="text"
                                            value={this.state.comentario}
                                            className="form-control"
                                            name="comentario"
                                            onChange={this.changeHandler}
                                            placeholder="Ingresar comentario"
                                            required
                                        />
                                        <Button
                                            id="crear"
                                            name="botonCrearComentario"
                                            type="submit"
                                        >
                                            {" "}
                                          Crear comentario
                                        </Button>
                                    </Form>
                                </ModalBody>
                            </Modal>
                            <Button id="volver" className="botonCrearProyecto" href={`/TemaReunion/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}`} size="lg">
                                    Volver
                                    <BsArrowReturnLeft/> <span></span>
                            </Button>
                        </div>
                        <Container fluid>
                            <Table striped bordered hover className="tablaTermino" >
                                <thead>
                                    <tr>
                                        <th width="1500">Comentario</th>
                                        <th width="200">Creador Comentario</th>
                                        <th width="200">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                    comentarios.map((comentario) => (
                                        <tr key={comentario.id_comentario} >
                                            <td> {comentario.comentario} </td>
                                            <td> {comentario.nombre_creador_comentario}</td>
                                            <td> 
                                                {proyecto.estado_proyecto === false && usuario.correo_usuario === comentario.correo_creador_comentario || usuario.id_rol === 1 ?
                                                  <Button id="eliminarComentario" size="sm"
                                                    variant="danger"
                                                    onClick={() => this.deleteComentario(comentario.id_comentario)}
                                                  >
                                                  {" "}
                                                    Eliminar{" "}
                                                  </Button>:
                                                  <Button className = "botones" id="eliminarComentario" size="sm"
                                                  variant="secondary"
                                                  disabled
                                                >
                                                {" "}
                                                  Eliminar{" "}
                                                </Button>
                                                }

                                                {proyecto.estado_proyecto === false && usuario.correo_usuario === comentario.correo_creador_comentario || usuario.id_rol === 1 ?
                                                  <Button className = "botones" size="sm"
                                                    id="editarComentario"
                                                    variant="warning"
                                                    onClick={() => this.editarComentario(comentario.id_comentario)}
                                                    
                                                  >
                                                  {" "}
                                                    Editar{" "}
                                                  </Button>:
                                                  <Button className = "botones" size="sm"
                                                  id="editarComentario"
                                                  variant="secondary"
                                                  disabled
                                                >
                                                {" "}
                                                  Editar{" "}
                                                </Button>
                                                }
                                                
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </Table>
                        </Container>
                    </Container>
                </div>
            </div>
        );
    }
}

export default ComentarPregunta;