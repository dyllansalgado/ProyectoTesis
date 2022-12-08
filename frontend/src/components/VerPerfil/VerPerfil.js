import React, { Component } from "react";
import axios from "axios";
import "./VerPerfil.css";
import { Button, Container,Modal, Form, ModalHeader, ModalBody} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import swal from "sweetalert";



class VerPerfil extends Component {
    constructor(props) {
      super(props);
      this.state = {  usuario: [],
        id: null,
        rol: "",
        contrasena_usuario:"",
      };
      this.changeContrasena = this.changeContrasena.bind(this);
    }
    handleModal() {
      this.setState({ showModal: !this.state.showModal });
    };
  
    changeContrasena(event) {
      this.setState({ contrasena_usuario: event.target.value });
    }  
  
    componentDidMount() {
      if (localStorage.getItem("token") == null && localStorage.getItem("id_rol") === null ){
        window.location.replace("http://localhost:3000/");
      }
      const id = localStorage.getItem('usuario');
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
      });
    }
    CambiarDatos(id_usuario){
      if (this.state.contrasena_usuario !== "") {
      axios.put("http://localhost:8080/usuario/" + id_usuario ,{
        contrasena_usuario: this.state.contrasena_usuario
        });
        swal({
          title: "Contraseña modificada",
          text: "La contraseña ha sido reemplazada correctamente",
          icon: "success",
        });
        setTimeout(() => {
          window.location.replace("http://localhost:3000/verPerfil");
        }, 2000);
      }
      else {
        swal({
          title: "No ha ingresado contraseña",
          text: "Ingrese una contraseña para realizar su cambio",
          icon: "warning",
        });
      }
    }

    render() {
    const {usuario} = this.state;
    const {rol} = this.state;
    const contrasena_usuario = this.state.contrasena_usuario;
    return (
      <div>
        <div>
        <NavbarLogeado />
        </div>
        <div>
        <Container fluid>
          <div className="center">
            <h1 className="titulo">Perfil de {rol}: {usuario.nombre_usuario}</h1>
            </div>
          <div className="center">
            <form className="ProyectoInformacion">
              <div className="center">
                <h3 className="titulo">Datos de Usuario</h3>
                <div className="form-group2">
                  <label>
                    Nombre de usuario: {usuario.nombre_usuario}
                  </label>
                </div>
                <div className="form-group2">
                  <label>
                    Apellido de usuario: {usuario.apellido_usuario}
                  </label>
                </div>
                <div className="form-group2">
                  <label>
                    Correo usuario: {usuario.correo_usuario}
                  </label>
                </div>
                <div className="form-group2">
                  <label>
                    Contraseña: {usuario.contrasena_usuario}
                  </label>
                </div>
                <div className="form-group2">
                  <label>
                    Tipo de usuario: {this.state.rol}
                  </label>
                </div>
                <Button id="editarContraseña" className="botonVolverMain" onClick={() => this.handleModal()}>
                  {" "}
                  Editar contraseña
                </Button>
                <Modal
                name="formato"
                show={this.state.showModal}
                onHide={() => this.handleModal()}
                >
                <ModalHeader closeButton>
                  Editando contraseña de {rol} {usuario.nombre_usuario} 
                </ModalHeader>
                <ModalBody>
                  <Form>
                    <p> Contraseña actual: {usuario.contrasena_usuario} </p>
                    <input
                      id="contrasena"
                      type="text"
                      value= {contrasena_usuario}
                      placeholder="********"
                      className="form-control"
                      name="contrasena"
                      onChange={this.changeContrasena}
                    />
                    <Button
                      id="crearCambio"
                      name="botonCrearCambio"
                      onClick={() => this.CambiarDatos(usuario.id_usuario)}
                    >
                      {" "}
                      Cambiar información
                    </Button>
                  </Form>
                </ModalBody>
                </Modal>
                <Button className="botonVolverMain" href="/main">
                    Volver
                </Button>
              </div>
            </form>
          </div>
        </Container>
        </div>
      </div>
    );
  }
}

export default VerPerfil;