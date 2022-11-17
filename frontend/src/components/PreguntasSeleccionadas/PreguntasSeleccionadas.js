import React, { Component} from "react";
import {Container, Col, Row, Table,} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import "../IngresarAProyecto/IngresarAProyecto.css";
import axios from "axios";
import "../Tema/Tema.css";
import "./PreguntasSeleccionadas.css";
import {BsArrowReturnLeft,BsDownload} from "react-icons/bs";
import jsPDF from "jspdf";
import "jspdf-autotable";
import "../Main/NavbarLogeado.css";
import swal from "sweetalert";

class PreguntasSeleccionadas extends Component { 
    constructor(props) {
      super(props);
      this.state = {
        usuario: [],
        id: null,
        proyecto:[],
        reunion:[],
        tema:[],
        preguntasSeleccionadas:[],
        idPregunta: null,
        respuestaCreada:"",
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
        .get("http://localhost:8080/preguntaSeleccionadaTema2/"+ idPath[4])
        .then((res) => {
          const preguntasSeleccionadas = res.data;
          this.setState({preguntasSeleccionadas});
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
    exportPDF = ()  => {
        swal({
        title: "Atención",
        text: "¿Desea descargar en archivo PDF?",
        icon: "warning",
        buttons: ["No", "Si"],
        }).then((respuesta) => {
            if (respuesta) {
                const unit = "pt";
                const size = "A4";
                //Orientación 
                const orientation = "portrait";
                const marginLeft = 40;
                const doc = new jsPDF(orientation, unit, size);
                doc.setFontSize(15);
                const title = "Nombre de Tema:  " + this.state.tema.nombre_tema;
                const headers = [["Pregunta", "Respuesta"]];
                const data = this.state.preguntasSeleccionadas.map(elt=> [elt.pregunta, elt.respuesta]);
                let content = {
                  startY: 50,
                  head: headers,
                  body: data
                };
                doc.text(title, marginLeft, 40);
                doc.autoTable(content);
                doc.save("Tema_"+this.state.tema.nombre_tema+".pdf")
            }
        });
    };

    IrResponderPregunta(id_pregunta) {
      let idPath = window.location.pathname.split("/");
      swal({
        title: "Atención",
        text: "¿Desea Responder la pregunta seleccionada?",
        icon: "warning",
        buttons: ["No", "Si"],
      }).then((respuesta) => {
        if (respuesta) {
          setTimeout(() => {
            window.location.replace("http://localhost:3000/PreguntaRespuesta/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4] + "/" + id_pregunta);
          }, 2000);
        }
      });
    }

    IrEditarRespuesta(id_pregunta,id_respuesta) {
      let idPath = window.location.pathname.split("/");
      swal({
        title: "Atención",
        text: "¿Desea editar la respuesta de la pregunta seleccionada?",
        icon: "warning",
        buttons: ["No", "Si"],
      }).then((respuesta) => {
        if (respuesta) {
          setTimeout(() => {
            window.location.replace("http://localhost:3000/EditarRespuesta/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4] + "/" + id_pregunta + "/" + id_respuesta);
          }, 2000);
        }
      });
    }

    CrearRequisitoPregunta(id_pregunta, id_respuesta){
      let idPath = window.location.pathname.split("/");
      swal({
        title: "Atención",
        text: "¿Desea crear un requisito?",
        icon: "warning",
        buttons: ["No", "Si"],
      }).then((respuesta) => {
        if (respuesta) {
          if(id_respuesta == null){
            setTimeout(() => {
              window.location.replace("http://localhost:3000/CrearRequisitoP/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4] + "/" + id_pregunta);
            }, 2000);
          }
          else if( id_respuesta != null){
            setTimeout(() => {
              window.location.replace("http://localhost:3000/CrearRequisitoPR/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4] + "/" + id_pregunta + "/" + id_respuesta);
            }, 2000);
          }
        }
      });
    }

    render() {
      const {proyecto} = this.state;
      const {tema} = this.state;
      const {preguntasSeleccionadas} = this.state;
      const {reunion}= this.state;
      return ( 
      <div>
          <div>
            <NavbarLogeado />
          </div>
          <div>
            <Container fluid className="container-fluid2">
              <Row>
                <h3 className="titulo"> Preguntas seleccionadas tema: {tema.nombre_tema}</h3>
                <div className="container-fluid cew-9">
                      <div className="row">
                        <div className= "subtitulo">
                          Nombre del Proyecto: {proyecto.nombre_proyecto}
                        </div>
                      </div>
                  </div>
              </Row>
              <div className="InformacionCentral">
              <Button className="botonCrearProyecto"   onClick={() => this.exportPDF()} size="lg">
                <BsDownload /> <span></span>
                  Descargar
              </Button>
              <Button className="botonCrearProyecto"  
                href={`/requisitosCreados/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}`}
                size="lg">
                Ir a requisitos
              </Button>
              <Button
                className="botonCrearProyecto"  
                href={`/temaReunion/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}`}
                size="lg">
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
                        placeholder="Buscar pregunta..."
                        ref={this.node}
                      />
                  </div>
                </div>
              </Col>
              </div>
              <Container fluid>
                <Table striped bordered hover className="tablaTermino" >
                  <thead>
                    <tr>
                      <th width="700">Pregunta</th>
                      <th width="400"></th>
                      <th width="900">Respuesta</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                    preguntasSeleccionadas.map((pregunta) => (
                      <tr key={pregunta.id_pregunta} >
                        <td> {pregunta.pregunta} </td>
                        <td>
                            {pregunta.respuesta == null ?
                              <Button className = "botones" size="sm"
                                variant="success"
                                onClick={() => this.IrResponderPregunta(pregunta.id_pregunta)}
                              >
                                Responder
                              </Button>
                              :
                              <Button className = "botones" size="sm"
                              variant="secondary" disabled
                              >
                              Responder
                              </Button>
                             }
                            {pregunta.respuesta == null ?
                            <Button className = "botones" size="sm"
                            variant="secondary" disabled
                            >
                              Editar
                            </Button>
                            :
                            <Button className = "botones" size="sm"
                            variant="success"
                            onClick={() => this.IrEditarRespuesta(pregunta.id_pregunta,pregunta.id_respuesta)}
                          >
                            Editar
                          </Button>
                            }
                            
                            <Button className = "botones" size="sm"
                              variant="success"
                              onClick={() => this.CrearRequisitoPregunta(pregunta.id_pregunta,pregunta.id_respuesta)}
                              >
                              Crear requisito
                            </Button>
                        </td>
                        <td> {pregunta.respuesta} </td>
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
export default PreguntasSeleccionadas ;