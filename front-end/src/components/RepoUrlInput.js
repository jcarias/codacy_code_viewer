import React, { Component } from "react";

import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";

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
      <Form
        onSubmit={e => {
          e.preventDefault();
          return this.props.onLoadClicked(this.state.repoUrl);
        }}
      >
        <Form.Row>
          <Col>
            <Form.Label htmlFor="repoUrl" size="sm">
              Repository URL
            </Form.Label>
          </Col>
        </Form.Row>
        <Form.Row>
          <Col>
            <Form.Control
              type="url"
              name="repoUrl"
              id="repoUrl"
              size="sm"
              placeholder="Enter the repository URL"
              value={this.state.repoUrl}
              onChange={this.handleChange}
            />
          </Col>
          <Col>
            <Button
              size="sm"
              variant="primary"
              onClick={() => this.props.onLoadClicked(this.state.repoUrl)}
            >
              <span className="mx-3">Load</span>
            </Button>
          </Col>
        </Form.Row>
      </Form>
    );
  }
}

export default RepoUrlInput;
