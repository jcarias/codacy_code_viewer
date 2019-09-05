package com.jcarias.git.model;

import org.eclipse.jgit.lib.PersonIdent;

import java.util.Date;

public class PersonJGit extends Person {

	public PersonJGit(String name, String email, Date date) {
		super(name, email, date);
	}

	public PersonJGit(PersonIdent ident){
		super(ident.getName(), ident.getEmailAddress(), ident.getWhen());
	}
}
