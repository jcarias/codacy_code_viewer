import React, { Component } from "react";
import cloneDeep from "lodash/cloneDeep";

import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";

import RepoUrlInput from "./components/RepoUrlInput";
import Header from "./components/Header";
import CommitList from "./components/CommitsTable/CommitList";

const COMMITS_API = "http://localhost:8080/base_api_war_exploded/api/commits";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      lastSha: null,
      pageSize: 50,
      repoUrl: "",

      commits: [],
      isLoading: false,
      hasMore: true
    };
  }

  componentDidMount() {
    window.addEventListener("scroll", this.handleWindowScroll);
  }

  componentWillUnmount() {
    window.removeEventListener("scroll", this.handleWindowScroll);
  }

  handleWindowScroll = () => {
    const winScroll =
      document.body.scrollTop || document.documentElement.scrollTop;
    const height =
      document.documentElement.scrollHeight -
      document.documentElement.clientHeight;
    const scrolled = winScroll / height;

    if (scrolled > 0.8 && !this.state.isLoading && this.state.hasMore) {
      this.fetchCommits();
    }
  };

  fetchCommits = () => {
    this.setState({ isLoading: true });
    fetch(COMMITS_API, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        lastCommitSha: this.state.lastSha,
        pageSize: this.state.pageSize,
        url: this.state.repoUrl
      })
    })
      .then(response => response.json())
      .then(data => {
        let existingCommits = cloneDeep(this.state.commits);

        this.setState({
          isLoading: false,
          commits: [...existingCommits, ...data.commits],
          lastSha: data.lastCommitSha,
          hasMore: data.commits.length > 0
        });
      })
      .catch(error => {
        console.error(error);
        this.setState({ isLoading: false, hasMore: false });
      });
  };

  handleLoadClicked = newRepoUrl => {
    this.setState(
      {
        repoUrl: newRepoUrl,
        lastSha: null,
        commits: [],
        hasMore: true
      },
      () => this.fetchCommits()
    );
  };

  render() {
    return (
      <React.Fragment>
        <Header />
        <Container fluid>
          <Row>
            <Col xs="1">Menu</Col>
            <Col xs>
              <Container fluid>
                <Row>
                  <Col xs>
                    <RepoUrlInput onLoadClicked={this.handleLoadClicked} />
                  </Col>
                </Row>
                <Row>
                  <Col xs>
                    <h4 className="mt-4">Commits</h4>
                  </Col>
                </Row>
                <Row>
                  <Col xs>
                    <CommitList
                      commits={this.state.commits}
                      isLoading={this.state.isLoading}
                    ></CommitList>
                  </Col>
                </Row>
              </Container>
            </Col>
          </Row>
        </Container>
      </React.Fragment>
    );
  }
}

export default App;
