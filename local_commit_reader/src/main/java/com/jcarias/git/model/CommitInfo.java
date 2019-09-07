package com.jcarias.git.model;

/**
 * Entity class to store commit information
 */
public class CommitInfo {
	private String sha;
	private String message;
	private Long date;
	private Person author;
	private Person committer;

	public CommitInfo(String sha, String message, Long date, Person author, Person committer) {
		this.sha = sha;
		this.message = message;
		this.date = date;
		this.author = author;
		this.committer = committer;
	}

	public String getSha() {
		return sha;
	}

	public String getMessage() {
		return message;
	}

	public Long getDate() {
		return date;
	}

	public Person getAuthor() {
		return author;
	}

	public Person getCommitter() {
		return committer;
	}
}
