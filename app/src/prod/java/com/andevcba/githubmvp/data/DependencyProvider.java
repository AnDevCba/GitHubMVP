package com.andevcba.githubmvp.data;

import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.cache.ReposCacheImpl;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.repository.InMemoryRepository;
import com.andevcba.githubmvp.data.repository.RepositoryFactory;
import com.andevcba.githubmvp.domain.interactor.LoadReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SaveReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SearchReposByUsernameInteractor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides dependencies.
 *gt
 * @author lucas.nobile
 */
public class DependencyProvider {

    private static ReposCacheImpl reposCache;
    private static Retrofit retrofit;
    private static GitHubApiClient gitHubApiClient;
    private static RepositoryFactory repositoryFactory;
    private static InMemoryRepository inMemoryRepository;
    private static SearchReposByUsernameInteractor searchReposByUsernameInteractor;
    private static SaveReposInteractor saveReposInteractor;
    private static LoadReposInteractor loadReposInteractor;

    public static ReposCacheImpl provideReposCache() {
        if (reposCache == null) {
            reposCache = new ReposCacheImpl();
        }
        return reposCache;
    }

    public static Retrofit provideRetrofit() {
        // Add interceptor to log request and response.
        // Use this for testing purpose only.
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.addInterceptor(new LoggingInterceptor());
//            OkHttpClient client = builder.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(GitHubApiClient.ENDPOINT)
//                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static GitHubApiClient provideGitHubApiClient() {
        if (gitHubApiClient == null) {
            gitHubApiClient = provideRetrofit().create(GitHubApiClient.class);
        }
        return gitHubApiClient;
    }

    public static RepositoryFactory provideRepositoryFactory(ReposCache reposCache) {
        if (repositoryFactory == null) {
            repositoryFactory = new RepositoryFactory(reposCache);
        }
        return repositoryFactory;
    }

    public static InMemoryRepository provideInMemoryRepository(ReposCache reposCache) {
        if (inMemoryRepository == null) {
            inMemoryRepository = new InMemoryRepository(reposCache);
        }
        return inMemoryRepository;
    }

    public static SearchReposByUsernameInteractor provideSearchReposByUsernameInteractor() {
        if (searchReposByUsernameInteractor == null) {
            searchReposByUsernameInteractor = new SearchReposByUsernameInteractor();
        }
        return searchReposByUsernameInteractor;
    }


    public static SaveReposInteractor provideSaveReposInteractor() {
        if (saveReposInteractor == null) {
            saveReposInteractor = new SaveReposInteractor();
        }
        return saveReposInteractor;
    }

    public static LoadReposInteractor provideLoadReposInteractor() {
        if (loadReposInteractor == null) {
            loadReposInteractor = new LoadReposInteractor();
        }
        return loadReposInteractor;
    }
}
