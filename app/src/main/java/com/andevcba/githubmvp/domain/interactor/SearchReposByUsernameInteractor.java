package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.DependencyProvider;
import com.andevcba.githubmvp.data.repository.ReposCache;
import com.andevcba.githubmvp.data.repository.ReposCallback;
import com.andevcba.githubmvp.data.repository.Repository;
import com.andevcba.githubmvp.data.repository.RepositoryFactory;

/**
 * Interactor to search for repos by a given username on GitHub API.
 *
 * @author lucas.nobile
 */
public class SearchReposByUsernameInteractor implements Interactor {

    private String username;
    private RepositoryFactory factory;
    private ReposCallback callback;

    public SearchReposByUsernameInteractor(ReposCallback callback) {
        ReposCache reposCache = DependencyProvider.provideReposCache();
        factory = DependencyProvider.provideRepositoryFactory(reposCache);
        this.callback = callback;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void execute() {
        Repository repository = factory.create(username);
        repository.searchReposByUsername(username, callback);
    }
}
