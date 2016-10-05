package andevcba.com.githubmvp.domain.interactor;

import andevcba.com.githubmvp.data.DependencyProvider;
import andevcba.com.githubmvp.data.repository.ReposCache;
import andevcba.com.githubmvp.data.repository.ReposCallback;
import andevcba.com.githubmvp.data.repository.Repository;

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
