import React, { Component } from "react";
import axios from "axios";
import { Col, Button, Row, Container} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import swal from "sweetalert";
import Modal from 'react-bootstrap/Modal';
import "./Respuesta.css"
import "bootstrap/dist/css/bootstrap.min.css";

class EditarRespuesta extends Component { 
  constructor(props) {
    super(props);
    this.state = {
        pregunta: [],
        respuestaCreada: "",
        usuario:[],
        reunion:[],
        tema:[],
        proyecto:[],
        respuesta:"",
        respuestas:[]
    };
    this.changeRespuesta = this.changeRespuesta.bind(this);
  }

  changeRespuesta(event) {
    this.setState({ respuesta: event.target.value });
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
      .get("http://localhost:8080/reunion/"+ idPath[3])
      .then((res) => {
        const reunion = res.data;
        this.setState({reunion});
      })
      .catch((error) => {
        console.log(error);
      }),
      axios
      .get("http://localhost:8080/pregunta/"+ idPath[5])
      .then((res) => {
        const pregunta = res.data;
        this.setState({pregunta});
      })
      .catch((error) => {
        console.log(error);
      }),
      axios
      .get("http://localhost:8080/respuesta/"+ idPath[6])
      .then((res) => {
        const respuestas = res.data;
        this.setState({respuestas});
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

  EditarRespuesta() {
    let idPath = window.location.pathname.split("/");
    if (this.state.respuesta !== "")
    {
      axios.put("http://localhost:8080/respuesta/" + idPath[6], {
        respuesta: this.state.respuesta
      });
      swal({
        title: "Respuesta actualizada con éxito",
        text: "La respuesta ha sido actualizada con éxito",
        icon: "success",
      });
      setTimeout(() => {
        window.location.replace("http://localhost:3000/preguntasSeleccionadas/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
      }, 2000);
    }else {
      swal({
        title: "Atención",
        text: "Error al editar la respuesta",
        icon: "warning",
        button: "Aceptar",
        timer: "2000",
      }); 
    }
  }

  render() { 
    const {proyecto} = this.state;
    const {tema} = this.state;
    const {reunion}= this.state;
    const {pregunta} = this.state;
    const respuesta= this.state.respuesta;
    const {usuario} = this.state;
    const {respuestas} = this.state;

    return(
     <div>
        <div>
          <NavbarLogeado />
        </div>
        <div className="fondoPreguntaRespuesta">
        <Container fluid className="ContenedorRespuesta">
          <Modal.Dialog>
            <Modal.Header>
              <Modal.Title> Bienvenido: {usuario.nombre_usuario}</Modal.Title>
            </Modal.Header>
            <Modal.Header>
              <Modal.Title>Editando Respuesta Seleccionada</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <form>
                <Row className="justify-content-md-center">
                  <Col>
                    <p>
                      Tema: {tema.nombre_tema}{" "}
                    </p>
                    <p>Pregunta: {pregunta.pregunta}</p>
                    <p>Respuesta: {respuestas.respuesta}</p>
                    <input
                      type="text"
                      placeholder="Ingrese una respuesta"
                      className="form-control"
                      value={respuesta}
                      name="Respuesta"
                      onChange={this.changeRespuesta}
                    />
                  </Col>
                </Row>
                <Modal.Footer>
                  <Button className="Botones"
                    variant="primary"
                    href={`/preguntasSeleccionadas/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}`}
                  >
                    Volver
                  </Button>
                  <Button className="Botones"
                    variant="success"
                    onClick={() => this.EditarRespuesta()}
                  >
                    Editar Respuesta
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


export default EditarRespuesta;