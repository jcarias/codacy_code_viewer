# Codacy Code Viewer

## About

The goal of this project is to build a complete solution to retrieve the list of commits from a Git repository by supplying a URL to a GitHub repository... like this repository, for example! ;)

This solution is has 2 parts, a Backend, which exposes an API endpoint. And ReactJS Application (FrontEnd) to consume the endpoint and present the list of commits to the user.

## The Backend

When a call is made to the endpoint, the backend will try to fetch the commits data from the GitHub Api. If retrieving data from github it fails, then an attempt is made to retrieve the information from the local repository if it has been previously cloned by this application.

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

* Frontend
  - Node (10.16.0 +)
  - Yarn (optional)

### Build the Backend

The directory where you execute the cloning will now be referred as "`BASE_DIR`".

1. Clone the repository

   ```shell
   $ git clone https://github.com/jcarias/codacy_code_viewer.git
   ```

1. Compile & Build 1. Enter the dir created by the repository clone.
   `shell $ cd <BASE_DIR>/codacy_code_viewer/` 1. Run maven install command
   `shell $ mvn clean install`
   Done! You can find the resulting Web Application (`.war` file) under `<BASE DRI>/codacy_code_viewer/base_api/target`

### Build the Frontend

1. Enter the dir of the frontend application.

   ```shell
   $ cd <BASE_DIR>/codacy_code_viewer/front-end
   ```

1. Install the node dependencies need for the app. (You case use either **NPM** or **Yarn**).
   - Using `npm`:
     ```shell
     $ npm install
     ```
   - Using `yarn`:
     ```shell
     $ yarn
     ```

### Running Requirements

If you gone this far without any major issues, the only remaining requirement is to have a Web Server ready to deploy the web app.

The recommended one is **Apache Tomcat 8.5**. The following instructions will assume this is the web server used.

There is also an additional optional requirement. Create an environment variable `CCV_REPO_DIR` with the desired path to the location where you wish to clone the repositories into.

## Running the Solution

### Deploy the Backend

1. Copy the generated .war file (`<BASE DRI>/codacy_code_viewer/base_api/target/base_api.war`) to the `/webapps` folder inside the Tomcat's folder.
1. Restart the server to make sure the application was installed properly.

### Running the frontend

The frontend can be ejected and have the minified resources deployed into a wer server. But, for test purposes, you can simply run it from it's location. To do it:

1. Head to the frontend app location

   ```shell
   $ cd <BASE_DIR>/codacy_code_viewer/front-end
   ```

1. (_Optional_, this can be done in the next step) Add the `REACT_APP_CVV_API_HOST` environment variable with the address of the backend app (e.g. _`REACT_APP_CVV_API_HOST=http://localhost:8080/base_api`_).

1. Run the app on a test server (`http://localhost:3000`). In this step you can define the environment variable if you skipped the previous step. (You case use either **NPM** or **Yarn**)

   - Using `npm`:

     ```shell
     $ npm start
     ```

   - Using `yarn`:
     ```shell
     $ yarn start
     ```

   #### (Optional) Define the environment variable and run the app.

   - Using `npm`:

   ```shell
   (Windows)
    set "REACT_APP_CVV_API_HOST=<URL>" && npm start

   (Linux and Mac OS)
    REACT_APP_CVV_API_HOST=<URL> npm start
   ```


    - Using `yanr`:
    ```shell
    (Windows)
    set "REACT_APP_CVV_API_HOST=<URL>" && yarn start

    (Linux and Mac OS)
    REACT_APP_CVV_API_HOST=<URL> yarn start
    ```

## About the API

The API exposes a single endpoint `/api/commits`. To get list of commits you should make a POST request with the following body:

```javascript
{
  "pageSize": 50,
  "url": "https://github.com/facebook/react"
}
```

#### Request Body

| Name            | Type   | Required | Default | Description                                                                                   |
| --------------- | ------ | :------: | ------- | --------------------------------------------------------------------------------------------- |
| `url`           | String |   Yes    |         | The Github repository URL.                                                                    |
| `lastCommitSha` | String |    No    |         | The SHA of the last commit returned from a previous request. This is returned in the response |
| `pageSize`      | Number |    No    | 30      | The number of commits to be returned.                                                         |

The expected response should be something like this:

```javascript
{
  "lastCommitSha": "2f15881859475cee7945eafbc7252244ce240100",
  "pageSize": 2,
  "commits": [
    {
      "date": 1567798216000,
      "committer": {
        "date": "Fri Sep 06 20:30:16 BST 2019",
        "avatarUrl": "https://avatars3.githubusercontent.com/u/19864447?v=4",
        "name": "GitHub",
        "email": "noreply@github.com"
      },
      "author": {
        "date": "Fri Sep 06 20:30:16 BST 2019",
        "avatarUrl": "https://avatars0.githubusercontent.com/u/810438?v=4",
        "name": "Dan Abramov",
        "email": "dan.abramov@gmail.com"
      },
      "message": "[Fresh] Add skipEnvCheck option to Babel plugin (#16688)",
      "sha": "b260bef398c4da465a41f6b95bc0cf64deafdfd7"
    },
    {
      "date": 1567796563000,
      "committer": {
        "date": "Fri Sep 06 20:02:43 BST 2019",
        "avatarUrl": "https://avatars0.githubusercontent.com/u/810438?v=4",
        "name": "Dan Abramov",
        "email": "dan.abramov@gmail.com"
      },
      "author": {
        "date": "Fri Sep 06 20:02:43 BST 2019",
        "avatarUrl": "https://avatars0.githubusercontent.com/u/810438?v=4",
        "name": "Dan Abramov",
        "email": "dan.abramov@gmail.com"
      },
      "message": "react-refresh@0.4.1",
      "sha": "2f15881859475cee7945eafbc7252244ce240100"
    }
  ]
}
```
