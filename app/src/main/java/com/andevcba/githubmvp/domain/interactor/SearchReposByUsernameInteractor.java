package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.DependencyProvider;
import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.repository.Repository;
import com.andevcba.githubmvp.data.repository.RepositoryFactory;

/**
 * Interactor to search for repos by a given username on GitHub API.
 *
 * @author lucas.nobile
 */
public class SearchReposByUsernameInteractor extends InteractorAdapter {

    private String username;
    private RepositoryFactory factory;

    public SearchReposByUsernameInteractor() {
        ReposCache reposCache = DependencyProvider.provideReposCache();
        factory = DependencyProvider.provideRepositoryFactory(reposCache);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void execute(ReposCallback callback) {
        Repository repository = factory.create(username);
        repository.searchReposByUsername(username, callback);
    }
}
