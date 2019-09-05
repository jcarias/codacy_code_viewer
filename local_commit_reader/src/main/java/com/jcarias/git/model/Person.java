package com.jcarias.git.model;

import java.util.Date;

public class Person {

	private String name;
	private String email;
	private Date date;

	public Person(String name, String email, Date date) {
		this.name = name;
		this.email = email;
		this.date = date;
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
}
