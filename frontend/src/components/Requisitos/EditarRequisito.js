import React, { Component } from "react";
import axios from "axios";
import { Col, Button, Row, Container} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import swal from "sweetalert";
import Modal from 'react-bootstrap/Modal';
import "../Respuesta/Respuesta.css"
import "bootstrap/dist/css/bootstrap.min.css";

class EditarRequisito extends Component { 
  constructor(props) {
    super(props);
    this.state = {
        usuario:[],
        reunion:[],
        tema:[],
        proyecto:[],
        requisito:[],
        nombre_requisito:"",
        descripcion_requisito:"",
        prioridad:"",
    };
    this.changeNombreRequisito = this.changeNombreRequisito.bind(this);
    this.changeDescripcionRequisito = this.changeDescripcionRequisito.bind(this);
    this.changePrioridad = this.changePrioridad.bind(this);
    }

    changeNombreRequisito(event) {
        this.setState({ nombre_requisito: event.target.value });
    }
    changeDescripcionRequisito(event) {
        this.setState({ descripcion_requisito: event.target.value });
    }
    changePrioridad(event) {
        this.setState({ prioridad: event.target.value });
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
            .get("http://localhost:8080/reunion/"+ idPath[3])
            .then((res) => {
                const reunion = res.data;
                this.setState({reunion});
            })
            .catch((error) => {
                console.log(error);
            }),
            axios
            .get("http://localhost:8080/requisito/"+ idPath[5])
            .then((res) => {
                const requisito = res.data;
                this.setState({requisito});
            })
            .catch((error) => {
                console.log(error);
            }),
        ]);
    }
    CambiarDatos(){
        let idPath = window.location.pathname.split("/");
        if(this.state.nombre_requisito !== "" && this.state.prioridad !== "" && this.state.descripcion_requisito!== "" && this.state.prioridad > 0 && this.state.prioridad <= 5) {
        axios.put("http://localhost:8080/requisito/" + idPath[5] ,{
          nombre_requisito: this.state.nombre_requisito,
          prioridad: this.state.prioridad,
          descripcion_requisito: this.state.descripcion_requisito
          });
          swal({
            title: "Información de requisito modificado con éxito",
            text: "El requisito ha sido modificado correctamente",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/requisitosCreados/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
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
    const {tema} = this.state;
    const {reunion}= this.state;
    const {usuario} = this.state;
    const {requisito} = this.state;
    const nombre_requisito= this.state.nombre_requisito;
    const descripcion_requisito = this.state.descripcion_requisito;
    const prioridad = this.state.prioridad;

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
              <Modal.Title>Editando Requisito Seleccionado</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <form>
                <Row className="justify-content-md-center">
                  <Col>
                    <p>
                      Tema: {tema.nombre_tema}{" "}
                    </p>
                    <p>Nombre requisito* : {requisito.nombre_requisito}</p>
                    <input
                      type="text"
                      value={nombre_requisito}
                      placeholder="Ingrese un nombre de requisito nuevo"
                      className="form-control"
                      name="Nombre Requisito"
                      onChange={this.changeNombreRequisito}
                      required
                    />
                    <p>Descripción requisito* : {requisito.descripcion_requisito} </p>
                    <input
                      type="text"
                      value={descripcion_requisito}
                      placeholder="Ingrese una descripción nueva"
                      className="form-control"
                      name="descripcion"
                      onChange={this.changeDescripcionRequisito}
                      required
                    />
                    <p>Prioridad* : {requisito.prioridad} </p>
                    <input
                        type="number"
                        min="1"
                        max="5"
                        placeholder="1:Alta Prioridad / 5:Baja Prioridad"
                        className="form-control"
                        value={prioridad}
                        name="Respuesta"
                        onChange={this.changePrioridad}
                        required
                    />
                  </Col>
                </Row>
                <p>(*) Campos obligatorios</p>
                <Modal.Footer>
                  <Button className="Botones"
                    variant="primary"
                    id="volver"
                    href={`/requisitosCreados/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}`}
                  >
                    Volver
                  </Button>
                  <Button className="Botones"
                    variant="success"
                    id="editarRequisitoBoton"
                    onClick={() => this.CambiarDatos()}
                  >
                    Editar Requisito
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


export default EditarRequisito;