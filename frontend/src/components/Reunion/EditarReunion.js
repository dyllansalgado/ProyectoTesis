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


class EditarReunion extends Component { 
  constructor(props) {
    super(props);
    this.state = {
        usuario:[],
        proyecto:[],
        reunion:[],
        lugar_reunion:"",
        fecha_reunion:"",
        fecha: new Date("2018", "06", "22"),
    };
    this.changeLugar = this.changeLugar.bind(this);
    }

    changeLugar(event) {
        this.setState({ lugar_reunion: event.target.value });
    }

    onChange=fecha=>{
        this.setState({fecha_reunion: fecha});
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
    CambiarDatos(){
        let idPath = window.location.pathname.split("/");
        if(this.state.lugar_reunion !== "" && this.state.fecha_reunion !== "") {
        axios.put("http://localhost:8080/reunion/" + idPath[3] ,{
          lugar_reunion: this.state.lugar_reunion,
          fecha_reunion: this.state.fecha_reunion,
          });
          swal({
            title: "Información de reunión modificada con éxito",
            text: "La reunión ha sido modificada correctamente",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/ingresarAProyecto/" + idPath[2]);
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
    const lugar_reunion = this.state.lugar_reunion;
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
              <Modal.Title>Editando Reunión Seleccionada</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <form>
                <Row className="justify-content-md-center">
                  <Col>
                    <p>Lugar Reunion* : {reunion.lugar_reunion}</p>
                    <input
                        type="text"
                        value={lugar_reunion}
                        placeholder="Ingrese un nombre de lugar de reunion nuevo"
                        className="form-control"
                        name="NombreReunion"
                        onChange={this.changeLugar}
                        required
                    />
                    <p>Fecha Reunion* : {reunion.fecha_reunion} </p>
                    <div className="contenedorDate">
                        <div className="centerDate">
                        <DatePicker selected={this.state.fecha_reunion} onChange={this.onChange} 
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
                    href={`/ingresarAProyecto/${proyecto.id_proyecto}`}
                  >
                    Volver
                  </Button>
                  <Button id="editarReunion" className="Botones"
                    variant="success"
                    onClick={() => this.CambiarDatos()}
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


export default EditarReunion;