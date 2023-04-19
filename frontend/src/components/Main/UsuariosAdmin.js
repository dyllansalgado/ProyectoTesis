import React, { Component} from "react";
import {Container, Col,Table } from "react-bootstrap";
import NavbarLogeado from "./NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";
import swal from "sweetalert";
import "./NavbarLogeado.css"
import "bootstrap/dist/css/bootstrap.min.css";


class UsuariosAdmin extends Component {
  constructor(props) {
    super(props);
    this.state = {
      usuario: [],
      usuarios:[],
      id: null,
      proyectos: [],
      proyectosFiltro: []
    };
    this.node = React.createRef();
  }

  componentDidMount() {
    if (localStorage.getItem("token") == null && localStorage.getItem("id_rol") == null ){
      window.location.replace("http://localhost:3000/");
    }
    const id = localStorage.getItem('usuario');
    axios.all([
      axios
        .get(
          "http://localhost:8080/usuario/"+id)
        .then((res) => {
          const usuario = res.data;
          this.setState({usuario});
        }),
    axios
        .get(
          "http://localhost:8080/usuarios/")
        .then((res) => {
          const usuarios = res.data;
          this.setState({usuarios});
        }),
    ]);
  }
  //Barra de busqueda
  onChange = (e) => {
    if (this.node.current.contains(e.target)) {
      return;
    }
    this.setState({
      usuariosFiltro: [],
    });
  };
  onUserChange = async (e) => {
    await axios
      .get("http://localhost:8080/usuarios/")
      .then((res) => {
        this.setState({
          usuariosFiltro: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
      });

      let filter = e.target.value.toLowerCase();
      let filtroUsuarios = this.state.usuariosFiltro.filter((e) => {

        let dataFilter = e.nombre_usuario.toLowerCase();
        let dataCorreo = e.correo_usuario.toLowerCase();
        let dataApellido = e.apellido_usuario.toLowerCase();
        return (
          dataFilter
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1 ||
          dataCorreo
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1 ||
          dataApellido
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1
          );
      });
    this.setState({
      usuarios: filtroUsuarios,
    });
  };

  editarRol(id_usuario){
    swal({
      title: "Atención",
      text: "¿Desea modificar el rol del usuario seleccionado?",
      icon: "warning",
      buttons: ["No", "Si"],
    }).then((respuesta) => {
      if (respuesta) {
        setTimeout(() => {
          window.location.replace("http://localhost:3000/editarRol/"+ id_usuario);
        }, 2000);
      }
    });
  }
  render() {
    const {usuario} = this.state;
    const {usuarios} = this.state;
    return (
      <div>
        <div>
          <NavbarLogeado />
        </div>
        <div>
        <Container fluid>
            <div>
                <Col>
                  <h3 className="titulo"> Bienvenido administrador: {usuario.nombre_usuario}</h3>
                </Col>
            </div>
            <div className="InformacionCentral">
              <h3 className="centerTitulo"> Usuarios registrados</h3>
              <Button id="UsuarioRegistrados" className="botonCrearProyectoUsuario"  href="/main" size="lg">
              Volver
              </Button>
              <Col>
              <div className="filterResponsive">
                  <div className="filterBlockUsuario">
                    <input
                      type="text"
                      onClick={this.onChange}
                      onChange={this.onUserChange}
                      placeholder="Buscar Proyecto..."
                      ref={this.node}
                    />
                  </div>
                </div>
            </Col>
            </div>
            <div>
            <Container fluid>
            <Table striped bordered hover className="tablaTermino">
                  <thead>
                    <tr>
                      <th width="350">Nombre usuario</th>
                      <th width="350">Apellido usuario</th>
                      <th width="350">Correo usuario</th>
                      <th width="350">Rol actual</th>
                      <th width="350">Cambiar rol</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                    usuarios.map((users) => (
                      <tr key={users.id_usuario} >
                        <td> {users.nombre_usuario} </td>
                        <td> {users.apellido_usuario} </td>
                        <td> {users.correo_usuario} </td>
                        {users.id_rol === 1 ?
                          <td> Jefe de Proyecto </td>
                        :
                        ""}
                        {users.id_rol === 2 ?
                          <td> Usuario </td>
                        :
                        ""}
                        {users.id_rol === 3 ?
                          <td> Administrador </td>
                        :
                        ""}
                        <td>
                        <Button
                              id="modificarRol"
                              variant="success"
                              onClick={() => this.editarRol(users.id_usuario)}
                            >
                            {" "}
                              Modificar Rol{" "}
                            
                        </Button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
            </Container>
            </div>
        </Container>
        </div>
      </div>
    );
  }
}

export default UsuariosAdmin;
