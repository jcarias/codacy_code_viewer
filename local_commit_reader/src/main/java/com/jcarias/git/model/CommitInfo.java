package com.jcarias.git.model;

import java.util.Date;

/**
 * Entity class to store commit information
 */
public class CommitInfo {
	private String sha;
	private String message;
	private Date date;
	private Person author;
	private Person committer;

	public CommitInfo(String sha, String message, Date date, Person author, Person committer) {
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

	public Date getDate() {
		return date;
	}

	public Person getAuthor() {
		return author;
	}

	public Person getCommitter() {
		return committer;
	}
}
