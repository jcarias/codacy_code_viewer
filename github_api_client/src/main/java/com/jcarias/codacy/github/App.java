package com.jcarias.codacy.github;

import com.jcarias.codacy.github.helpers.IncorrectHostException;
import com.jcarias.git.model.CommitInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

public class App {
	public static void main(String[] args) throws MalformedURLException, IncorrectHostException {

		Collection<CommitInfo> commitInfos = new GitHubApiClient().fetchRepoCommits(new URL("https://github.com/jcarias/codacy_code_viewer.git"));

		for (CommitInfo commitInfo : commitInfos) {
			System.out.println(commitInfo.getSha());
			System.out.println(commitInfo.getDate());
			System.out.println(commitInfo.getMessage()+"\n");
		}
	}
}
