package com.jcarias.git;

import com.jcarias.git.converters.CommitsInfoToJsonArray;
import com.jcarias.git.converters.Converter;
import com.jcarias.git.model.CommitInfo;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONArray;

import java.io.IOException;
import java.util.Collection;

public class GetCommitList {
	public static void main(String[] args) throws IOException, GitAPIException {

		if(args.length < 1) {
			System.out.println("The repository URL parameter is requied.");
			return;
		}

		//Open or Clone repository to read commit list
		RepoCommitExtractor extractor = new RepoCommitExtractor(args[0], true);
		Collection<CommitInfo> commits = extractor.getCommits();

		//Convert results to JSON
		Converter<Collection<CommitInfo>, JSONArray > converter = new CommitsInfoToJsonArray();
		JSONArray jsonArray = converter.convert(commits);

		System.out.println(jsonArray.toString(2));
	}
}
