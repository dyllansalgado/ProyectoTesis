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

class RequisitosAceptados extends Component { 
    constructor(props) {
      super(props);
      this.state = {
        usuario: [],
        id: null,
        proyecto:[],
        reunion:[],
        tema:[],
        requisitosAceptados:[],
        idPregunta: null,
        respuestaCreada:"",
        requisitosFiltro:[],
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
        .get("http://localhost:8080/RequisitosTodoAceptados/"+ idPath[4])
        .then((res) => {
          const requisitosAceptados = res.data;
          this.setState({requisitosAceptados});
          requisitosAceptados.sort((a,b) => a.prioridad - b.prioridad)
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
        requisitosFiltro: [],
      });
    };
    onUserChange = async (e) => {
      let idPath = window.location.pathname.split("/");
      await axios
      .get("http://localhost:8080/RequisitosTodoAceptados/"+ idPath[4])
      .then((res) => {
        this.setState({
            requisitosFiltro: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
      });
      let filter = e.target.value.toLowerCase();
      let filtroRequisitos = this.state.requisitosFiltro.filter((e) => {
        let dataFilter = e.nombre_requisito.toLowerCase();
        let dataTipoRequisito = e.nombre_tipo_requisito.toLowerCase();
        return (
            dataFilter
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1 ||
            dataTipoRequisito 
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1 
        );
      });
      this.setState({
        requisitosAceptados: filtroRequisitos,
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
          const data = this.state.requisitosAceptados.map(elt=> [elt.nombre_requisito, elt.descripcion_requisito, elt.nombre_tipo_requisito,
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

    AceptarRequisito(id_requisito){
      let idPath = window.location.pathname.split("/");
      swal({
        title: "Atención",
        text: "¿Desea aceptar el requisito seleccionado?",
        icon: "warning",
        buttons: ["No", "Si"],
      }).then((respuesta) => {
        if (respuesta) {
          axios.put("http://localhost:8080/requisito/" + id_requisito, {
            estado_requisito: true,
          });
          swal({
            title: "Requisito aceptado con éxito",
            text: "El requisito ha sido aceptado con éxito",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/requisitosCreados/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
          }, 2000);
        }
      });
    }
    render() {
      const {proyecto} = this.state;
      const {tema} = this.state;
      const {requisitosAceptados} = this.state;
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
              <Button
                className="botonCrearProyecto"  
                href={`/requisitosCreados/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}`}
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
                      <th width="700">Tipo de requisito</th>
                      <th width="700">Pregunta</th>
                      <th width="900">Respuesta</th>
                      <th width="150">Creador</th>
                      <th width="50">Prioridad</th>
                    </tr>
                  </thead>
                  <tbody >
                    {
                    requisitosAceptados.map((requisitos) => (
                      <tr key={requisitos.id_requisito}>
                        <td> {requisitos.nombre_requisito}</td>
                        <td> {requisitos.descripcion_requisito} </td>
                        <td> {requisitos.nombre_tipo_requisito} </td>
                        <td> {requisitos.pregunta} </td>
                        <td> {requisitos.respuesta} </td>
                        <td> {requisitos.creador_requisito}</td>
                        <td> {requisitos.prioridad}</td>
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
export default RequisitosAceptados ;