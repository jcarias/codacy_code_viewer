package com.jcarias.codacy.github.helpers;

import com.jcarias.codacy.github.model.GitGubRepoParams;

import java.net.URL;

public class GitGubRepoUrlParser implements Parser<GitGubRepoParams, URL> {

	@Override
	public GitGubRepoParams parse(URL sourceObject) throws IncorrectHostException {

		if (!"github.com".equals(sourceObject.getHost())) {
			throw new IncorrectHostException(String.format("Invalid host supplied '%s'. Expected host is 'github.com'.", sourceObject.getHost()));
		}

		String file = sourceObject.getFile();
		String[] fileSplitParts = file.split("/");

		String owner = fileSplitParts[fileSplitParts.length - 2];
		String repositoryFile = fileSplitParts[fileSplitParts.length - 1];

		if (repositoryFile.contains(".git")) {
			String repository = repositoryFile.split("\\.git")[0];
			return new GitGubRepoParams(owner, repository);
		} else {
			return new GitGubRepoParams(owner, repositoryFile);
		}

	}


}
