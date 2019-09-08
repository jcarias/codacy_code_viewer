import React from "react";

import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";

import logo from "../codacy-white.svg";

const Header = props => {
  return (
    <Navbar style={{ backgroundColor: "#101e35", minHeight: 60 }}>
      <Container fluid>
        <Navbar.Brand href="#home" style={{ padding: "0 15px" }}>
          <img
            src={logo}
            width="35"
            height="35"
            className="d-inline-block align-top"
            alt="Codacy logo"
          />
        </Navbar.Brand>
      </Container>
    </Navbar>
  );
};

export default Header;
