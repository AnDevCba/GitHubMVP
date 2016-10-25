package com.andevcba.githubmvp.data.net;

import com.andevcba.githubmvp.data.DependencyProvider;
import com.andevcba.githubmvp.data.model.Repo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;
import retrofit2.mock.MockRetrofit;

/**
 * Mock {@link GitHubApiClient} for a successful response.
 *
 * @author lucas.nobile
 */
public class MockGitHubApiClient implements GitHubApiClient {

    private final Repo REPO1 = new Repo("repo1", "http://test.com");
    private final Repo REPO2 = new Repo("repo2", "http://test.com");
    private List<Repo> REPO_LIST = new ArrayList<>();

    private final BehaviorDelegate<GitHubApiClient> delegate;

    public MockGitHubApiClient() {

        Retrofit retrofit = DependencyProvider.provideRetrofit();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit).build();
        delegate = mockRetrofit.create(GitHubApiClient.class);

        // Mocked repo list
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);
    }

    @Override
    public Call<List<Repo>> searchReposByUsername(@Path("username") String username) {
        Response response = Response.success(REPO_LIST);
        return delegate.returning(Calls.response(response)).searchReposByUsername(username);
    }
}