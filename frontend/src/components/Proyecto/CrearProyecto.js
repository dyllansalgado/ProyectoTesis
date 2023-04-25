import React, { Component } from "react";
import axios from "axios";
import {Button,Container} from "react-bootstrap";
import swal from "sweetalert";
import NavbarLogeado from "../Main/NavbarLogeado.js";
import DatePicker, { registerLocale } from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import "./CrearProyecto.css";
import PasswordChecklist from "react-password-checklist"
import es from "date-fns/locale/es"
registerLocale("es",es);

class CrearProyecto extends Component {
    constructor(props) {
      super(props);
      this.state = { nombre_proyecto: "", 
      fecha_inicio_proyecto: "", 
      objetivo_proyecto: "",
      contrasena: "",
      contrasena2:"",
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
        this.state.contrasena !== "" &&
        this.state.contrasena === this.state.contrasena2)
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
              title: "Proyecto creado con éxito",
              text: "Se ha creado correctamente el proyecto",
              icon: "success",
            }),
            setTimeout(() => {
              window.location.replace("http://localhost:3000/main/");
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
          text: "Rellene todos los datos solicitados",
          icon: "warning",
        });
      }
    };

    render() {
      const nombre_proyecto = this.state.nombre_proyecto;
      const objetivo_proyecto = this.state.objetivo_proyecto;
      const contrasena = this.state.contrasena;
      const contrasena2 = this.state.contrasena2;
  
      return (
        <div>
          <NavbarLogeado />
          <div>
          <Container fluid>
            <div className="center">
            <form className="loginForm" onSubmit={this.CrearProyecto}>
                <div className="center">
                  <h3 className="color-custom">Creación de Proyecto</h3>
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
                        <DatePicker selected={this.state.fecha_inicio_proyecto} onChange={this.onChange} 
                        locale = "es"
                        className="pickers" 
                        dateFormat="dd-MM-yyyy"
                        />
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
                        placeholder="Objetivos del proyecto"
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
                        minLength={8}
                        name="contrasena"
                        onChange={this.changeHandler}
                        placeholder="Contraseña"
                        required
                      />
                    </label>
                  </div>
                  <div className="form-group">
                  <label>
                    Repetir Contraseña:
                    <input
                      type="password"
                      value={contrasena2}
                      name="contrasena2"
                      onChange={this.changeHandler}
                      placeholder="Repetir Contraseña"
                      required
                    />
                  </label>
                  </div>
                  <PasswordChecklist
				            rules={["minLength","specialChar","number","capital","match"]}
				            minLength={8}
				            value={contrasena}
                    valueAgain={contrasena2}
                    messages={{
                      minLength: "La contraseña tiene más de 8 caracteres.",
                      specialChar: "La contraseña tiene caracteres especiales.",
                      number: "La contraseña tiene un número.",
                      capital: "La contraseña tiene una letra mayúscula.",
                      match: "Las contraseñas coinciden."
                    }}
			            />
                  <div className="text-center">
                  <Button className="botonIngresar" type ="submit" value="Submit" size="sm">
                    {" "}
                    Crear Proyecto
                  </Button>
                  <Button className="botonRegistrarse" href="/main/" size="sm">Volver</Button>
                  {""}
                  </div>
                </div>
              </form>
            </div>
          </Container>
          </div>
        </div>
      );
    }
  }
  
  export default CrearProyecto;