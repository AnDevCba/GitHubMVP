package addevcba.com.githubmvp.data.repository;

import addevcba.com.githubmvp.util.DependencyProvider;

/**
 * Creates a concrete {@link Repository} by a given username;
 *
 * @author lucas.nobile
 */
public class RepositoryFactory {

    private ReposCache reposCache;

    public RepositoryFactory(ReposCache reposCache) {
        this.reposCache = reposCache;
    }

    public Repository create(String username) {
        if (reposCache.isCached(username)) {
            return new InMemoryRepository(reposCache);
        }
        return new NetworkRepository(DependencyProvider.provideGitHubApiClient());
    }
}
