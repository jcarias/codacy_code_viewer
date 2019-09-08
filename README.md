# Codacy Code Viewer

## About
The goal of this project is to build a complete solution to retrieve the list of commits from a Git repository by supplying a URL to a GitHub repository... like this repository, for example! ;)

This solution is has 2 parts, a Backend, which exposes an API endpoint. And ReactJS Application (FrontEnd) to consume the endpoint and present the list of commits to the user.

## The Backend

When a call is made to the endpoint, the backend will try to fetch the commits data from the GitHub Api. If retrieving data from github it fails, then an attempt is made to retrieve the information from  the local repository if it has been previously cloned by this application.

When a call is made to the API, if there is network connection and GitHub can be reached, the repository is cloned locally. If the repo already exists locally, it is updated (a "pull" made) to ensure you will always have the most up-to-date information as possible.

The backend solution was written in Java, and it has 3 main modules:
- **The Base API**:
    - Responsible to handle API requests and process the responses.
    - Use the fallback mechanism (local commits) when the main (Github API) fails. 

- **GitHub API Client**
    - Facade for the actual API client.
    - Parses received data into the format used in this system.
    
- **Local Commits Retriver**
    - retrieves the data structure of the commits list from a locally clonned repository. 
    - The management of the local repositories is made using a library: [JGit](https://www.eclipse.org/jgit/).


## FrontEnd

The frontend application was mainly written in javascript (ReactJS). It was created using the tool `create-react-app`. This is a single page application with only a single page implemented: the commits list; where the user supplies a URL to a public Github repository and the commits of the master branch are listed. 

The results are paged using an infinite scroll approach. When the window is scrolled past 80% of it's height, a new page is requested to the backed.

## Building the Solution

### Requirements

To Build the solution you'll have to make sure the following is installed on the test machine:

- Backend
    - Java JDK (version 1.8+)
    - Maven (version 3+)
    

- Frontend
    - Node (10.16.0 +)
    - Yarn (optional)
    
### Build the Backend

The directory where you execute the cloning will now be referred as "`BASE_DIR`".

1. Clone the repository 
    ``` shell
    $ git clone https://github.com/jcarias/codacy_code_viewer.git
    ```

1. Compile & Build
    1. Enter the dir created by the repository clone.
    
        ``` shell
        $ cd <BASE_DIR>/codacy_code_viewer/
        ```
    1. Run maven install command
        ``` shell
        $ mvn clean install
        ```
Done! You can find the resulting Web Application (`.war` file) under `<BASE DRI>/codacy_code_viewer/base_api/target`


### Build the Frontend

1. Enter the dir of the frontend application.
    
    ``` shell
    $ cd <BASE_DIR>/codacy_code_viewer/front-end
    ```
1. Install the node dependencies need for the app. (You case use either **NPM** or **Yarn**).
    - Using `npm`:
        ``` shell
        $ npm install
        ```
    - Using `yarn`:
        ``` shell
        $ yarn
        ```


### Running Requirements

If you gone this far without any major issues, the only remaining requirement is to have a Web Server ready to deploy the web app.

## Running the Solution

1. Deploy 


### Install Backend into Web Application Serve

