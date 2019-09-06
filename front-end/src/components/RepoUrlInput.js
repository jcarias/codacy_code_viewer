import React, { Component } from "react";

import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

class RepoUrlInput extends Component {
  constructor(props) {
    super(props);
    this.state = { repoUrl: "" };
  }

  handleChange = ev => {
    this.setState({ [ev.currentTarget.name]: ev.currentTarget.value });
  };
  render() {
    return (
      <Form>
        <Form.Group>
          <Form.Label>Email address</Form.Label>
          <Form.Control
            type="url"
            name="repoUrl"
            placeholder="Enter the repository URL"
            value={this.state.repoUrl}
            onChange={this.handleChange}
          />
        </Form.Group>
        <Button
          variant="primary"
          onClick={() => this.props.onLoadClicked(this.state.repoUrl)}
        >
          Load
        </Button>
      </Form>
    );
  }
}

export default RepoUrlInput;
