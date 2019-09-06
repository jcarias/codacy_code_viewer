# Codacy Code Viewer

## About
The goal of this project is to build a complete solution to retrieve the list of commits from a Git repository by supplying the URL to a GitHub repository... like this one ;)

This solution is the composed of a backend, which exposes an API endpoint, to be consumed by the FrontEnd application.

### The BackEnd

The backend will try to fetch the commits data from the GitHub Api. If retrieving data from github it fails, then an attempt is made to retrieve the information from a locally cloned repository.

 
