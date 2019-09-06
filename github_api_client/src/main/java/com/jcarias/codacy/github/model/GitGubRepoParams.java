package com.jcarias.codacy.github.model;

public class GitGubRepoParams  {
	private String owner;
	private String repository;

	public GitGubRepoParams(String owner, String repository) {
		this.owner = owner;
		this.repository = repository;
	}

	public String getOwner() {
		return owner;
	}

	public String getRepository() {
		return repository;
	}
}
