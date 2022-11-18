import React, { Component} from "react";
import {Container, Col, Row, Table,} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";
import {BsArrowReturnLeft,BsDownload} from "react-icons/bs";
import jsPDF from "jspdf";
import swal from "sweetalert";
import "../IngresarAProyecto/IngresarAProyecto.css";
import "../Tema/Tema.css";
import "../Main/NavbarLogeado.css";
import "../PreguntasSeleccionadas/PreguntasSeleccionadas.css";
import "jspdf-autotable";

class Requisitos extends Component { 
    constructor(props) {
      super(props);
      this.state = {
        usuario: [],
        id: null,
        proyecto:[],
        reunion:[],
        tema:[],
        requisitosCreados:[],
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
        .get("http://localhost:8080/RequisitosTodo/"+ idPath[4])
        .then((res) => {
          const requisitosCreados = res.data;
          this.setState({requisitosCreados});
          requisitosCreados.sort((a,b) => (a.prioridad.toLowerCase() < b.prioridad.toLowerCase() ? -1 : 1));
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
          const title = "Requisitos para tema:  " + this.state.tema.nombre_tema;
          const headers = [["Nombre Requisito ","Descripción Requisito", "Tipo Requerimiento", "Breve Descripción", "Pregunta", "Respuesta", "Prioridad"]];
          const data = this.state.requisitosCreados.map(elt=> [elt.nombre_requisito, elt.descripcion_requisito, elt.nombre_tipo_requisito,
          elt.descripcion_tipo_requisito, elt.pregunta, elt.respuesta, elt.prioridad]);
          let content = {
            startY: 50,
            head: headers,
            body: data,
          };
          doc.text(title, marginLeft, 40);
          doc.autoTable(content);
          doc.save("Requisitos_Tema_"+this.state.tema.nombre_tema+".pdf")
        }
      });
    };

    render() {
      const {proyecto} = this.state;
      const {tema} = this.state;
      const {requisitosCreados} = this.state;
      const {reunion}= this.state;
      return ( 
      <div>
          <div>
            <NavbarLogeado />
          </div>
          <div>
            <Container fluid className="container-fluid2">
              <Row>
                <h3 className="titulo"> Requisitos de Preguntas Aceptadas: {tema.nombre_tema}</h3>
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
                ccccc
              </Button>
              <Button
                className="botonCrearProyecto"  
                href={`/preguntasSeleccionadas/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}`}
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
                      <th width="700">Nombre Requisito</th>
                      <th width="900">Descripción Requisito</th>
                      <th width="900">Tipo de requisito</th>
                      <th width="900">Pregunta</th>
                      <th width="900">Respuesta</th>
                      <th width="300">Creador</th>
                      <th width="100">Prioridad</th>
                      <th width="900">Acciones</th>

                    </tr>
                  </thead>
                  <tbody>
                    {
                    requisitosCreados.map((requisitos) => (
                      <tr key={requisitos.id_requisito_todo} >
                        <td> {requisitos.nombre_requisito} </td>
                        <td> {requisitos.descripcion_requisito} </td>
                        <td> {requisitos.nombre_tipo_requisito} </td>
                        <td> {requisitos.pregunta} </td>
                        <td> {requisitos.respuesta} </td>
                        <td> {requisitos.creador_requisito}</td>
                        <td> {requisitos.prioridad} </td>
                        <td></td>
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
export default Requisitos ;