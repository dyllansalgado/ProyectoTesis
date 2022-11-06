import React, { Component } from "react";
import axios from "axios";
import {Button,Container} from "react-bootstrap";
import swal from "sweetalert";
import NavbarLogeadoJP from "../Main/NavbarLogeadoJP.js";
import DatePicker, { registerLocale } from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import "./CrearProyecto.css";
import es from "date-fns/locale/es"
registerLocale("es",es);

class CrearProyecto extends Component {
    constructor(props) {
      super(props);
      this.state = { nombre_proyecto: "", 
      fecha_inicio_proyecto: "", 
      objetivo_proyecto: "",
      contrasena: "",
      usuario: [],
      id: null,
      estado_proyecto: false,
      fecha: new Date("2018", "06", "22"),
      };
    }
  
    changeHandler = (e) => {
      this.setState({ [e.target.name]: e.target.value });
    };

    onChange=fecha=>{
      this.setState({fecha_inicio_proyecto: fecha});
    }
    
    CrearProyecto = (e) => {
      e.preventDefault();
      if (
        this.state.nombre_proyecto !== "" &&
        this.state.fecha_inicio_proyecto !== "" &&
        this.state.objetivo_proyecto !== "" &&
        this.state.contrasena !== "")
        {
          axios.all([
            axios.post("http://localhost:8080/proyecto/create/"+localStorage.getItem('usuario'), {
            nombre_proyecto: this.state.nombre_proyecto,
            fecha_inicio_proyecto: this.state.fecha_inicio_proyecto,
            objetivo_proyecto: this.state.objetivo_proyecto,
            contrasena: this.state.contrasena,
            estado_proyecto: this.state.estado_proyecto,
            id_usuario: localStorage.getItem('usuario'),
            }),
          
            swal({
              title: "Proyecto creado con exito",
              text: "Se ha creado correctamente el proyecto",
              icon: "success",
            }),
            setTimeout(() => {
              window.location.replace("http://localhost:3000/mainJefeProyecto/");
            }, 2000),

            axios
              .get("http://localhost:8080/proyectos/")
              .then((res) => {
                const proyectos = res.data;
                console.log(proyectos);
                this.setState({proyectos});
              })
              .catch((error) => {
                console.log(error);
              }),
          ]);  
        }
      else {
        swal({
          title: "Error al crear el proyecto",
          text: "falla",
          icon: "warning",
        });
      }
    };

    render() {
      const nombre_proyecto = this.state.nombre_proyecto;
      const objetivo_proyecto = this.state.objetivo_proyecto;
      const contrasena = this.state.contrasena;
  
      return (
        <div>
          <NavbarLogeadoJP />
          <Container fluid>
          <div className="fondo">
            <div className="container_register">
            <form className="registrarseForm" onSubmit={this.CrearProyecto}>
                <div className="center">
                  <h3 className="titulo">Creación de Proyecto</h3>
                  <div className="form-group">
                    <label>
                      Nombre de Proyecto:
                      <input
                        className="inputRegister"
                        type="text"
                        value={nombre_proyecto}
                        name="nombre_proyecto"
                        onChange={this.changeHandler}
                        placeholder="Proyecto...."
                        required
                      />
                    </label>
                  </div>
                  <div className="form-group">
                    <label>
                      Fecha de inicio proyecto:
                      <div className="contenedorDate">
                        <div className="centerDate">
                        <DatePicker selected={this.state.fecha_inicio_proyecto} onChange={this.onChange} locale = "es" className="pickers" dateFormat="dd-MM-yyyy"/>
                        </div>
                      </div>
                    </label>
                  </div>
                  <div className="form-group">
                    <label>
                      Objetivos del proyecto:
                      <input
                        className="inputRegister"
                        type="text"
                        value={objetivo_proyecto}
                        name="objetivo_proyecto"
                        onChange={this.changeHandler}
                        placeholder="aaaa"
                        required
                      />
                    </label>
                  </div>
                  <div className="form-group">
                    <label>
                      Contraseña:
                      <input
                        className="inputRegister"
                        type="password"
                        value={contrasena}
                        name="contrasena"
                        onChange={this.changeHandler}
                        placeholder="*****"
                        required
                      />
                    </label>
                  </div>
                  <Button type="submit" value="Submit">
                    {" "}
                    Crear Proyecto
                  </Button>
                </div>
              </form>
            </div>
          </div>
          </Container>
        </div>
      );
    }
  }
  
  export default CrearProyecto;