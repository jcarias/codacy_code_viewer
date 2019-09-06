package com.jcarias.codacy.github;

import com.jcarias.codacy.github.helpers.ConnectivityException;
import com.jcarias.codacy.github.helpers.IncorrectHostException;
import com.jcarias.git.model.CommitInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

public class App {
	public static void main(String[] args) throws MalformedURLException, IncorrectHostException, ConnectivityException {

		URL url = new URL("https://github.com/jcarias/codacy_code_viewer.git");
		Collection<CommitInfo> commitInfos = new GitHubApiClient(url).fetchRepoCommits();

		for (CommitInfo commitInfo : commitInfos) {
			System.out.println(commitInfo.getSha());
			System.out.println(commitInfo.getDate());
			System.out.println(commitInfo.getMessage()+"\n");
		}
	}
}
