package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.DependencyProvider;
import com.andevcba.githubmvp.data.repository.ReposCache;
import com.andevcba.githubmvp.data.repository.ReposCallback;
import com.andevcba.githubmvp.data.repository.Repository;

/**
 * Interactor to load cached repos.
 *
 * @author lucas.nobile
 */
public class LoadReposInteractor implements Interactor {

    private ReposCallback callback;
    private Repository repository;

    public LoadReposInteractor(ReposCallback callback) {
        this.callback = callback;
        ReposCache reposCache = DependencyProvider.provideReposCache();
        repository = DependencyProvider.provideInMemoryRepository(reposCache);
    }

    @Override
    public void execute() {
        repository.loadAllRepos(callback);
    }
}
