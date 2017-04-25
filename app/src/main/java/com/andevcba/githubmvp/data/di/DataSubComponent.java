package com.andevcba.githubmvp.data.di;

import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.model.ErrorResponseHelper;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.repository.InMemoryRepository;
import com.andevcba.githubmvp.data.repository.NetworkRepository;
import com.andevcba.githubmvp.data.repository.RepositoryFactory;

import java.util.List;
import java.util.TreeMap;

import dagger.Subcomponent;
import retrofit2.Retrofit;

/**
 * Component for data layer.
 *
 * @author lucas.nobile
 */
@Subcomponent(modules = DataModule.class)
public interface DataSubComponent {
    TreeMap<String, List<Repo>> treeMap();

    ReposCache reposCache();

    Retrofit retrofit();

    GitHubApiClient gitHubApiClient();

    ErrorResponseHelper errorResponseHelper();

    RepositoryFactory repositoryFactory();

    InMemoryRepository inMemoryRepository();

    NetworkRepository networkRepository();
}
