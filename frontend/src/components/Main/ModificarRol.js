import React, { Component } from "react";
import axios from "axios";
import { Col, Button, Row, Form, Container} from "react-bootstrap";
import NavbarLogeado from "./NavbarLogeado.js";
import swal from "sweetalert";
import Modal from 'react-bootstrap/Modal';
import "bootstrap/dist/css/bootstrap.min.css";

class ModificarRol extends Component { 
  constructor(props) {
    super(props);
    this.state = {
        usuario:[],
        usuarioModificar:[],
        id_rol:1,
        roles:[],
    };
    this.changeRol = this.changeRol.bind(this);
    }

    changeRol(event) {
        this.setState({ id_rol: event.target.value });
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
            })
            .catch((error) => {
                console.log(error);
            }),
            axios
            .get(
              "http://localhost:8080/usuario/"+ idPath[2])
            .then((res) => {
                const usuarioModificar = res.data;
                this.setState({usuarioModificar});
            })
            .catch((error) => {
                console.log(error);
            }),
            axios
            .get(
              "http://localhost:8080/roles/")
            .then((res) => {
                const roles = res.data;
                this.setState({roles});
            })
            .catch((error) => {
                console.log(error);
            }),
        ]);
    }

    CambiarRol(){
        let idPath = window.location.pathname.split("/");
        swal({
          title: "Atención al seleccionar Sí cambiará el rol del usuario seleccionado",
          text: "¿Desea cambiar el rol del usuario seleccionado?",
          icon: "warning",
          buttons: ["No", "Si"],
        }).then((respuesta) => {
          if (respuesta) {
            if(this.state.id_rol !== "") {
            axios.put("http://localhost:8080/usuario/" + idPath[2] ,{
              id_rol: this.state.id_rol,
              });
              swal({
                title: "Información de rol modificado con exito",
                text: "El rol ha sido modificado correctamente",
                icon: "success",
              });
              setTimeout(() => {
                window.location.replace("http://localhost:3000/usuarios/");
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
        });
    }
    //Usuario es el administrador, user corresponde al usuario que le cambian el rol
    render() {
    const {usuario} = this.state;
    const {usuarioModificar} = this.state;
    const roles = this.state.roles;
    return(
     <div>
        <div>
          <NavbarLogeado />
        </div>
        <div>
        <Container fluid className="ContenedorRespuesta">
          <Modal.Dialog>
            <Modal.Header>
              <Modal.Title> Bienvenido Administrador: {usuario.nombre_usuario}</Modal.Title>
            </Modal.Header>
            <Modal.Header>
              <Modal.Title>Editando Rol de usuario: {usuarioModificar.nombre_usuario} </Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <form>
                <Row className="justify-content-md-center">
                  <Col>
                    <p>
                      Nombre Usuario: {usuarioModificar.nombre_usuario}{" "}
                    </p>
                    <p>
                      Correo Usuario: {usuarioModificar.correo_usuario}{" "}
                    </p>
                    {usuarioModificar.id_rol === 1 ?
                      <p>
                      Rol actual: jefe de proyecto{" "}
                      </p>:
                    <p>
                      Rol actual: Usuario{" "}
                    </p>
                    }
                  </Col>
                  <label>
                    Tipo de usuario:
                    <Form.Select
                      aria-label="Select id_rol"
                      name="id_rol"
                      onChange={this.changeHandler}
                      required
                    >
                    {roles.map((rol) => (
                      <option key={rol.id_rol} type="text" value={rol.id_rol} >
                        {rol.tipo_rol}
                      </option>
                    ))}
                    </Form.Select>
                </label>
                </Row>
                <Modal.Footer>
                  <Button className="Botones"
                    variant="primary"
                    id="volver"
                    href={`/usuarios`}
                  >
                    Volver
                  </Button>
                  <Button className="Botones"
                    variant="success"
                    id="editarRolBoton"
                    onClick={() => this.CambiarRol()}
                  >
                    Modificar Rol
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


export default ModificarRol;