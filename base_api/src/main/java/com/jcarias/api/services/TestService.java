package com.jcarias.api.services;

import com.jcarias.api.entities.CommitsRequest;
import com.jcarias.api.entities.ErrorEntity;
import com.jcarias.api.helpers.MalformedRequestSyntaxException;
import com.jcarias.api.helpers.UncaughtException;
import com.jcarias.codacy.github.GitHubApiClient;
import com.jcarias.codacy.github.helpers.ConnectivityException;
import com.jcarias.codacy.github.helpers.IncorrectHostException;
import com.jcarias.git.RepoCommitExtractor;
import com.jcarias.git.converters.CommitsInfoToJsonArray;
import com.jcarias.git.converters.Converter;
import com.jcarias.git.model.CommitInfo;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path("/commits")
public class TestService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestService() throws UncaughtException {

		RepoCommitExtractor extractor = null;
		try {
			extractor = new RepoCommitExtractor("https://github.com/naveenvemulapalli/");
			Collection<CommitInfo> commits = extractor.getCommits();
			Converter<Collection<CommitInfo>, JSONArray> converter = new CommitsInfoToJsonArray();
			JSONArray jsonArray = converter.convert(commits);

			return Response.ok(jsonArray.toString()).build();

		} catch (IOException | GitAPIException e) {
			ErrorEntity errorEntity = new ErrorEntity(e.getMessage(), INTERNAL_SERVER_ERROR.getStatusCode());
			return Response.serverError().entity(errorEntity)
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Throwable t) {
			throw new UncaughtException();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommitsFromRepo(CommitsRequest commitsRequest) throws MalformedRequestSyntaxException {

		if (StringUtils.isBlank(commitsRequest.getUrl())) {
			throw new MalformedRequestSyntaxException("No repo URL was found in the request");
		}

		String url = commitsRequest.getUrl();
		try {

			//Fetch, Open or Clone repository to read commit list
			Collection<CommitInfo> commits = fetchCommits(url, commitsRequest.getPageSize(), commitsRequest.getLastCommitSha());

			//TODO: Add method to update or clone the local repository
			RepoCommitExtractor extractor = new RepoCommitExtractor(url);

			//Conversion of the files
			Converter<Collection<CommitInfo>, JSONArray> converter = new CommitsInfoToJsonArray();
			JSONArray jsonArray = converter.convert(commits);

			JSONObject responseData = new JSONObject();
			responseData.put("lastCommitSha", findLastCommitSha(commits));
			responseData.put("pageSize", commitsRequest.getPageSize());
			responseData.put("commits", jsonArray);

			return Response.ok(responseData.toString()).build();

		} catch (IOException e) {
			return Response.status(501).entity(e.getMessage())
					.type("text/plain").build();
		} catch (GitAPIException e) {
			Response.serverError().build();
			return Response.status(502).entity(e.getMessage())
					.type("text/plain").build();
		} catch (Throwable t) {
			return new UncaughtException().toResponse(t);
		}
	}

	private String findLastCommitSha(Collection<CommitInfo> commits) {
		final Iterator<CommitInfo> itr = commits.iterator();
		CommitInfo lastElement = itr.next();
		while (itr.hasNext()) {
			lastElement = itr.next();
		}
		return lastElement.getSha();
	}

	private Collection<CommitInfo> fetchCommits(String url, int pageSize, String lastSha) throws IOException, IncorrectHostException, GitAPIException {
		try {
			return new GitHubApiClient(new URL(url), pageSize, lastSha).fetchRepoCommits();
		} catch (ConnectivityException e) {
			return new RepoCommitExtractor(url, pageSize, lastSha).getCommits();
		}
	}
}