import React, { Component} from "react";
import {Container} from "react-bootstrap";
import NavbarLogeadoJP from "../Main/NavbarLogeadoJP.js";
import Button from 'react-bootstrap/Button';
import axios from "axios";
import swal from "sweetalert";
import DatePicker, { registerLocale } from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import "../Proyecto/CrearProyecto.css";
import es from "date-fns/locale/es"
registerLocale("es",es);


class CrearReunion extends Component {
    constructor(props) {
        super(props);
        this.state = { fecha_reunion: "", 
        lugar_reunion: "", 
        estado: true,
        proyecto: [],
        fecha: new Date("2018", "06", "22"),
        };
    }


    changeHandler = (e) => {
      this.setState({ [e.target.name]: e.target.value });
    };

    onChange=fecha=>{
      this.setState({fecha_reunion: fecha});
    }

    CrearReunion = (e) => {
      let idPath = window.location.pathname.split("/");
      e.preventDefault();
      if (
        this.state.fecha_reunion !== "" &&
        this.state.lugar_reunion !== "" )
        {
          axios.post("http://localhost:8080/reunion/create", {
          fecha_reunion: this.state.fecha_reunion,
          lugar_reunion: this.state.lugar_reunion,
          estado: this.state.estado,
          id_proyecto: idPath[2]
          });
  
          swal({
            title: "Reunion creada con exito",
            text: "Se ha creado correctamente la reunion",
            icon: "success",
          });
          setTimeout(() => {
            window.location.replace("http://localhost:3000/ingresarAProyectoJP/"+idPath[2]);
          }, 2000);
        }
      else {
        swal({
          title: "Error al crear la reunion",
          text: "falla",
          icon: "warning",
        });
  
      }
    };
    componentDidMount() {
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
    ]);
  }


    render() {
      const fecha_reunion = this.state.fecha_reunion;
      const lugar_reunion = this.state.lugar_reunion;
      const {proyecto} = this.state;
        return (
            <div>
              <NavbarLogeadoJP />
              <Container fluid>
              <h3 className="titulo">{proyecto.nombre_proyecto} </h3>
              <div className="fondo">
                <div className="container_register">
                <form className="registrarseForm" onSubmit={this.CrearReunion}>
                    <div className="center">
                      <h3 className="titulo">Creación de Reunion</h3>
                      <div className="form-group">
                        <label>
                          Fecha de reunion:
                          <div className="contenedorDate">
                            <div className="centerDate">
                            <DatePicker selected={fecha_reunion} onChange={this.onChange} locale = "es" className="pickers" dateFormat="dd-MM-yyyy"/>
                            </div>
                          </div>
                        </label>
                      </div>
                      <div className="form-group">
                        <label>
                          Lugar de reunion:
                          <input
                            className="inputRegister"
                            type="text"
                            value={lugar_reunion}
                            name="lugar_reunion"
                            onChange={this.changeHandler}
                            placeholder="aaaa"
                            required
                          />
                        </label>
                      </div>
                      <Button type="submit" value="Submit">
                        {" "}
                        Crear Reunion
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
export default CrearReunion ;