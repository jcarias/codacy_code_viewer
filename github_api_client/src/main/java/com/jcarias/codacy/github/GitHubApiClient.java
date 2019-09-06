package com.jcarias.codacy.github;


import com.jcarias.codacy.github.helpers.GitGubRepoUrlParser;
import com.jcarias.codacy.github.helpers.IncorrectHostException;
import com.jcarias.codacy.github.helpers.PersonParser;
import com.jcarias.codacy.github.model.GitGubRepoParams;
import com.jcarias.git.model.CommitInfo;
import com.jcarias.git.model.Person;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.*;

public class GitHubApiClient {

	private static final String API_ADDRESS = "https://api.github.com/repos";

	public Collection<CommitInfo> fetchRepoCommits(URL repoUrl) throws IncorrectHostException {
		Client client = ClientBuilder.newClient();
		client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
		client.property(ClientProperties.READ_TIMEOUT,    5000);

		GitGubRepoParams params = new GitGubRepoUrlParser().parse(repoUrl);
		String owner = params.getOwner();
		String repository = params.getRepository();
		WebTarget webTarget = client.target(API_ADDRESS).path(owner).path(repository).path("commits");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		System.out.println(response.getStatus());
		Object responseEntity = response.readEntity(Object.class);

		Collection<CommitInfo> commits = new ArrayList<>();

		if (responseEntity instanceof ArrayList) {
			ArrayList commitsArray = (ArrayList) responseEntity;

			for (Object object : commitsArray) {
				if (object instanceof Map) {
					Map commitMap = (Map) object;
					String sha = (String) commitMap.get("sha");
					CommitInfo commit = parseCommit(sha, (Map) commitMap.get("commit"));
					commits.add(commit);
				}
			}
		}

		return commits;
	}


	private CommitInfo parseCommit(String sha, Map commit) {

		Map authorMap = (Map) commit.get("author");
		Person author = new PersonParser().parse(authorMap);

		Map committerMap = (Map) commit.get("committer");
		Person committer = new PersonParser().parse(committerMap);

		String message = (String) commit.get("message");

		return new CommitInfo(sha, message, committer.getDate(), author, committer );
	}
}
