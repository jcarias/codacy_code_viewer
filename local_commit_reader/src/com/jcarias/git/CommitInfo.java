package com.jcarias.git;

import java.util.Date;

/**
 * Entity class to store commit information
 */
public class CommitInfo {
	private String id;
	private String message;
	private Date date;
	private String authorName;

	/**
	 * Constructor
	 * @param id the commit Hash
	 * @param message The commit message
	 * @param date the commit date
	 * @param authorName the committer name;
	 */
	public CommitInfo(String id, String message, Date date, String authorName) {
		this.id = id;
		this.message = message;
		this.date = date;
		this.authorName = authorName;
	}

	public String getId() {
		return id;
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
