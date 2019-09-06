package com.jcarias.codacy.github.helpers;

public class TimeoutException extends Exception{
	public TimeoutException(String errorMessage) {
		super(errorMessage);
	}
}

