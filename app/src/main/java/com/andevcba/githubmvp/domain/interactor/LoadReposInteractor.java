package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.DependencyProvider;
import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.repository.Repository;

/**
 * Interactor to load cached repos.
 *
 * @author lucas.nobile
 */
public class LoadReposInteractor extends InteractorAdapter {

    private Repository repository;

    public LoadReposInteractor() {
        ReposCache reposCache = DependencyProvider.provideReposCache();
        repository = DependencyProvider.provideInMemoryRepository(reposCache);
    }

    @Override
    public void execute(ReposCallback callback) {
        repository.loadAllRepos(callback);
    }
}
