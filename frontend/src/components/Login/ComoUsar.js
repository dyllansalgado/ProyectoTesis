import React, { Component } from "react";
import "./Login.css";
import axios from "axios";
import { Button, Container,Table } from "react-bootstrap";
import NavbarInicio from "./NavbarInicio.js";
import NavbarLogeado from "../Main/NavbarLogeado.js";

class ComoUsar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id_rol: null,
            usuario:[],
        };
    }
    componentDidMount() {
        const id = localStorage.getItem('usuario');
        axios
          .get(
            "http://localhost:8080/usuario/"+id)
          .then((res) => {
            const usuario = res.data;
            this.setState({usuario});
        });
    }
    render() {
        const {usuario} = this.state;
        return (
            <div>
                <div>
                { usuario.id_rol === 1 || usuario.id_rol === 2 ?
                <NavbarLogeado />:
                <NavbarInicio />
                }
                </div>
                <Container fluid>
                        <Table striped bordered hover className="tablaComoUsar">
                            <h1 className="TituloAcercaDe">Software Colaborativo de Apoyo para Entrevistas en el proceso de Captura de requisitos</h1>
                                <div className="text-center">
                                    <h2 className="SubitituloAcercaDe">
                                        A continuación se presentan las diferentes funcionalidades:
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Registro e inicio de sesión
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        El usuario se debe registrar en el sistema, ingresando su nombre, apellido, correo, contraseña y un rol. Si el correo ingresando
                                        ya se encuentra registrado, va a recibir un mensaje avisando que este ya se encuentra en uso. Al seleccionar un rol, debe saber 
                                        que contara con diferentes funcionalidades. El jefe de proyecto, puede crear, editar y eliminar proyectos, reuniones, temas, glosarios, términos, preguntas, respuestas y requisitos 
                                        que se encuentren en proyectos que él ha creado. Por otro lado, el rol usuario puede crear, editar y eliminar términos, preguntas y requisitos que él ha creado.
                                    </h2>
                                    <h2 className="textoVersion">
                                        Al iniciar la sesión, si se ha equivocado en ingresar el correo o la contraseña va a recibir un mensaje avisando que se ha equivocado y que vuelva a intentar ingresar sus datos.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Luego de iniciar sesión
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        Si ha ingresado con el rol de jefe de usuario, al iniciar se encuentra con todos los proyectos que se encuentran disponibles para ingresar. Usted tendrá dos botones centrales
                                        llamados "Crear Proyecto" y "Mis proyectos", donde al seleccionar "Crear Proyecto" se le habilita una nueva ventana que le solicitará un nombre de proyecto,
                                        Fecha de inicio de proyecto, los objetivos del proyecto y una contraseña para llevar a cabo el proceso de creación de proyecto. Al crear el proyecto vuelve a la ventana inicial y el proyecto que ha creado se asigna a sus proyectos, que para
                                        visualizar esto debe seleccionar "Mis proyectos", donde se le habilitarán todos los proyectos que usted ha registrado o creado.
                                    </h2>
                                    <h2 className="textoVersion">
                                        Si ha ingresado con el rol de usuario, al iniciar se encuentra con todos los proyectos que se encuentran disponibles para ingresar. Usted tendrá un boton central
                                        llamado "Mis proyectos" donde al seleccionar se le habilitarán todos los proyectos que usted ha registrado.
                                    </h2>
                                    <h2 className="textoVersion">
                                    Independiente del rol que tengan, al estar en la ventana inicial visualizarán todos los proyectos creados y que no ha registrado, podrá ver su nombre, la fecha de inicio del proyecto, el estado del proyecto y el nombre del creador del proyecto. Además, tendrá un botón denominado "Ver más", que al hacer click les mostrará estos mismos datos descritos anteriormente, pero le solicitará una contraseña para registrar el proyecto a sus proyectos. Si la contraseña está errada, el registro no se cumplirá y el proyecto no se va a agregar a sus proyectos.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Funcionalidades a Proyectos
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        Si usted tiene el rol de jefe de proyecto, y ha creado un proyecto, tendrá diferentes funcionalidades para estos proyectos, los cuales corresponden a Ingresar al proyecto, Cerrar el proyecto y Editar el Proyecto. Al Cerrar el proyecto, cambiará el estado del proyecto, pasando de estado Disponible a estado Cerrado, evitando que usuarios con rol usuario puedan seguir realizando funcionalidades una vez el proyecto ya se encuentre terminado, asimismo usted podrá editar el proyecto cambiando el nombre del proyecto, los objetivos y la fecha de inicio.
                                    </h2>
                                    <h2 className="textoVersion">
                                        Si tiene el rol de usuario, solo podrá ingresar a los proyectos que usted ha registrado.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Funcionalidades a Reuniones
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        Si usted tiene el rol de jefe de proyecto, y ha creado un proyecto, podrá crear reuniones a estas mismas, donde debe ingresar una fecha de reunión y el lugar de la reunión. Además, tendrá diferentes funcionalidades, los cuales corresponden a Ingresar a la reunión, Eliminar Reunión y Editar Reunión. Al eliminar una reunión provocará que esta reunión se elimine y no aparezca a ningún usuario, asimismo usted podrá editar la reunión cambiando la fecha de la reunión y el lugar de la reunión.
                                    </h2>
                                    <h2 className="textoVersion">
                                     Si tiene el rol de usuario, solo podrá ingresar a las reuniones que se encuentren disponibles en los proyectos registrados.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Funcionalidades a Temas de Reuniones
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        Si usted tiene el rol de jefe de proyecto, y ha creado una reunión, podrá crear temas de reuniones a estas mismas, donde debe ingresar un nombre de tema de reunión y una descripción de lo que se quiere tratar en ese tema. Además, tendrá diferentes funcionalidades, los cuales corresponden a Ingresar, Eliminar y Editar el tema. Al eliminar un tema provocará que este tema se elimine y no aparezca a ningún usuario, asimismo usted podrá editar el tema cambiando nombre de tema y la descripción del tema.
                                    </h2>
                                    <h2 className="textoVersion">
                                        Si tiene el rol de usuario, solo podrá ingresar a los temas que se encuentren disponibles en los proyectos registrados.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Funcionalidades a Glosarios de Términos
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        Si usted tiene el rol de jefe de proyecto, y ha creado un tema, podrá crear glosario de términos a estas mismas, donde debe ingresar un nombre de glosario  y una descripción breve del glosario. Además, tendrá diferentes funcionalidades, los cuales corresponden a Ingresar, Eliminar y Editar el glosario. Al eliminar un glosario provocará que este glosario se elimine y no aparezca a ningún usuario, asimismo usted podrá editar el glosario cambiando nombre de glosario y la descripción del glosario.
                                    </h2>
                                    <h2 className="textoVersion">
                                        Si tiene el rol de usuario, solo podrá ingresar a los glosarios de términos que se encuentren disponibles en los proyectos registrados.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Funcionalidades de Términos a glosarios
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        Si usted tiene el rol de jefe de proyecto, podrá crear términos a los glosarios, donde debe ingresar un nombre de término y una descripción breve del término. Además, tendrá diferentes funcionalidades, los cuales corresponden a Eliminar, Editar y descargar todos los términos presentes en el glosario. Al eliminar un término provocará que este se elimine y no aparezca a ningún usuario, asimismo usted podrá editar el término cambiando el nombre y la descripción.
                                    </h2>
                                    <h2 className="textoVersion">
                                        Si tiene el rol de usuario, solo podrá crear, editar y eliminar términos usted que haya creado. Asimismo usted puede descargar todos los términos presentes en el glosario.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Funcionalidades a Temas
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        Si usted tiene el rol de jefe de proyecto, y ha creado una reunión, podrá crear temas a estas mismas, donde debe ingresar un nombre de tema y una breve descripción. Además, tendrá diferentes funcionalidades, los cuales corresponden a Ingresar al Tema, Eliminar Tema y Editar Tema. Al eliminar un Tema provocará que este se elimine y no aparezca a ningún usuario, asimismo usted podrá editar el Tema cambiando el nombre y su descripción.
                                    </h2>
                                    <h2 className="textoVersion">
                                        Si tiene el rol de usuario, solo podrá ingresar a los temas que se encuentren disponibles en las reuniones creadas.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Funcionalidades dentro de un Tema
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        Si usted tiene el rol de jefe de proyecto, y se encuentra dentro de un Tema, podrá crear preguntas a estas mismas, donde debe ingresar la pregunta deseada. Además, tendrá diferentes funcionalidades, los cuales corresponden a aceptar preguntas, eliminar preguntas, crear comentarios, barra de búsqueda de preguntas y utilizar el botón de preguntas recomendadas, donde al seleccionar una pregunta se guardará en el portapapeles, luego al querer utilizar esta pregunta debe dirigirse a crear preguntas selecciona pregunta... y apretar el botón del teclado "Ctrl + V" y su pregunta seleccionada se pegará. Al eliminar una Pregunta provocará que este se elimine y no aparezca a ningún usuario.
                                    </h2>
                                    <h2 className="textoVersion">
                                        Si tiene el rol de usuario, podrá ingresar preguntas, donde debe ingresar la pregunta deseada. Además, tendrá diferentes funcionalidades, como eliminar preguntas que usted haya realizado, barra de búsqueda de preguntas, Votar una única vez a una pregunta que encuentre necesaria que se agregue al guión, usar el botón de preguntas recomendadas, donde al seleccionar una pregunta se guardará en el portapapeles, luego al querer utilizar esta pregunta debe dirigirse a crear preguntas selecciona pregunta... y apretar el botón del teclado "Ctrl + V" y su pregunta seleccionada se pegará y por último puede generar comentarios a las preguntas.
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="FuncionalidadesAcercaDe">
                                        Funcionalidades Comentarios
                                    </h2>
                                </div>
                                <div className="text-center">
                                    <h2 className="textoVersion">
                                        aaa
                                    </h2>
                                    <h2 className="textoVersion">
                                        aaa
                                    </h2>
                                </div>
                                <div className="text-center">
                                { usuario.id_rol === 1 || usuario.id_rol === 2 ?
                                <Button variant="secondary" className="botonVolver" href="/main" size="sm">Volver</Button>
                                :
                                <Button variant="secondary" className="botonVolver" href="/" size="sm">Volver</Button>
                                }
                                </div>
                        </Table>
                </Container>
            </div>
        );
    }
}

export default ComoUsar;