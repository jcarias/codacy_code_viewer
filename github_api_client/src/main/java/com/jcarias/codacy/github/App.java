package com.jcarias.codacy.github;

import java.net.MalformedURLException;
import java.net.URL;

public class App {
	public static void main(String[] args) throws MalformedURLException {

		new JerseyClientGet().fetchRepoCommits(new URL("https://github.com/jcarias/Trabalho-SSS-MEI-2015"));

	}
}
