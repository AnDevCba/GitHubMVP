package andevcba.com.githubmvp.domain.interactor;

import andevcba.com.githubmvp.data.repository.ReposCallback;
import andevcba.com.githubmvp.data.repository.Repository;
import andevcba.com.githubmvp.data.repository.RepositoryFactory;

/**
 * Interactor to search for repos by a given username on GitHub API.
 *
 * @author lucas.nobile
 */
public class SearchReposByUsernameInteractor implements Interactor {

    private RepositoryFactory factory;
    private ReposCallback callback;
    private String username;

    public SearchReposByUsernameInteractor(String username, RepositoryFactory factory, ReposCallback callback) {
        this.username = username;
        this.factory = factory;
        this.callback = callback;
    }

    @Override
    public void execute() {
        Repository repository = factory.create(username);
        repository.searchReposByUsername(username, callback);
    }
}
