package com.jcarias.api.entities;

import java.io.Serializable;

public class CommitsRequest implements Serializable {

	private String url;
	private String lastCommitSha;
	private int pageSize;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLastCommitSha() {
		return lastCommitSha;
	}

	public void setLastCommitSha(String lastCommitSha) {
		this.lastCommitSha = lastCommitSha;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
