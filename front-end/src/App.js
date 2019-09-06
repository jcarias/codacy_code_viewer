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
      pageIndex: 0,
      pageSize: 50,
      repoUrl: "",
      commits: [],
      thePosition: 0,
      isLoading: false,
      itemsTotal: 0
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

    if (
      scrolled > 0.8 &&
      !this.state.isLoading &&
      this.state.commits.length < this.state.itemsTotal
    ) {
      this.setState(
        prevState => {
          return { pageIndex: prevState.pageIndex + 1 };
        },
        () => {
          this.fetchCommits();
        }
      );
    }
  };

  fetchCommits = () => {
    this.setState({ isLoading: true });
    fetch(COMMITS_API, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        page: this.state.pageIndex,
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
          itemsTotal: data.totalCommits
        });
      })
      .catch(error => {
        console.error(error);
        this.setState({ isLoading: false });
      });
  };

  handleLoadClicked = newRepoUrl => {
    this.setState(
      {
        repoUrl: newRepoUrl,
        pageIndex: 0,
        commits: [],
        itemsTotal: 0
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
                    <CommitList
                      commits={this.state.commits}
                      isLoading={this.state.isLoading}
                      totalCommits={this.state.itemsTotal}
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
