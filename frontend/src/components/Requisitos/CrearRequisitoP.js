import React, { Component } from "react";
import axios from "axios";
import { Col, Button, Row, Container,Form,} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import swal from "sweetalert";
import Modal from 'react-bootstrap/Modal';
import "../Respuesta/Respuesta.css"
import "bootstrap/dist/css/bootstrap.min.css";

class CrearRequisitoP extends Component { 
    constructor(props) {
        super(props);
        this.state = {
            pregunta: [],
            respuestaCreada: "",
            usuario:[],
            reunion:[],
            tema:[],
            proyecto:[],
            estado: false,
            tipos_requisitos:[],
            id_tipo_requisito:1,
            nombre_requisito: "",
            descripcion_requisito: "",
            prioridad: "",
        };
        this.changeNombreR = this.changeNombreR.bind(this);
        this.changeDescripcionR = this.changeDescripcionR.bind(this);
        this.changePrioridadR = this.changePrioridadR.bind(this);

    }

    changeHandler = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }; 

    changeNombreR(event) {
        this.setState({nombre_requisito: event.target.value });
    }
    changeDescripcionR(event) {
        this.setState({descripcion_requisito: event.target.value });
    }
    changePrioridadR(event) {
        this.setState({prioridad: event.target.value });
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
      .get(
          "http://localhost:8080/usuario/"+id)
        .then((res) => {
          const usuario = res.data;
          this.setState({usuario});
        }),
        axios
            .get("http://localhost:8080/tiposRequisitos/")
            .then((res) => {
              const tipos_requisitos = res.data;
              this.setState({tipos_requisitos});
            })
            .catch((error) => {
              console.log(error);
        }),
    ]);
  }  


  CrearRequisito = (e) => {
    let idPath = window.location.pathname.split("/");
    if (this.state.nombre_requisito !== "" && this.state.nombre_descripcion_requisito !== "" && this.state.prioridad > 0 && this.state.prioridad <= 5)
    {
      axios.post("http://localhost:8080/requisito/create/"+ idPath[5] + "/" + localStorage.getItem('usuario'), {
        nombre_requisito: this.state.nombre_requisito,
        descripcion_requisito: this.state.descripcion_requisito,
        estado_requisito: this.state.estado,
        prioridad: this.state.prioridad,
        id_tipo_requisito: this.state.id_tipo_requisito,

      });
      swal({
        title: "Se ha creado con éxito el requisito",
        text: "El requisito ha sido creado con éxito",
        icon: "success",
      });
      setTimeout(() => {
        window.location.replace("http://localhost:3000/preguntasSeleccionadas/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
      }, 2000);
    }else {
      swal({
        title: "Atención",
        text: "El requisito debe tener todos los campos rellenados correctamente",
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
    const {usuario} = this.state;
    const {tipos_requisitos} = this.state;

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
              <Modal.Title className="text-center">Creando un requisito para Pregunta o Respuesta</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <form>
                <Row className="justify-content-md-center">
                  <Col>
                    <p>
                        Tema: {tema.nombre_tema}{" "}
                    </p>
                    <p>Pregunta: {pregunta.pregunta}</p>
                    <p>Nombre Requisito* : </p>
                    <input
                        type="text"
                        placeholder="Ingrese un nombre de requisito"
                        className="form-control"
                        value={this.state.nombre_requisito}
                        name="Respuesta"
                        onChange={this.changeNombreR}
                        required
                    />
                    <p>Descripción requisito* : </p>
                    <input
                        type="text"
                        placeholder="Ingrese un breve descripción"
                        className="form-control"
                        value={this.state.descripcion_requisito}
                        name="Respuesta"
                        onChange={this.changeDescripcionR}
                        required
                    />
                    <p>Prioridad* : ( 1:Alta Prioridad / 5:Baja Prioridad )</p>
                    <input
                        type="number"
                        min="1"
                        max="5"
                        placeholder="Ingrese un valor para prioridad (1 a 5)"
                        className="form-control"
                        value={this.state.prioridad}
                        name="Respuesta"
                        onChange={this.changePrioridadR}
                        required
                    />
                    <p>Tipo de requisito* :</p>
                        <Form.Select
                          aria-label="Default select example"
                          name="id_tipo_requisito"
                          onChange={this.changeHandler}
                        >
                          {tipos_requisitos.map((tiposRequisitos) => (
                            <option key={tiposRequisitos.id_tipo_requisito} type="number" value={tiposRequisitos.id_tipo_requisito} required>
                              {tiposRequisitos.nombre_tipo_requisito}
                            </option>
                          ))}
                        </Form.Select>
                  </Col>
                </Row>
                <p>(*) Campos obligatorios</p>
                <Modal.Footer>
                  <Button className="Botones"
                    variant="primary"
                    href={`/preguntasSeleccionadas/${proyecto.id_proyecto}/${reunion.id_reunion}/${tema.id_tema}`}
                  >
                    Volver
                  </Button>
                  <Button className="Botones"
                    variant="success"
                    onClick={() => this.CrearRequisito()}
                  >
                    Crear Requisito
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


export default CrearRequisitoP;