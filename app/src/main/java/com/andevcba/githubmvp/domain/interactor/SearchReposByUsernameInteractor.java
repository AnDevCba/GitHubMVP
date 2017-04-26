package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.repository.Repository;
import com.andevcba.githubmvp.data.repository.RepositoryFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Interactor to search for repos by a given username on GitHub API.
 *
 * @author lucas.nobile
 */
@Singleton
public class SearchReposByUsernameInteractor extends InteractorAdapter {

    private RepositoryFactory factory;

    @Inject
    public SearchReposByUsernameInteractor(RepositoryFactory factory) {
        this.factory = factory;
    }

    @Override
    public void execute(String username, ReposCallback callback) {
        Repository repository = factory.create(username);
        repository.searchReposByUsername(username, callback);
    }
}
