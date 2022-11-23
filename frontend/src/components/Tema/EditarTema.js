import React, { Component } from "react";
import axios from "axios";
import { Col, Button, Row, Container} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import swal from "sweetalert";
import Modal from 'react-bootstrap/Modal';
import "../Respuesta/Respuesta.css"
import "bootstrap/dist/css/bootstrap.min.css";


class EditarTema extends Component { 
  constructor(props) {
    super(props);
    this.state = {
        usuario:[],
        proyecto:[],
        tema:[],
        nombre_tema:"",
        reunion:[],
        descripcion_tema:"",
    };
    this.changeNombreTema = this.changeNombreTema.bind(this);
    this.changeDescripcionTema = this.changeDescripcionTema.bind(this);
    }

    changeNombreTema(event) {
        this.setState({ nombre_tema: event.target.value });
    }

    changeDescripcionTema(event) {
        this.setState({ descripcion_tema: event.target.value });
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
            .get("http://localhost:8080/tema/"+ idPath[4])
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
        ]);
    }
    CambiarDatosTema(){
        let idPath = window.location.pathname.split("/");
        if(this.state.nombre_tema !== "" && this.state.descripcion_tema !== "") {
        axios.put("http://localhost:8080/tema/" + idPath[4] ,{
          nombre_tema: this.state.nombre_tema,
          descripcion_tema: this.state.descripcion_tema,
          });
          swal({
            title: "Información de tema modificada con éxito",
            text: "El tema ha sido modificado correctamente",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/ingresarReunion/"+ idPath[2] + "/" + idPath[3]);
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
    const {tema} = this.state;
    const {reunion} = this.state;
    const nombre_tema = this.state.nombre_tema;
    const descripcion_tema = this.state.descripcion_tema;
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
              <Modal.Title>Editando Tema Seleccionado</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <form>
                <p>Lugar de Reunión : {reunion.lugar_reunion}</p>
                <Row className="justify-content-md-center">
                  <Col>
                    <p>Nombre Tema* : {tema.nombre_tema}</p>
                    <input
                        type="text"
                        value={nombre_tema}
                        placeholder="Ingrese un nombre de tema nuevo"
                        className="form-control"
                        name="NombreTema"
                        onChange={this.changeNombreTema}
                        required
                    />
                    <p>Descripción* : {tema.descripcion_tema} </p>
                    <input
                        type="text"
                        value={descripcion_tema}
                        placeholder="Ingrese una descripción de tema nuevo"
                        className="form-control"
                        name="DescripcionTema"
                        onChange={this.changeDescripcionTema}
                        required
                    />
                  </Col>
                </Row>
                <p>(*) Campos obligatorios</p>
                <Modal.Footer>
                  <Button className="Botones"
                    variant="primary"
                    href={`/ingresarReunion/${proyecto.id_proyecto}/${reunion.id_reunion}`}
                  >
                    Volver
                  </Button>
                  <Button className="Botones"
                    variant="success"
                    onClick={() => this.CambiarDatosTema()}
                  >
                    Editar Reunión
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


export default EditarTema;