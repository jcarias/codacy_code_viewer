import React, { Component } from "react";
import cloneDeep from "lodash/cloneDeep";
import isEmpty from "lodash/isEmpty";

import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";

import RepoUrlInput from "./components/RepoUrlInput";
import Header from "./components/Header";
import CommitList from "./components/CommitsTable/CommitList";
import SideMenu from "./components/SideMenu/SideMenu";

import "./App.css";
import AlertModal from "./components/AlertModal/AlertModal";
import Loader from "./components/Loader";

const COMMITS_API = "http://localhost:8080/base_api/api/commits";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      lastSha: null,
      pageSize: 20,
      repoUrl: "",

      commits: [],
      isLoading: false,
      hasMore: true,
      showModal: false,
      modalMessage: "",
      modalTitle: "",
      errorCode: null
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
      this.state.hasMore &&
      !isEmpty(this.state.repoUrl)
    ) {
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
      .then(response => {
        if (!response.ok) {
          response.json().then(error => {
            this.setState({
              showModal: true,
              modalMessage: error.message,
              modalTitle: `Something went wrong...`,
              errorCode: error.statusCode
            });
          });
          throw Error(response.statusText);
        } else {
          return response.json();
        }
      })
      .then(data => {
        let existingCommits = cloneDeep(this.state.commits);
        const hasMoreCommits = this.state.lastSha !== data.lastCommitSha;
        const newCommits = hasMoreCommits
          ? [...existingCommits, ...data.commits]
          : existingCommits;

        this.setState({
          isLoading: false,
          commits: newCommits,
          lastSha: data.lastCommitSha,
          hasMore: hasMoreCommits
        });
      })
      .catch(error => {
        this.setState({
          isLoading: false,
          hasMore: false
        });
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

  hideModal = () => {
    this.setState({ showModal: false });
  };

  render() {
    return (
      <React.Fragment>
        <AlertModal
          show={this.state.showModal}
          onHide={this.hideModal}
          title={this.state.modalTitle}
        >
          <p className="text-body">{this.state.modalMessage}</p>
          <small className="text-muted">
            {`Error Code: ${this.state.errorCode}`}
          </small>
        </AlertModal>
        <Header />
        <div className="app-root-container">
          <div>
            <SideMenu />
          </div>

          <Container fluid>
            <Row>
              <Col xs>
                <Container fluid>
                  <Row>
                    <Col xs className="mt-5">
                      <RepoUrlInput onLoadClicked={this.handleLoadClicked} />
                    </Col>
                  </Row>
                  <Row>
                    <Col xs>
                      <h4 className="mt-5">Commits</h4>
                      <hr />
                    </Col>
                  </Row>
                  <Row>
                    <Col xs>
                      <CommitList commits={this.state.commits}></CommitList>
                    </Col>
                  </Row>
                  <Row>
                    <Col>
                      {this.state.isLoading && (
                        <div
                          style={{
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center"
                          }}
                        >
                          <span className="mr-2">
                            {`${this.state.commits.length} loaded so far. Loading more ${this.state.pageSize}...`}
                          </span>
                          <Loader color="rgba(16, 30, 53, 0.6)" size={24} />
                        </div>
                      )}
                      {this.state.hasMore || (
                        <p className="text-black-50">{`${
                          this.state.commits ? this.state.commits.length : "No "
                        } commits.`}</p>
                      )}
                    </Col>
                  </Row>
                </Container>
              </Col>
            </Row>
          </Container>
        </div>
      </React.Fragment>
    );
  }
}

export default App;
