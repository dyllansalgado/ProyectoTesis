import React, { Component} from "react";
import {Container, Col, Row, Card, Modal, Form, ModalHeader, ModalBody,} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import "../IngresarAProyecto/IngresarAProyecto.css";
import "./Glosario.css";
import axios from "axios";
import swal from "sweetalert";
import "../Main/NavbarLogeado.css";
import {BsArrowReturnLeft} from "react-icons/bs";


class Glosario extends Component { 
  constructor(props) {
    super(props);
    this.state = {
      usuario: [],
      id: "",
      proyecto:[],
      reunion:[],
      glosario:[],
      nombreGlosario: "",
      descripcionGlosario: "",
      glosarioFiltro: [],
    };
    this.node = React.createRef();
    this.handleModal = this.handleModal.bind(this);
  }

  handleModal() {
    this.setState({ showModal: !this.state.showModal });
  };

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
        .get("http://localhost:8080/glosarioReunion/"+ idPath[3])
        .then((res) => {
          const glosario = res.data;
          this.setState({glosario});
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

  IngresarNuevoGlosario = (e) => {
    let idPath = window.location.pathname.split("/");
    e.preventDefault();
    if (
    this.state.nombreGlosario !== "" &&
    this.state.descripcionGlosario !== ""){
    axios.post("http://localhost:8080/glosario/create", {
      nombre_glosario: this.state.nombreGlosario,
      descripcion_glosario: this.state.descripcionGlosario,
      id_reunion: idPath[3]
    })
    swal({
      title: "Glosario creado con éxito",
      text: "Se ha creado correctamente el Glosario",
      icon: "success",
    });
    setTimeout(() => {
        window.location.replace("http://localhost:3000/GlosarioReunion/"+ idPath[2] + "/" + idPath[3]);
    }, 2000);
    }
    else {
      swal({
        title: "Error al crear el glosario",
        text: "Rellene todos los campos correctamente",
        icon: "warning",
      });
    }
  };
  //Barra de busqueda
  onChange = (e) => {
    if (this.node.current.contains(e.target)) {
      return;
    }
    this.setState({
      glosarioFiltro: [],
    });
  };
  onUserChange = async (e) => {
    let idPath = window.location.pathname.split("/");
    await axios
      .get("http://localhost:8080/glosarioReunion/"+ idPath[3])
      .then((res) => {
        this.setState({
          glosarioFiltro: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
      });

      let filter = e.target.value.toLowerCase();
      let filtroGlosario= this.state.glosarioFiltro.filter((e) => {

        let dataFilter = e.nombre_glosario.toLowerCase();
        return (
          dataFilter
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .indexOf(filter) !== -1)
      });

      this.setState({
        glosario: filtroGlosario,
      });
    };
    deleteGlosario(id_glosario) {
      let idPath = window.location.pathname.split("/");
      swal({
        title: "Atención",
        text: "¿Desea eliminar el glosario seleccionado?",
        icon: "warning",
        buttons: ["No", "Si"],
      }).then((respuesta) => {
        if (respuesta) {
          axios.delete("http://localhost:8080/glosario/" + id_glosario).then((res) => {
            swal({
              title: "Glosario borrado",
              text: "El glosario ha sido borrado con éxito",
              icon: "success",
            });
            setTimeout(() => {
              window.location.replace("http://localhost:3000/GlosarioReunion/"+ idPath[2] + "/" + idPath[3]);
            }, 2000);
          });
        }
      });
    }

    EditarGlosario(id_glosario){
      let idPath = window.location.pathname.split("/");
      swal({
        title: "Atención",
        text: "¿Desea modificar el glosario seleccionado?",
        icon: "warning",
        buttons: ["No", "Si"],
      }).then((respuesta) => {
        if (respuesta) {
          setTimeout(() => {
            window.location.replace("http://localhost:3000/EditarGlosario/"+ idPath[2] + "/" + idPath[3] + "/" + id_glosario);
          }, 2000);
        }
      });
    }
    
    render() {
        const {usuario} = this.state;
        const {proyecto} = this.state;
        const {glosario} = this.state;
        const {reunion} = this.state;
        const nombreGlosario = this.state.nombre_glosario;
        const descripcionGlosario = this.state.descripcion_glosario;
        return ( 
        <div>
            <div>
              <NavbarLogeado />
            </div>
            <div>
                <Container fluid>
                    <Row>
                        <h2 className="titulo"> Glosarios disponibles: {usuario.nombre_usuario}</h2>
                        <div className="container-fluid cew-9">
                            <div className="row">
                                <div className= "subtitulo">
                                  Nombre del Proyecto: {proyecto.nombre_proyecto}
                                </div>
                                <div className="subtitulo">
                                  Objetivos: {proyecto.objetivo_proyecto}
                                </div>
                            </div>
                        </div>
                    </Row>
                    <div className="InformacionCentral">
                    {usuario.id_rol === 1 && proyecto.estado_proyecto === false ?
                    <Button
                      id= "crearGlosario"
                      className="botonCrearProyecto"  
                      onClick={() => this.handleModal()}
                      size="lg">
                      Crear Glosario
                    </Button>:""
                    }
                    {usuario.id_rol === 1 ?
                    <Button id= "volver" className="botonCrearProyecto"   href={`/ingresarReunion/${proyecto.id_proyecto}/${reunion.id_reunion}`} size="lg">
                      Volver
                      <BsArrowReturnLeft/> <span></span>
                    </Button>:
                    <Button id= "volver" className="botonCrearProyecto"   href={`/ingresarReunion/${proyecto.id_proyecto}/${reunion.id_reunion}`} size="lg">
                      Volver
                      <BsArrowReturnLeft/> <span></span>
                    </Button>
                    }
                    <Modal
                      name="formato"
                      show={this.state.showModal}
                      onHide={() => this.handleModal()}
                    >
                      <ModalHeader closeButton>
                        Creando Glosario Proyecto: {proyecto.nombre_proyecto}
                      </ModalHeader>
                      <ModalBody>
                        <Form onSubmit={this.IngresarNuevoGlosario}>
                          <p> Nombre Glosario </p>
                          <input
                            type="text"
                            value={nombreGlosario}
                            className="form-control"
                            name="nombreGlosario"
                            onChange={this.changeHandler}
                            placeholder="Nombre de glosario..."
                          />
                          <p> Descripción Glosario </p>
                          <input
                            id="descripcion"
                            type="text"
                            placeholder="Descripción"
                            className="form-control"
                            value={descripcionGlosario}
                            name="descripcionGlosario"
                            onChange={this.changeHandler}
                          />
                          <Button
                            id="crear"
                            name="botonCrearGlosario"
                            type="submit"
                          >
                            {" "}
                            Crear glosario
                          </Button>
                        </Form>
                      </ModalBody>
                    </Modal>
                        <Col>
                          <div className="filterResponsive">
                            <div className="filterBlockUsuario">
                                <input
                                  type="text"
                                  onClick={this.onChange}
                                  onChange={this.onUserChange}
                                  placeholder="Buscar Glosario..."
                                  ref={this.node}
                                />
                            </div>
                          </div>
                        </Col>
                    </div>
                    <Row className="ReunionList">
                      {glosario.map((glosa) => (
                        <Col lg={4} key={glosa.id_glosario}>
                          <Card className="bg-light text-black">
                            <Card.Body>
                                <Card.Title>Nombre Glosario: {glosa.nombre_glosario}</Card.Title>
                                <Card.Subtitle>Descripción Glosario: {glosa.descripcion_glosario}</Card.Subtitle>
                                <div className="center">
                                  <Button id="ingresarAglosario"
                                    variant="outline-primary" href={`/ingresarAGlosario/${proyecto.id_proyecto}/${reunion.id_reunion}/${glosa.id_glosario}`}
                                  >
                                    Ingresar a glosario
                                  </Button>
                                  <div className="botonescenter">
                                    {usuario.id_rol === 1 && usuario.correo_usuario === proyecto.correoCreador && proyecto.estado_proyecto === false ?
                                    <Button className = "botones" id="editarGlosario" size="sm"
                                      variant="warning" onClick={() => this.EditarGlosario(glosa.id_glosario)}
                                    >
                                      Editar
                                    </Button>:
                                    ""
                                    }
                                    {usuario.id_rol === 1 && usuario.correo_usuario === proyecto.correoCreador && proyecto.estado_proyecto === false ?
                                    <Button className = "botones" id="deleteGlosario" size="sm"
                                      variant="danger" onClick={() => this.deleteGlosario(glosa.id_glosario)}
                                    >
                                      Eliminar
                                    </Button>:
                                    ""
                                    }
                                    </div>
                                </div>
                              </Card.Body>
                            </Card>
                        </Col>
                      ))}
                  </Row>
                </Container>
            </div>
        </div>
        );
    }
}

export default Glosario ;