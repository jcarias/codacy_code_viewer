package com.jcarias.git.model;

import java.util.Date;

public class Person {

	private String name;
	private String email;
	private Date date;
	private String avatarUrl;

	public Person(String name, String email, Date date) {
		this.name = name;
		this.email = email;
		this.date = date;
	}

	public Person(String name, String email, Date date, String avatarUrl) {
		this(name, email, date);
		this.avatarUrl = avatarUrl;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Date getDate() {
		return date;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl() {
		return this.avatarUrl;
	}
}
