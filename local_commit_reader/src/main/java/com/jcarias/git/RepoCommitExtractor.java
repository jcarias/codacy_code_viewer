package com.jcarias.git;

import com.jcarias.git.model.CommitInfo;
import com.jcarias.git.model.Person;
import com.jcarias.git.model.PersonJGit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.StringUtils;

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

	int pageSize;
	private String lastSha;


	/**
	 * Constructor
	 *
	 * @param repoURL remote repository URL
	 */
	public RepoCommitExtractor(String repoURL, boolean isPullNeeded) throws IOException, GitAPIException {
		if (new UrlValidator().isValid(repoURL)) {
			this.repoURL = repoURL;
			this.repoDir = new File(buildRepoDirName(repoURL));
			this.commits = new ArrayList<>();

			try {
				repository = openLocalRepo(isPullNeeded);
			} catch (RepositoryNotFoundException rnfe) {
				System.out.println(rnfe.getMessage());
				repository = cloneRemoteRepo(this.repoURL);
			}
		} else {
			throw new MalformedURLException(String.format("repoURL '%s' is not a valid URL", repoURL));
		}
	}

	private static String buildRepoDirName(String repoURL) {
		String baseDir = System.getenv("CCV_REPO_DIR");
		if (StringUtils.isEmptyOrNull(baseDir)) {
			baseDir = System.getProperty("user.dir");
		}

		return String.format("%s%s%s", baseDir, File.separator, repoURL.hashCode(), File.separator);
	}

	public RepoCommitExtractor(String repoURL, int pageSize, String lastSha) throws IOException, GitAPIException {
		this(repoURL, StringUtils.isEmptyOrNull(lastSha));
		this.pageSize = pageSize;
		this.lastSha = lastSha;
	}

	public Collection<CommitInfo> getCommits() throws IOException {
		if (this.commits.isEmpty()) {
			getAllCommits();
		}
		return commits;
	}

	private Repository openLocalRepo(boolean isPullNeeded) throws IOException {

		File localRepoDir = new File(this.repoDir + File.separator + ".git");
		Git git = Git.open(localRepoDir);

		if (isPullNeeded) {
			try {
				git.pull().call();
			} catch (GitAPIException e) {
				e.printStackTrace();
			}
		}

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

			ObjectId objectId = head.getObjectId();
			if (this.lastSha != null)
				objectId = repository.resolve(this.lastSha);

			RevCommit firstCommit = walk.parseCommit(objectId);
			walk.markStart(firstCommit);


			for (RevCommit commit : walk) {
				Person committer = new PersonJGit(commit.getCommitterIdent());
				Person author = new PersonJGit(commit.getAuthorIdent());

				CommitInfo commitInfo = new CommitInfo(
						commit.getId().getName(),
						commit.getShortMessage(),
						commit.getCommitterIdent().getWhen().getTime(),
						committer,
						author
				);

				commits.add(commitInfo);

				if (commits.size() == this.pageSize)
					break;
			}

			walk.dispose();
		}
		repository.close();
	}

	public void dispose(){
		this.repository.close();
	}

}
