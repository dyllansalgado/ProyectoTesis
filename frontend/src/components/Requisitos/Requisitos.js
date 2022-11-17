import swal from "sweetalert";
import React, { Component} from "react";
import axios from "axios";
import jsPDF from "jspdf";
import "jspdf-autotable";
import NavbarLogeado from "../Main/NavbarLogeado.js";


class Requisitos extends Component {  
    constructor(props) {
        super(props);
        this.state = {
            preguntasSeleccionadas:[],
            usuario: [],
            id: null,
            proyecto:[],
            reunion:[],
            tema:[],
        };
    }

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
          .get("http://localhost:8080/preguntaSeleccionadaTema2/"+ idPath[4])
          .then((res) => {
            const preguntasSeleccionadas = res.data;
            this.setState({preguntasSeleccionadas});
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
          .get(
              "http://localhost:8080/usuario/"+id)
            .then((res) => {
              const usuario = res.data;
              this.setState({usuario});
            }),
        ]);
      }
    render() { 
        return(
        <div>
          <div>
          <NavbarLogeado/>
          </div>
        </div>
        );
    }
}
export default Requisitos;