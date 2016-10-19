package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.DependencyProvider;
import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.repository.Repository;

/**
 * Interactor to save repos in cache (memory).
 *
 * @author lucas.nobile
 */
public class SaveReposInteractor extends InteractorAdapter {

    private ReposByUsername reposByUsername;
    private Repository repository;

    public SaveReposInteractor() {
        ReposCache reposCache = DependencyProvider.provideReposCache();
        repository = DependencyProvider.provideInMemoryRepository(reposCache);
    }

    public void setReposByUsername(ReposByUsername reposByUsername) {
        this.reposByUsername = reposByUsername;
    }

    @Override
    public void execute() {
        repository.saveReposByUsername(reposByUsername);
    }
}
