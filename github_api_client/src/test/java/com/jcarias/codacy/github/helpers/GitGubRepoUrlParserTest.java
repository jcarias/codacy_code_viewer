package com.jcarias.codacy.github.helpers;

import com.jcarias.codacy.github.model.GitGubRepoParams;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;

public class GitGubRepoUrlParserTest {

	@Test
	public void parseUrlOk1() throws Exception {
		URL url = new URL("https://github.com/jcarias/codacy_code_viewer.git");
		GitGubRepoParams params = new GitGubRepoUrlParser().parse(url);

		Assert.assertThat(params.getOwner(), is("jcarias"));
		Assert.assertThat(params.getRepository(), is("codacy_code_viewer"));
	}

	@Test
	public void parseUrlOk2() throws Exception {
		URL url = new URL("https://github.com/jcarias/codacy_code_viewer");
		GitGubRepoParams params = new GitGubRepoUrlParser().parse(url);

		Assert.assertThat(params.getOwner(), is("jcarias"));
		Assert.assertThat(params.getRepository(), is("codacy_code_viewer"));
	}

	@Test(expected = IncorrectHostException.class)
	public void parseUrlNOk() throws Exception {
		URL url = new URL("https://test.com/jcarias/codacy_code_viewer");
		GitGubRepoParams params = new GitGubRepoUrlParser().parse(url);
	}

	@Test(expected = MalformedURLException.class)
	public void parseUrlNOk2() throws Exception {
		URL url = new URL("hello world");
		GitGubRepoParams params = new GitGubRepoUrlParser().parse(url);
	}
}