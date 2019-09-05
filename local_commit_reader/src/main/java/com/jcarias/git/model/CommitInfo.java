package com.jcarias.git.model;

import java.util.Date;

/**
 * Entity class to store commit information
 */
public class CommitInfo {
	private String sha;
	private String message;
	private Date date;
	private String authorName;

	/**
	 * Constructor
	 * @param sha the commit Hash
	 * @param message The commit message
	 * @param date the commit date
	 * @param authorName the committer name;
	 */
	public CommitInfo(String sha, String message, Date date, String authorName) {
		this.sha = sha;
		this.message = message;
		this.date = date;
		this.authorName = authorName;
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

	public String getAuthorName() {
		return authorName;
	}
}
