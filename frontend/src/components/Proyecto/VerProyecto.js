import React, { Component } from "react";
import {Button,Navbar,} from "react-bootstrap";
class VerProyecto extends Component { 

    render() {  
        return (
            <>
              <Navbar collapseOnSelect className="navbarLogeado">
                  <Navbar.Brand href="/mainJefeProyecto">
                  </Navbar.Brand>
                  <Button className="botonAyudaLogeado"  href="/ayuda" size="sm">
                      ¿Necesitas ayuda?
                  </Button>
                  <Button className="botonCerrarSesion"  href="/verPerfil" size="sm">
                      Ver perfil
                  </Button>
                  <Button className="botonCerrarSesion"  size="sm">
                      Cerrar sesión
                  </Button>
              </Navbar>
            </>
        );
    }
}

export default VerProyecto;