package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.di.InjectionName;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.repository.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Interactor to save repos in cache (memory).
 *
 * @author lucas.nobile
 */
@Singleton
public class SaveReposInteractor extends InteractorAdapter {

    private Repository repository;

    @Inject
    public SaveReposInteractor(@Named(InjectionName.IN_MEMORY_NAME) Repository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(ReposByUsername reposByUsername) {
        repository.saveReposByUsername(reposByUsername);
    }
}
