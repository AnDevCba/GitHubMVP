package andevcba.com.githubmvp.data;

import andevcba.com.githubmvp.data.net.GitHubApiClient;
import andevcba.com.githubmvp.data.repository.InMemoryRepository;
import andevcba.com.githubmvp.data.repository.ReposCache;
import andevcba.com.githubmvp.data.repository.ReposCacheImpl;
import andevcba.com.githubmvp.data.repository.Repository;
import andevcba.com.githubmvp.data.repository.RepositoryFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides dependencies.
 *
 * @author lucas.nobile
 */
public class DependencyProvider {

    private static ReposCache reposCache;
    private static GitHubApiClient gitHubApiClient;
    private static RepositoryFactory repositoryFactory;
    private static Repository inMemoryRepository;

    public static ReposCache provideReposCache() {
        if (reposCache == null) {
            reposCache = new ReposCacheImpl();
        }
        return reposCache;
    }

    public static GitHubApiClient provideGitHubApiClient() {
        if (gitHubApiClient == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GitHubApiClient.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            gitHubApiClient = retrofit.create(GitHubApiClient.class);
        }
        return gitHubApiClient;
    }

    public static RepositoryFactory provideRepositoryFactory(ReposCache reposCache) {
        if (repositoryFactory == null) {
            repositoryFactory = new RepositoryFactory(reposCache);
        }
        return repositoryFactory;
    }

    public static Repository provideInMemoryRepository(ReposCache reposCache) {
        if (inMemoryRepository == null) {
            inMemoryRepository = new InMemoryRepository(reposCache);
        }
        return inMemoryRepository;
    }
}
