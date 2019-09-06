package com.jcarias.codacy.github.helpers;

public class IncorrectHostException extends Exception{
	public IncorrectHostException(String errorMessage) {
		super(errorMessage);
	}
}
