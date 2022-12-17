import React, { Component } from "react";
import axios from "axios";
import { Col, Button, Row, Container} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import swal from "sweetalert";
import Modal from 'react-bootstrap/Modal';
import "../Respuesta/Respuesta.css"
import "bootstrap/dist/css/bootstrap.min.css";

class EditarComentario extends Component { 
  constructor(props) {
    super(props);
    this.state = {
        usuario:[],
        reunion:[],
        tema:[],
        proyecto:[],
        comentarios:[],
        pregunta:[],
        comentario:"",
    };
    this.changeComentario = this.changeComentario.bind(this);
    }

    changeComentario(event) {
        this.setState({ comentario: event.target.value });
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
            .get("http://localhost:8080/pregunta/"+ idPath[5])
            .then((res) => {
                const pregunta = res.data;
                this.setState({pregunta});
            })
            .catch((error) => {
                console.log(error);
            }),
            axios
            .get("http://localhost:8080/comentario/"+ idPath[6])
            .then((res) => {
                const comentarios = res.data;
                this.setState({comentarios});
            })
            .catch((error) => {
                console.log(error);
            }),
        ]);
    }
    CambiarDatos(){
        let idPath = window.location.pathname.split("/");
        if(this.state.comentario !== "") {
        axios.put("http://localhost:8080/comentario/" + idPath[6] ,{
          comentario: this.state.comentario,
          });
          swal({
            title: "Información de comentario modificado con éxito",
            text: "El comentario ha sido modificado correctamente",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/comentarPregunta/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4] + "/" + idPath[5] );
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
    const {pregunta} = this.state;
    const {comentarios} = this.state;
    const comentario= this.state.comentario;

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
              <Modal.Title>Editando Comentario Seleccionado</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <form>
                <Row className="justify-content-md-center">
                  <Col>
                    <p>
                      Pregunta: {pregunta.pregunta}{" "}
                    </p>
                    <p>Comentario* : {comentarios.comentario}</p>
                    <input
                      type="text"
                      value={comentario}
                      placeholder="Ingrese un comentario"
                      className="form-control"
                      name="Comentario"
                      onChange={this.changeComentario}
                      required
                    />
                  </Col>
                </Row>
                <p>(*) Campos obligatorios</p>
                <Modal.Footer>
                  <Button className="Botones"
                    variant="primary"
                    id="volver"
                    href={`/comentarPregunta/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}/${pregunta.id_pregunta}`}
                  >
                    Volver
                  </Button>
                  <Button className="Botones"
                    variant="success"
                    id="editarComentarioBoton"
                    onClick={() => this.CambiarDatos()}
                  >
                    Editar Comentario
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


export default EditarComentario;