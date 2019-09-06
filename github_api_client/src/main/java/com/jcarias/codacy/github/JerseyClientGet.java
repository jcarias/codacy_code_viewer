package com.jcarias.codacy.github;


import com.jcarias.codacy.github.helpers.PersonParser;
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

public class JerseyClientGet {

	private static final String API_ADDRESS = "https://api.github.com/repos";

	private Map<String, String> processRepoURL(URL repoUrl) {
		Map<String, String> parts = new HashMap<>();

		String path = repoUrl.getFile();

		return parts;
	}

	public Collection<CommitInfo> fetchRepoCommits(URL repoUrl) {
		Client client = ClientBuilder.newClient();
		client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
		client.property(ClientProperties.READ_TIMEOUT,    5000);
		String owner = "jcarias";
		String repository = "Trabalho-SSS-MEI-2015";
		WebTarget webTarget = client.target("https://api.github.com/repos").path(owner).path(repository).path("commits");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		System.out.println(response.getStatus());
		Object responseEntity = response.readEntity(Object.class);

		Collection<CommitInfo> commits = new ArrayList<>();

		if (responseEntity instanceof ArrayList) {
			ArrayList commitsArray = (ArrayList) responseEntity;

			for (Object o : commitsArray) {
				if (o instanceof LinkedHashMap) {
					LinkedHashMap commitMap = (LinkedHashMap) o;
					String sha = (String) commitMap.get("sha");
					System.out.println(sha);

					CommitInfo commit = parseCommit(sha, (LinkedHashMap) commitMap.get("commit"));
					commits.add(commit);
				}
			}
		}

		return commits;
	}


	private CommitInfo parseCommit(String sha, LinkedHashMap commit) {
		
		LinkedHashMap authorMap = (LinkedHashMap) commit.get("author");
		Person author = new PersonParser().parse(authorMap);

		LinkedHashMap committerMap = (LinkedHashMap) commit.get("committer");
		Person committer = new PersonParser().parse(committerMap);

		String message = (String) commit.get("message");

		return new CommitInfo(sha, message, committer.getDate(), author, committer );
	}
}
