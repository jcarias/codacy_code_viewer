package com.jcarias.git;

import com.jcarias.git.model.CommitInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

public class RepoCommitExtractor {
	private String repoURL;
	private File repoDir;
	private Repository repository;
	private Collection<CommitInfo> commits;


	/**
	 * Constructor
	 *
	 * @param repoURL remote repository URL
	 */
	public RepoCommitExtractor(String repoURL) throws IOException, GitAPIException {
		if (new UrlValidator().isValid(repoURL)) {
			this.repoURL = repoURL;
			this.repoDir = new File(String.format("%s%d%s", File.separator, repoURL.hashCode(), File.separator));
			this.commits = new ArrayList<>();

			try {
				repository = openLocalRepo();
			} catch (RepositoryNotFoundException rnfe) {
				System.out.println(rnfe.getMessage());
				repository = cloneRemoteRepo(this.repoURL);
			}
		} else {
			throw new MalformedURLException(String.format("repoURL '%s' is not a valid URL", repoURL));
		}
	}

	public Collection<CommitInfo> getCommits() throws IOException {
		if (this.commits.isEmpty()) {
			getAllCommits();
		}
		return commits;
	}

	private Repository openLocalRepo() throws IOException {
		File localRepoDir = new File(this.repoDir + File.separator+ ".git");
		Git git = Git.open(localRepoDir);
		return git.getRepository();
	}

	private Repository cloneRemoteRepo(String url) throws GitAPIException, IOException {
		FileUtils.deleteDirectory(this.repoDir);
		return Git.cloneRepository()
				.setURI(url)
				.setDirectory(this.repoDir)
				.call()
				.getRepository();
	}

	//Fetch All commits from a repository
	private void getAllCommits() throws IOException {
		commits = new ArrayList<>();

		Ref head = repository.exactRef("refs/heads/master");

		try (RevWalk walk = new RevWalk(repository)) {
			RevCommit firstCommit = walk.parseCommit(head.getObjectId());
			walk.markStart(firstCommit);

			for (RevCommit commit : walk) {
				CommitInfo commitInfo = new CommitInfo(
						commit.getId().getName(),
						commit.getShortMessage(),
						commit.getCommitterIdent().getWhen(),
						commit.getCommitterIdent().getName()
				);

				commits.add(commitInfo);
			}

			walk.dispose();
		}
	}

}
