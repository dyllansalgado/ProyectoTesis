import React, { Component } from "react";
import axios from "axios";
import { Col, Button, Row, Container} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import swal from "sweetalert";
import DatePicker, { registerLocale } from 'react-datepicker';
import Modal from 'react-bootstrap/Modal';
import "../Respuesta/Respuesta.css"
import "bootstrap/dist/css/bootstrap.min.css";
import es from "date-fns/locale/es"
registerLocale("es",es);


class EditarProyecto extends Component { 
  constructor(props) {
    super(props);
    this.state = {
        usuario:[],
        proyecto:[],
        nombre_proyecto:"",
        objetivo_proyecto:"",
        fecha_inicio_proyecto:"",
        fecha: new Date("2018", "06", "22"),
    };
    this.changeNombreProyecto = this.changeNombreProyecto.bind(this);
    this.changeObjetivoProyecto = this.changeObjetivoProyecto.bind(this);
    }

    changeNombreProyecto(event) {
        this.setState({ nombre_proyecto: event.target.value });
    }
    changeObjetivoProyecto(event) {
        this.setState({ objetivo_proyecto: event.target.value });
    }
    onChange=fecha=>{
        this.setState({fecha_inicio_proyecto: fecha});
    }
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
    CambiarDatos(){
        let idPath = window.location.pathname.split("/");
        if(this.state.nombre_proyecto !== "" && this.state.objetivo_proyecto !== ""  && this.state.fecha_inicio_proyecto !== "") {
        axios.put("http://localhost:8080/proyecto/" + idPath[2] ,{
          nombre_proyecto: this.state.nombre_proyecto,
          objetivo_proyecto: this.state.objetivo_proyecto,
          fecha_inicio_proyecto: this.state.fecha_inicio_proyecto,
          });
          swal({
            title: "Información de proyecto modificado con éxito",
            text: "El proyecto ha sido modificado correctamente",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/misProyectos");
          }, 2000);
        }
        else {
          swal({
            title: "Falta información o esta erronea",
            text: "Rellene o revise los campos para realizar el cambio de información",
            icon: "warning",
          });
        }
    }

    render() { 
    const {proyecto} = this.state;
    const {usuario} = this.state;
    const nombre_proyecto = this.state.nombre_proyecto;
    const objetivo_proyecto = this.state.objetivo_proyecto;
    return(
     <div>
        <div>
          <NavbarLogeado />
        </div>
        <div>
        <Container fluid className="ContenedorRespuesta">
          <Modal.Dialog>
            <Modal.Header>
              <Modal.Title> Bienvenido: {usuario.nombre_usuario}</Modal.Title>
            </Modal.Header>
            <Modal.Header>
              <Modal.Title>Editando Proyecto Seleccionado</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <form>
                <Row className="justify-content-md-center">
                  <Col>
                    <p>Nombre Proyecto* : {proyecto.nombre_proyecto}</p>
                    <input
                        type="text"
                        value={nombre_proyecto}
                        placeholder="Ingrese un nombre de proyecto nuevo"
                        className="form-control"
                        name="NombreProeycto"
                        onChange={this.changeNombreProyecto}
                        required
                    />
                    <p>Objetivo Proyecto* : {proyecto.objetivo_proyecto} </p>
                    <input
                        type="text"
                        value={objetivo_proyecto}
                        placeholder="Ingrese un objetivo nuevo"
                        className="form-control"
                        name="Objetivo"
                        onChange={this.changeObjetivoProyecto}
                        required
                    />
                    <p>Fecha Proyecto* : {proyecto.fecha_inicio_proyecto} </p>
                    <div className="contenedorDate">
                        <div className="centerDate">
                        <DatePicker selected={this.state.fecha_inicio_proyecto} onChange={this.onChange} 
                        locale = "es"
                        className="pickers" 
                        dateFormat="dd-MM-yyyy"
                        required
                        />
                        </div>
                    </div>
                  </Col>
                </Row>
                <p>(*) Campos obligatorios</p>
                <Modal.Footer>
                  <Button id="volver" className="Botones"
                    variant="primary"
                    href="/misProyectos/"
                  >
                    Volver
                  </Button>
                  <Button id= "editarProyecto" className="Botones"
                    variant="success"
                    onClick={() => this.CambiarDatos()}
                  >
                    Editar Proyecto
                  </Button>
                </Modal.Footer>
              </form>
            </Modal.Body>
          </Modal.Dialog>
        </Container>
        </div>
      </div>
    );
  }
}


export default EditarProyecto;