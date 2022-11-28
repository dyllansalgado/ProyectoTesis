import React, { Component } from "react";
import axios from "axios";
import { Col, Button, Row, Container} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import swal from "sweetalert";
import Modal from 'react-bootstrap/Modal';
import "../Respuesta/Respuesta.css"
import "bootstrap/dist/css/bootstrap.min.css";


class EditarGlosario extends Component { 
  constructor(props) {
    super(props);
    this.state = {
        usuario:[],
        proyecto:[],
        glosario:[],
        nombre_glosario:"",
        reunion:[],
        descripcion_glosario:"",
    };
    this.changeNombreGlosario = this.changeNombreGlosario.bind(this);
    this.changeDescripcionGlosario = this.changeDescripcionGlosario.bind(this);
    }

    changeNombreGlosario(event) {
        this.setState({ nombre_glosario: event.target.value });
    }

    changeDescripcionGlosario(event) {
        this.setState({ descripcion_glosario: event.target.value });
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
            })
        ]);
    }
    CambiarDatosGlosario(){
        let idPath = window.location.pathname.split("/");
        if(this.state.nombre_glosario !== "" && this.state.descripcion_glosario !== "") {
        axios.put("http://localhost:8080/glosario/" + idPath[4] ,{
          nombre_glosario: this.state.nombre_glosario,
          descripcion_glosario: this.state.descripcion_glosario,
          });
          swal({
            title: "Información de glosario modificado con éxito",
            text: "El glosario ha sido modificado correctamente",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/GlosarioReunion/"+ idPath[2] + "/" + idPath[3]);
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
    const nombre_glosario = this.state.nombre_glosario;
    const descripcion_glosario = this.state.descripcion_glosario;
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
                <Row className="justify-content-md-center">
                  <Col>
                    <p>Nombre Glosario* : {glosario.nombre_glosario}</p>
                    <input
                        type="text"
                        value={nombre_glosario}
                        placeholder="Ingrese un nombre de glosario nuevo"
                        className="form-control"
                        name="NombreGlosario"
                        onChange={this.changeNombreGlosario}
                        required
                    />
                    <p>Descripción* : {glosario.descripcion_glosario} </p>
                    <input
                        type="text"
                        value={descripcion_glosario}
                        placeholder="Ingrese una descripción de glosario nuevo"
                        className="form-control"
                        name="DescripcionGlosario"
                        onChange={this.changeDescripcionGlosario}
                        required
                    />
                  </Col>
                </Row>
                <p>(*) Campos obligatorios</p>
                <Modal.Footer>
                  <Button id="volver" className="Botones"
                    variant="primary"
                    href={`/GlosarioReunion/${proyecto.id_proyecto}/${reunion.id_reunion}`}
                  >
                    Volver
                  </Button>
                  <Button id="editarGlosario" className="Botones"
                    variant="success"
                    onClick={() => this.CambiarDatosGlosario()}
                  >
                    Editar Glosario
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


export default EditarGlosario;