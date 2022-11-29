import React, { Component } from "react";
import axios from "axios";
import { Col, Button, Row, Container} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import swal from "sweetalert";
import Modal from 'react-bootstrap/Modal';
import "../Respuesta/Respuesta.css"
import "bootstrap/dist/css/bootstrap.min.css";


class EditarTermino extends Component { 
  constructor(props) {
    super(props);
    this.state = {
        usuario:[],
        proyecto:[],
        terminos:[],
        glosario:[],
        nombre_termino:"",
        reunion:[],
        descripcion_termino:"",
    };
    this.changeNombreTermino = this.changeNombreTermino.bind(this);
    this.changeDescripcionTermino = this.changeDescripcionTermino.bind(this);
    }

    changeNombreTermino(event) {
        this.setState({ nombre_termino: event.target.value });
    }

    changeDescripcionTermino(event) {
        this.setState({ descripcion_termino: event.target.value });
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
                this.setState({reunion});
            })
            .catch((error) => {
                console.log(error);
            }),
            axios
            .get("http://localhost:8080/glosario/"+ idPath[4])
            .then((res) => {
                const glosario = res.data;
                this.setState({glosario});
            }),
            axios
            .get("http://localhost:8080/termino/"+ idPath[5])
            .then((res) => {
                const terminos = res.data;
                this.setState({terminos});
            })
        ]);
    }
    CambiarDatosTermino(){
        let idPath = window.location.pathname.split("/");
        if(this.state.nombre_termino !== "" && this.state.descripcion_termino !== "") {
        axios.put("http://localhost:8080/termino/" + idPath[5] ,{
          nombre_termino: this.state.nombre_termino,
          descripcion_termino: this.state.descripcion_termino,
          });
          swal({
            title: "Información de término modificado con éxito",
            text: "El término ha sido modificado correctamente",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/ingresarAGlosario/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
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
    const {reunion} = this.state;
    const {glosario} = this.state;
    const {terminos} = this.state;
    const nombre_termino = this.state.nombre_termino;
    const descripcion_termino = this.state.descripcion_termino;
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
              <Modal.Title>Editando Glosario Seleccionado</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <form>
                <p>Lugar de Reunión : {reunion.lugar_reunion}</p>
                <p>Nombre Glosario : {glosario.nombre_glosario}</p>
                <Row className="justify-content-md-center">
                  <Col>
                    <p>Nombre Término* : {terminos.nombre_termino}</p>
                    <input
                        type="text"
                        value={nombre_termino}
                        placeholder="Ingrese un nombre de término nuevo"
                        className="form-control"
                        name="NombreTermino"
                        onChange={this.changeNombreTermino}
                        required
                    />
                    <p>Descripción* : {terminos.descripcion_termino} </p>
                    <input
                        type="text"
                        value={descripcion_termino}
                        placeholder="Ingrese una descripción de término nuevo"
                        className="form-control"
                        name="DescripcionTermino"
                        onChange={this.changeDescripcionTermino}
                        required
                    />
                  </Col>
                </Row>
                <p>(*) Campos obligatorios</p>
                <Modal.Footer>
                  <Button id="volver" className="Botones"
                    variant="primary"
                    href={`/ingresarAGlosario/${proyecto.id_proyecto}/${reunion.id_reunion}/${glosario.id_glosario}`}
                  >
                    Volver
                  </Button>
                  <Button id="editarTermino" className="Botones"
                    variant="success"
                    onClick={() => this.CambiarDatosTermino()}
                  >
                    Editar Término
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


export default EditarTermino;