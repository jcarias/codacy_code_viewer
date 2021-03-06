package com.jcarias.codacy.github;


import com.jcarias.codacy.github.helpers.ConnectivityException;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class GitHubApiClient {

	private URL repoUrl;
	private GitGubRepoParams repoParams;
	int pageSize;
	private String lastSha;

	public GitHubApiClient(URL repoUrl, int pageSize, String lastSha) throws IncorrectHostException {
		this.repoUrl = repoUrl;
		this.repoParams = new GitGubRepoUrlParser().parse(repoUrl);
		this.pageSize = pageSize;
		this.lastSha = lastSha;
	}


	public Collection<CommitInfo> fetchRepoCommits() throws ConnectivityException {
		Collection<CommitInfo> commits = new ArrayList<>();

		final String API_ADDRESS = "https://api.github.com/repos";
		final int TIMEOUT_1S = 1000; // 1 second
		final int TIMEOUT_5M = 300000; // 5 minutes


		try {
			Client client = ClientBuilder.newClient();
			client.property(ClientProperties.CONNECT_TIMEOUT, TIMEOUT_1S);
			client.property(ClientProperties.READ_TIMEOUT, TIMEOUT_5M);
			WebTarget webTarget = client.target(API_ADDRESS)
					.path(this.repoParams.getOwner())
					.path(this.repoParams.getRepository())
					.path("commits")
					.queryParam("per_page", this.pageSize)
					.queryParam("sha", this.lastSha);

			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.get();

			if (response.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new ConnectivityException(response.getStatusInfo().getReasonPhrase());
			}

			Object responseEntity = response.readEntity(Object.class);

			if (responseEntity instanceof ArrayList) {
				ArrayList commitsArray = (ArrayList) responseEntity;

				for (Object object : commitsArray) {
					if (object instanceof Map) {
						Map commitMap = (Map) object;
						CommitInfo commit = parseCommit(commitMap);
						commits.add(commit);
					}
				}
			}
		} catch (Exception stEx) {
			throw new ConnectivityException(stEx.getMessage());
		}
		return commits;
	}


	private CommitInfo parseCommit(Map commitMap) {
		String sha = (String) commitMap.get("sha");

		Map commit = (Map) commitMap.get("commit");

		Map commitAuthorMap = (Map) commit.get("author");
		Person author = new PersonParser().parse(commitAuthorMap);
		Map authorMap = (Map) commitMap.get("author");
		if (authorMap != null) {
			author.setAvatarUrl((String) authorMap.get("avatar_url"));
		}

		Map commitCommitterMap = (Map) commit.get("committer");
		Person committer = new PersonParser().parse(commitCommitterMap);
		Map committerMap = (Map) commitMap.get("committer");
		if (committerMap != null) {
			committer.setAvatarUrl((String) committerMap.get("avatar_url"));
		}

		String message = (String) commit.get("message");

		return new CommitInfo(sha, message, committer.getDate().getTime(), author, committer);
	}
}
