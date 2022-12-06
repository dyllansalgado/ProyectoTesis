import React, { Component} from "react";
import {Container, Col, Row, Table, Modal, Form, ModalHeader, ModalBody,} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import "../IngresarAProyecto/IngresarAProyecto.css";
import axios from "axios";
import swal from "sweetalert";
import Dropdown from 'react-bootstrap/Dropdown';
import "./Tema.css";
import {AiOutlineCheck} from "react-icons/ai";
import {BsArrowReturnLeft} from "react-icons/bs";
import {AiFillLike} from "react-icons/ai";
import "../Main/NavbarLogeado.css";

class Tema extends Component { 
    constructor(props) {
      super(props);
      this.state = {
        usuario: [],
        id: null,
        proyecto:[],
        reunion:[],
        tema:[],
        estado: false,
        preguntaNueva: "",
        preguntas:[],
        idPregunta: null,
        votos:[],
        preguntasFiltro:[],
        preguntasRecomendadas:[],
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
          .get("http://localhost:8080/tema/"+ idPath[4])
          .then((res) => {
            const tema = res.data;
            this.setState({tema});
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
        .get("http://localhost:8080/votos/")
        .then((res) => {
          const votos = res.data;
          this.setState({ votos});
        })
        .catch((error) => {
          console.log(error);
        }),
        axios
        .get("http://localhost:8080/preguntaTema/"+ idPath[4])
        .then((res) => {
          const preguntas = res.data;
          this.setState({preguntas});
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
        .get(
            "http://localhost:8080/usuario/"+id)
          .then((res) => {
            const usuario = res.data;
            this.setState({usuario});
          }),
        axios
        .get(
            "http://localhost:8080/preguntasRecomendadas/")
          .then((res) => {
            const preguntasRecomendadas = res.data;
            this.setState({preguntasRecomendadas});
          }),
      ]);
    }
    //Barra de busqueda
    onChange = (e) => {
      if (this.node.current.contains(e.target)) {
        return;
      }
      this.setState({
        preguntasFiltro: [],
      });
    };
    onUserChange = async (e) => {
      let idPath = window.location.pathname.split("/");
      await axios
      .get("http://localhost:8080/preguntaTema/"+ idPath[4])
      .then((res) => {
        this.setState({
          preguntasFiltro: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
      });
      let filter = e.target.value.toLowerCase();
      let filtroPreguntas = this.state.preguntasFiltro.filter((e) => {
        let dataFilter = e.pregunta.toLowerCase();
        return (
          dataFilter
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1
        );
      });
      this.setState({
        preguntas: filtroPreguntas,
      });
    };

    IngresarNuevaPregunta = (e) => {
      let idPath = window.location.pathname.split("/");
      e.preventDefault();
      if (
        this.state.preguntaNueva !== ""){
        axios.post("http://localhost:8080/pregunta/create/"+localStorage.getItem('usuario'), {
          pregunta: this.state.preguntaNueva,
          estado: this.state.estado,
          id_tema: idPath[4]
        });
        swal({
          title: "Pregunta creada con éxito",
          text: "Se ha creado correctamente la nueva pregunta",
          icon: "success",
        });
        setTimeout(() => {
          window.location.replace("http://localhost:3000/temaReunion/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
        }, 2000);
      }
      else {
        swal({
          title: "Error al crear la pregunta",
          text: "falla",
          icon: "warning",
        });
      }
    };

    AceptarPregunta(idPregunta) {
      let idPath = window.location.pathname.split("/");
      swal({
        title: "Atención",
        text: "¿Desea aceptar la pregunta seleccionada?",
        icon: "warning",
        buttons: ["No", "Si"],
      }).then((respuesta) => {
        if (respuesta) {
          axios.put("http://localhost:8080/pregunta/" + idPregunta, {
            estado: true,
          });
          swal({
            title: "Pregunta aceptada con éxito",
            text: "La pregunta ha sido aceptada con éxito",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/temaReunion/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
          }, 2000);
        }
      });
    }

    VotarPregunta(idUsuario,idPregunta){
      let idPath = window.location.pathname.split("/");
      swal({
        title: "Atención",
        text: "¿Desea votar la pregunta seleccionada?",
        icon: "warning",
        buttons: ["No", "Si"],
      }).then((respuesta) => {
        if (respuesta) {
          axios.post("http://localhost:8080/voto/create", {
            tipo_voto: true ,
            id_pregunta: idPregunta,
            id_usuario: idUsuario
          }).then((response) => {
            if (response.data === false) {
              swal({
                title: "Ya ha votado por esta pregunta",
                text: "Por favor vote otra pregunta",
                icon: "warning",
              });
              setTimeout(() => {
                  window.location.replace("http://localhost:3000/temaReunion/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
              }, 2000);
            }
          })
          swal({
            title: "Pregunta calificada con éxito",
            text: "La pregunta ha sido calificada con éxito",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/temaReunion/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
          }, 2000);
        }
      });
    }
    deletePregunta(id_pregunta) {
      let idPath = window.location.pathname.split("/");
      swal({
        title: "Atención",
        text: "¿Desea eliminar la pregunta seleccionada?",
        icon: "warning",
        buttons: ["No", "Si"],
      }).then((respuesta) => {
        if (respuesta) {
          axios.delete("http://localhost:8080/pregunta/" + id_pregunta).then((res) => {
            swal({
              title: "Pregunta borrada",
              text: "La pregunta ha sido borrada con éxito",
              icon: "success",
            });
            setTimeout(() => {
              window.location.replace("http://localhost:3000/temaReunion/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
            }, 2000);
          });
        }
      });
    }
    render() {
      const {proyecto} = this.state;
      const {tema} = this.state;
      const preguntaNueva = this.state.pregunta;
      const {preguntas} = this.state;
      const {reunion}= this.state;
      const {usuario}= this.state;
      const {preguntasRecomendadas} =this.state;
      return ( 
      <div>
          <div>
            <NavbarLogeado />
          </div>
          <div>
            <Container fluid>
              <Row>
                <h3 className="titulo"> Tema seleccionado: {tema.nombre_tema}</h3>
                <div className="container-fluid cew-9">
                  <div className="row">
                    <div className= "subtitulo">
                      Nombre del Proyecto: {proyecto.nombre_proyecto}
                    </div>
                  </div>
                </div>
              </Row>
              <div className="col-md-12 school-options-dropdown text-center">
              <Dropdown>
              <Button id="preguntasSeleccionadas" className="botonSeleccionadas"
                variant="primary" href={`/preguntasSeleccionadas/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}`}
                >
                Ingresar a preguntas seleccionadas
              </Button>
              <Dropdown.Toggle id="dropdown-basic-button">
                Preguntas Recomendadas
              </Dropdown.Toggle>
                <Dropdown.Menu  className="dropdownTamaño">
                  <Dropdown>* Debes seleccionar la pregunta y se guardará en el portapapeles *</Dropdown>
                  <Dropdown.Divider />
                  {preguntasRecomendadas.map((preguntasReco) => (
                     <Dropdown.Item onClick={() => navigator.clipboard.writeText(preguntasReco.pregunta_recomendada)} >
                      {preguntasReco.pregunta_recomendada}
                      </Dropdown.Item>
                    ))}
                </Dropdown.Menu>
              </Dropdown>
              </div>
              <div className="InformacionCentral_2">
              {proyecto.estado_proyecto === false ?
              <Button
                className="botonCrearProyecto"  
                id="crearPregunta"
                onClick={() => this.handleModal()}
                size="lg">
                Crear Pregunta
              </Button>:
              ""
              }
              <Button
                className="botonCrearProyecto"  
                id="volver"
                href={`/ingresarReunion/${proyecto.id_proyecto}/${reunion.id_reunion}`}
                size="lg">
                Volver
                <BsArrowReturnLeft/> <span></span>
              </Button>
              <Modal
                name="formato"
                show={this.state.showModal}
                onHide={() => this.handleModal()}
              >
                <ModalHeader closeButton>
                  Creando pregunta del tema {tema.nombre_tema}
                </ModalHeader>
                <ModalBody>
                  <Form onSubmit={this.IngresarNuevaPregunta}>
                      <p> Pregunta </p>
                      <input
                        type="text"
                        value={preguntaNueva}
                        className="form-control"
                        name="preguntaNueva"
                        onChange={this.changeHandler}
                        placeholder="Pregunta..."
                        required
                      />
                      <Button
                        id="crear"
                        name="botonCrearPregunta"
                        type="submit"
                      >
                        {" "}
                        Crear
                      </Button>
                  </Form>
                </ModalBody>
              </Modal>
                <Col>
                    <div className="filterBlockUsuario">
                        <input
                          type="text"
                          onClick={this.onChange}
                          onChange={this.onUserChange}
                          placeholder="Buscar pregunta..."
                          ref={this.node}
                        />
                    </div>
                </Col>
              </div>
              <Container fluid>
                <Table striped bordered hover className="tablaTermino">
                  <thead>
                    <tr>
                      <th width="900">Pregunta</th>
                      <th width="250">Creador</th>
                      {usuario.id_rol === 1 ?
                      <th width="170">Aceptar Pregunta</th>:
                      <th width="170">Votar Pregunta</th>
                      }
                      <th width="170">Estado</th>
                      <th width="100">Votos</th>
                      <th width="50">Acción</th>
                      <th width="50">Comentarios</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                    preguntas.map((pregunta) => (
                      <tr key={pregunta.id_pregunta} >
                        <td> {pregunta.pregunta} </td>
                        <td> {pregunta.creador} </td>
                        {usuario.id_rol === 1 ?
                        <td>
                          {" "}
                          {pregunta.estado === false ?
                            <Button id="aceptarPregunta"
                              variant="success"
                              onClick={() => this.AceptarPregunta(pregunta.id_pregunta)}
                            >
                            {" "}
                              Aceptar{" "}
                              <AiOutlineCheck/> <span></span>
                            </Button>: 
                            <Button
                            variant="secondary" disabled
                            >
                            {" "}
                            Aceptada{" "}
                            <AiOutlineCheck/> <span></span>
                            </Button>
                          }
                        </td>:
                        <td>
                          {proyecto.estado_proyecto === false && pregunta.estado === false ?
                            <Button id="votarPregunta"
                              variant="success"
                              onClick={() => this.VotarPregunta(usuario.id_usuario,pregunta.id_pregunta)}
                            >
                            {" "}
                              Votar{" "}
                              <AiFillLike/> <span></span>
                            </Button>:
                            <Button
                            variant="secondary"
                            disabled
                          >
                          {" "}
                            Votar{" "}
                            <AiFillLike/> <span></span>
                          </Button>
                            }
                        </td>
                        }
                        <td>
                          {pregunta.estado === false ?
                          <p> Aun no Aceptada </p>
                           :
                          <p> Aceptada </p>
                          }
                        </td>
                        <td>
                          {pregunta.votos}
                        </td>
                        {usuario.id_rol === 1 ?
                        <td>
                          {pregunta.estado === false ?
                            <Button
                              id="eliminarPregunta"
                              variant="danger"
                              onClick={() => this.deletePregunta(pregunta.id_pregunta)}
                            >
                            {" "}
                              Eliminar{" "}
                            </Button>:
                            <Button
                            variant="secondary"
                            disabled
                          >
                          {" "}
                            Eliminar{" "}
                          </Button>
                            }
                        </td>:
                          <td>
                          {proyecto.estado_proyecto === false && usuario.correo_usuario === pregunta.correoCreador && pregunta.estado === false ?
                            <Button
                              id="eliminarPregunta"
                              variant="danger"
                              onClick={() => this.deletePregunta(pregunta.id_pregunta)}
                            >
                            {" "}
                              Eliminar{" "}
                            </Button>:
                            <Button
                            variant="secondary"
                            disabled
                          >
                          {" "}
                            Eliminar{" "}
                          </Button>
                            }
                          </td> 
                        }

                        <td>
                          {pregunta.estado === false ?
                            <Button
                              id="comentarPregunta"
                              variant="success"
                              href={`/comentarPregunta/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}/${pregunta.id_pregunta}`}
                            >
                            {" "}
                              Comentarios{" "}
                            </Button>:
                            <Button
                            variant="secondary"
                            disabled
                          >
                          {" "}
                            Comentarios{" "}
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
export default Tema ;