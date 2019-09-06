import React from "react";

import Navbar from "react-bootstrap/Navbar";

import logo from "../codacy-white.svg";

const Header = props => {
  return (
    <Navbar bg="dark">
      <Navbar.Brand href="#home">
        <img
          src={logo}
          width="30"
          height="30"
          className="d-inline-block align-top"
          alt="React Bootstrap logo"
        />
      </Navbar.Brand>
    </Navbar>
  );
};

export default Header;
