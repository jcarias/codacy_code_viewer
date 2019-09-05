package com.jcarias.api.services;

import com.jcarias.api.entities.CommitsRequest;
import com.jcarias.api.helpers.MalformedRequestSyntaxException;
import com.jcarias.api.helpers.Partition;
import com.jcarias.api.helpers.UncaughtException;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Path("/commits")
public class TestService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestService() throws UncaughtException {

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
			//Open or Clone repository to read commit list
			RepoCommitExtractor extractor = new RepoCommitExtractor(url);
			Collection<CommitInfo> commits = extractor.getCommits();

			//Partition of results into chunks
			Partition<CommitInfo> commitInfoPartition = new Partition(new ArrayList(commits), commitsRequest.getPageSize());
			List<CommitInfo> commitsPage = commitInfoPartition.get(commitsRequest.getPage());

			//Conversion of the files
			Converter<Collection<CommitInfo>, JSONArray> converter = new CommitsInfoToJsonArray();
			JSONArray jsonArray = converter.convert(commitsPage);

			JSONObject responseData = new JSONObject();
			responseData.put("page", commitsRequest.getPage());
			responseData.put("totalCommits", commits.size());
			responseData.put("totalPages", commitInfoPartition.size());
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
}