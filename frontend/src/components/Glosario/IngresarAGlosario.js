import React, { Component} from "react";
import {Container, Col, Row, Table, Modal, Form, ModalHeader, ModalBody,} from "react-bootstrap";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";
import "./Glosario.css";
import jsPDF from "jspdf";
import "jspdf-autotable";
import swal from "sweetalert";
import "../Main/NavbarLogeado.css";
import { BsDownload,BsArrowReturnLeft } from "react-icons/bs";


class IngresarAGlosario extends Component {
    constructor(props) {
        super(props);
        this.state = {
          usuario: [],
          id: null,
          proyecto:[],
          terminos:[],
          reunion:[],
          terminosFiltro:[],
          glosario:[],
          nombreTermino:"",
          descripcionTermino:"",
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
                .get("http://localhost:8080/terminoGlosario/"+ idPath[4])
                .then((res) => {
                    const terminos = res.data;
                    this.setState({terminos});
                    terminos.sort((a,b) => (a.nombre_termino.toLowerCase() < b.nombre_termino.toLowerCase() ? -1 : 1));
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
                    this.setState({ reunion});
                })
                .catch((error) => {
                    console.log(error);
            }),

            axios
            .get("http://localhost:8080/glosario/"+ idPath[4])
            .then((res) => {
                const glosario = res.data;
                this.setState({ glosario});
            })
            .catch((error) => {
                console.log(error);
            }),
        ]);
    }
    //Barra de busqueda
    onChange = (e) => {
        if (this.node.current.contains(e.target)) {
          return;
        }
        this.setState({
          terminosFiltro: [],
        });
    };
    onUserChange = async (e) => {
        let idPath = window.location.pathname.split("/");
        await axios
            .get("http://localhost:8080/terminoGlosario/"+ idPath[4])
            .then((res) => {
              this.setState({terminosFiltro: res.data});
            })
            .catch((err) => {
              console.log(err);
            });
        
            let filter = e.target.value.toLowerCase();
            let filtroProyectos = this.state.terminosFiltro.filter((e) => {
                let dataFilter = e.nombre_termino.toLowerCase();
                let dataFecha = e.descripcion_termino.toLowerCase();
                return (
                    dataFilter
                        .normalize("NFD")
                        .replace(/[\u0300-\u036f]/g, "")
                        .indexOf(filter) !== -1 ||
                    dataFecha
                        .normalize("NFD")
                        .replace(/[\u0300-\u036f]/g, "")
                        .indexOf(filter) !== -1
                );
            });
        
        this.setState({
            terminos: filtroProyectos,
        });
    };
    exportPDF = ()  => {

        swal({
        title: "Atención",
        text: "¿Desea descargar en archivo PDF?",
        icon: "warning",
        buttons: ["No", "Si"],
        }).then((respuesta) => {
            if (respuesta) {
                const unit = "pt";
                const size = "A4";
                //Orientación 
                const orientation = "portrait";
                const marginLeft = 40;
                const doc = new jsPDF(orientation, unit, size);
                doc.setFontSize(15);
                const title = "Nombre de Glosario:  " + this.state.glosario.nombre_glosario;
                const headers = [["Nombre de Término", "Descripción"]];
                const data = this.state.terminos.map(elt=> [elt.nombre_termino, elt.descripcion_termino]);
                let content = {
                  startY: 50,
                  head: headers,
                  body: data
                };
                doc.text(title, marginLeft, 40);
                doc.autoTable(content);
                doc.save("Glosario_"+this.state.glosario.nombre_glosario+".pdf")
            }
        });
    };

    IngresarNuevoTermino = (e) => {
        let idPath = window.location.pathname.split("/");
        e.preventDefault();
        if (
        this.state.nombreTermino !== "" &&
        this.state.descripcionTermino !== ""){
        axios.post("http://localhost:8080/termino/create/"+localStorage.getItem('usuario') , {
            nombre_termino: this.state.nombreTermino,
            descripcion_termino: this.state.descripcionTermino,
            id_glosario: idPath[4]
        });
        swal({
            title: "Término creado con éxito",
            text: "Se ha creado correctamente el término",
            icon: "success",
        });
        setTimeout(() => {
            window.location.replace("http://localhost:3000/ingresarAGlosario/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
        }, 2000);
        }
        else {
            swal({
              title: "Error al crear el término",
              text: "falla",
              icon: "warning",
            });
        }
    };

    deleteTermino(id_termino) {
        let idPath = window.location.pathname.split("/");
        swal({
          title: "Atención",
          text: "¿Desea eliminar el término seleccionado?",
          icon: "warning",
          buttons: ["No", "Si"],
        }).then((respuesta) => {
          if (respuesta) {
            axios.delete("http://localhost:8080/termino/" + id_termino).then((res) => {
              swal({
                title: "Término borrado",
                text: "El término ha sido borrado con éxito",
                icon: "success",
              });
              setTimeout(() => {
                window.location.replace("http://localhost:3000/ingresarAGlosario/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]);
              }, 2000);
            });
          }
        });
    }
    editarTermino(id_termino){
        let idPath = window.location.pathname.split("/");
        swal({
          title: "Atención",
          text: "¿Desea modificar el termino seleccionado?",
          icon: "warning",
          buttons: ["No", "Si"],
        }).then((respuesta) => {
          if (respuesta) {
            setTimeout(() => {
              window.location.replace("http://localhost:3000/EditarTermino/"+ idPath[2] + "/" + idPath[3]+ "/" + idPath[4]+ "/" + id_termino);
            }, 2000);
          }
        });
      }
    render() { 
        const {proyecto} = this.state;
        const {glosario} = this.state;
        const {reunion} = this.state;
        const {terminos} = this.state;
        const {usuario}= this.state;
        const nombreTermino = this.state.nombre_termino;
        const descripcionTermino = this.state.descripcion_termino;
        return(
            <div>
                <div>
                    <NavbarLogeado />
                </div>
                <div>
                    <Container fluid>
                        <Row>
                          <h2 className="titulo"> Terminos de: {glosario.nombre_glosario}</h2>
                          <div className="container-fluid cew-9">
                              <div className="row">
                                  <div className="subtitulo">
                                      Fecha de reunion: {reunion.fecha_reunion} / Lugar de reunion : {reunion.lugar_reunion}
                                  </div>
                              </div>
                          </div>
                        </Row>
                        <div className="InformacionCentral">
                            {proyecto.estado_proyecto === false ?
                            <Button
                                id="crearTermino"
                                className="botonCrearProyecto"  
                                onClick={() => this.handleModal()}
                                size="lg">
                                Crear Término
                            </Button>:
                            ""

                            }
                            <Modal
                                name="formato"
                                show={this.state.showModal}
                                onHide={() => this.handleModal()}
                            >
                                <ModalHeader closeButton>
                                    Creando término de glosario: {glosario.nombre_glosario}
                                </ModalHeader>
                                <ModalBody>
                                    <Form onSubmit={this.IngresarNuevoTermino}>
                                        <p> Nombre término </p>
                                        <input
                                            type="text"
                                            value={nombreTermino}
                                            className="form-control"
                                            name="nombreTermino"
                                            onChange={this.changeHandler}
                                            placeholder="Nombre de término..."
                                            required
                                        />
                                        <p> Descripción del Término </p>
                                        <input
                                            id="descripcion"
                                            type="text"
                                            placeholder="Descripción"
                                            className="form-control"
                                            value={descripcionTermino}
                                            name="descripcionTermino"
                                            onChange={this.changeHandler}
                                            required
                                        />
                                        <Button
                                            id="crear"
                                            name="botonCrearTermino"
                                            type="submit"
                                        >
                                            {" "}
                                          Crear término 
                                        </Button>
                                    </Form>
                                </ModalBody>
                            </Modal>
                            <Button id="descargar" className="botonCrearProyecto"   onClick={() => this.exportPDF()} size="lg">
                                    Descargar
                                    <BsDownload /> <span></span>
                            </Button>
                            <Button id="volver" className="botonCrearProyecto" href={`/GlosarioReunion/${proyecto.id_proyecto}/${reunion.id_reunion}`} size="lg">
                                    Volver
                                    <BsArrowReturnLeft/> <span></span>
                            </Button>
                            <Col>
                                <div className="filterResponsive">
                                    <div className="filterBlock">
                                        <input
                                          type="text"
                                          onClick={this.onChange}
                                          onChange={this.onUserChange}
                                          placeholder="Buscar Término..."
                                          ref={this.node}
                                        />
                                    </div>
                                </div>
                            </Col>
                        </div>
                        <Container fluid>
                            <Table striped bordered hover className="tablaTermino" >
                                <thead>
                                    <tr>
                                        <th width="200">Nombre de término</th>
                                        <th width="1500">Descripción</th>
                                        <th width="200">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                    terminos.map((termino) => (
                                        <tr key={termino.id_termino} >
                                            <td> {termino.nombre_termino} </td>
                                            <td> {termino.descripcion_termino}</td>
                                            <td> 
                                                {(proyecto.estado_proyecto === false && usuario.correo_usuario === termino.correoCreador) || usuario.id_rol === 1?
                                                  <Button className = "botones" id="eliminarTermino" size="sm"
                                                    variant="danger"
                                                    onClick={() => this.deleteTermino(termino.id_termino)}
                                                  >
                                                  {" "}
                                                    Eliminar{" "}
                                                  </Button>:
                                                  <Button className = "botones" id="eliminarTermino" size="sm"
                                                  variant="secondary"
                                                  disabled
                                                >
                                                {" "}
                                                  Eliminar{" "}
                                                </Button>
                                                }
                                                {(proyecto.estado_proyecto === false && usuario.correo_usuario === termino.correoCreador) || usuario.id_rol === 1?
                                                  <Button className = "botones" size="sm"
                                                    id="editarTermino"
                                                    variant="warning"
                                                    onClick={() => this.editarTermino(termino.id_termino)}
                                                  >
                                                  {" "}
                                                    Editar{" "}
                                                  </Button>:
                                                  <Button className = "botones" size="sm"
                                                  id="editarTermino"
                                                  variant="secondary"
                                                  disabled
                                                >
                                                {" "}
                                                  Editar{" "}
                                                </Button>
                                                }
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </Table>
                        </Container>
                    </Container>
                </div>
            </div>
        );
    }
}

export default IngresarAGlosario;