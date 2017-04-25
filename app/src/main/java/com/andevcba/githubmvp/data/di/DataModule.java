package com.andevcba.githubmvp.data.di;

import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.cache.ReposCacheImpl;
import com.andevcba.githubmvp.data.model.ErrorResponseHelper;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.repository.InMemoryRepository;
import com.andevcba.githubmvp.data.repository.NetworkRepository;
import com.andevcba.githubmvp.data.repository.Repository;

import java.util.List;
import java.util.TreeMap;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module class to provide dependencies for data layer.
 *
 * @author lucas.nobile
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    TreeMap<String, List<Repo>> provideTreeMap() {
        return new TreeMap<>();
    }

    @Provides
    ReposCache provideReposCache(TreeMap<String, List<Repo>> cachedRepos) {
        return new ReposCacheImpl(cachedRepos);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubApiClient.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    GitHubApiClient provideGitHubApiClient(Retrofit retrofit) {
        return retrofit.create(GitHubApiClient.class);
    }

    @Provides
    @Singleton
    ErrorResponseHelper provideErrorResponseHelper(Retrofit retrofit) {
        return new ErrorResponseHelper(retrofit);
    }

    @Provides
    @Named(InjectionName.IN_MEMORY_NAME)
    Repository provideInMemoryRepository(ReposCache reposCache) {
        return new InMemoryRepository(reposCache);
    }

    @Provides
    @Named(InjectionName.NETWORK_NAME)
    Repository provideNetworkRepository(GitHubApiClient gitHubApiClient, ErrorResponseHelper errorResponseHelper) {
        return new NetworkRepository(gitHubApiClient, errorResponseHelper);
    }
}
