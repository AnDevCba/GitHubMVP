package andevcba.com.githubmvp.domain.interactor;

import andevcba.com.githubmvp.data.DependencyProvider;
import andevcba.com.githubmvp.data.model.ReposByUsername;
import andevcba.com.githubmvp.data.repository.ReposCache;
import andevcba.com.githubmvp.data.repository.Repository;

/**
 * Interactor to save repos in cache (memory).
 *
 * @author lucas.nobile
 */
public class SaveReposInteractor implements Interactor {

    private ReposByUsername reposByUsername;
    private Repository repository;

    public SaveReposInteractor() {
        ReposCache reposCache = DependencyProvider.provideReposCache();
        repository = DependencyProvider.provideInMemoryRepository(reposCache);
    }


    public ReposByUsername getReposByUsername() {
        return reposByUsername;
    }

    public void setReposByUsername(ReposByUsername reposByUsername) {
        this.reposByUsername = reposByUsername;
    }

    @Override
    public void execute() {
        repository.saveReposByUsername(reposByUsername);
    }
}
