import React, { Component } from "react";
import {Container } from "react-bootstrap";


class Main extends Component {


    render() {
        return (
          <div>
            <Container fluid>
              <div className="center">
                <form className="tituloUsers" >
                  <div className="center">
                    <h3 className="color-custom">bienvenido jefe de proyecto</h3>
                  </div>
                </form>
              </div>
            </Container>
          </div>
        );
      }
}

export default Main;
