package com.jcarias.api.services;

import com.jcarias.api.entities.RepoURL;
import com.jcarias.git.CommitInfo;
import com.jcarias.git.RepoCommitExtractor;
import com.jcarias.git.converters.CommitsInfoToJsonArray;
import com.jcarias.git.converters.Converter;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collection;

@Path("/commits")
public class TestService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestService() {

		RepoCommitExtractor extractor = null;
		try {
			extractor = new RepoCommitExtractor("https://github.com/naveenvemulapalli/test-jersey-rest-maven-tomcat.git");
			Collection<CommitInfo> commits = extractor.getCommits();
			Converter<Collection<CommitInfo>, JSONArray> converter = new CommitsInfoToJsonArray();
			JSONArray jsonArray = converter.convert(commits);

			return Response.ok(jsonArray.toString()).build();

		} catch (IOException e) {

			return Response.status(501).entity(e.getMessage())
					.type("text/plain").build();
		} catch (GitAPIException e) {
			Response.serverError().build();

			return Response.status(502).entity(e.getMessage())
					.type("text/plain").build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommitsFromRepo(RepoURL repoURL) {

		String url = repoURL.getUrl();
		try {
			//Open or Clone repository to read commit list
			RepoCommitExtractor extractor = new RepoCommitExtractor(url);
			Collection<CommitInfo> commits = extractor.getCommits();

			Converter<Collection<CommitInfo>, JSONArray> converter = new CommitsInfoToJsonArray();
			JSONArray jsonArray = converter.convert(commits);

			return Response.ok(jsonArray.toString()).build();
		} catch (IOException e) {
			return Response.status(501).entity(e.getMessage())
					.type("text/plain").build();
		} catch (GitAPIException e) {
			Response.serverError().build();
			return Response.status(502).entity(e.getMessage())
					.type("text/plain").build();
		}
	}
}