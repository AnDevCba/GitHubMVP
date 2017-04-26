package com.andevcba.githubmvp.data.repository;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.model.ErrorResponse;
import com.andevcba.githubmvp.data.model.ErrorResponseHelper;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;

import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Concrete implementation to load {@link Repo}s from network.
 *
 * @author lucas.nobile
 */
@Singleton
public class NetworkRepository extends RepositoryAdapter {

    private GitHubApiClient gitHubApiClient;
    private ErrorResponseHelper errorResponseHelper; // TODO Lazy loading

    @Inject
    public NetworkRepository(GitHubApiClient gitHubApiClient, ErrorResponseHelper errorResponseHelper) {
        this.gitHubApiClient = gitHubApiClient;
        this.errorResponseHelper = errorResponseHelper;
    }

    @Override
    public void searchReposByUsername(final String username, final ReposCallback callback) {
        Call<List<Repo>> call = gitHubApiClient.searchReposByUsername(username);
        call.enqueue(new retrofit2.Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if (response.isSuccessful() && !response.body().isEmpty()) {
                    TreeMap<String, List<Repo>> reposByUsername = new TreeMap<>();
                    reposByUsername.put(username, response.body());

                    ReposByUsername repoResponse = new ReposByUsername(reposByUsername, false /* is cached */);

                    callback.onResponse(repoResponse);
                } else {
                    // Error such as resource not found
                    ErrorResponse errorResponse = errorResponseHelper.parseError(response);
                    callback.onError(errorResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                // Error such as no internet connection
                callback.onError(t.getMessage());
            }
        });
    }
}
