import React, { Component } from "react";
import { Button, Container } from "react-bootstrap";


class MainUsuario extends Component {


    render() {
        return (
          <div>
            <Container fluid>
              <div className="center">
                <form className="tituloUsers" >
                  <div className="center">
                    <h3 className="color-custom">bienvenido usuario</h3>
                  </div>
                </form>
              </div>
            </Container>
          </div>
        );
      }
}

export default MainUsuario;