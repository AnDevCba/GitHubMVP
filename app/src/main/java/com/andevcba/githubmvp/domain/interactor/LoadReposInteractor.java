package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.di.InjectionName;
import com.andevcba.githubmvp.data.repository.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Interactor to load repos from cache (memory).
 *
 * @author lucas.nobile
 */
@Singleton
public class LoadReposInteractor extends InteractorAdapter {

    private Repository repository;

    @Inject
    public LoadReposInteractor(@Named(InjectionName.IN_MEMORY_NAME) Repository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(ReposCallback callback) {
        repository.loadAllRepos(callback);
    }
}
