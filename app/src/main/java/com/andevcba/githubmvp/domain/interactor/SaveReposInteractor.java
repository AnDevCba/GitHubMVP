package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.DependencyProvider;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.repository.ReposCache;
import com.andevcba.githubmvp.data.repository.ReposCallback;
import com.andevcba.githubmvp.data.repository.Repository;

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
    public void execute(ReposCallback callback) {
        repository.saveReposByUsername(reposByUsername);
        // FIXME Need to use the callback here?
//        callback.onResponse(reposByUsername);
    }
}
