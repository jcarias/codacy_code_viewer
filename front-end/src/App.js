import React, { Component } from "react";

import Header from "./components/Header";
import SideMenu from "./components/SideMenu/SideMenu";
import CommitsScreen from "./containers/CommitsScreen";

import "./App.css";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedScreen: 1
    };
  }

  getSelectedScreen = () => {
    switch (this.state.selectedScreen) {
      case 1:
        return <CommitsScreen />;

      default:
        return null;
    }
  };

  handleScreenSelection = screen => {
    this.setState({ selectedScreen: screen });
  };

  render() {
    return (
      <React.Fragment>
        <Header />
        <div className="app-root-container">
          <div>
            <SideMenu onScreenSelected={this.handleScreenSelection} />
          </div>
          {this.getSelectedScreen()}
        </div>
      </React.Fragment>
    );
  }
}

export default App;
