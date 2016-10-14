package com.andevcba.githubmvp.data.net;

import java.util.ArrayList;
import java.util.List;

import com.andevcba.githubmvp.data.model.Repo;
import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;

/**
 * Mock {@link GitHubApiClient} for a successful response.
 *
 * @author lucas.nobile
 */
public class MockGitHubApiClient implements GitHubApiClient {

    private final static Repo REPO1 = new Repo("repo1", "url1");
    private final static Repo REPO2 = new Repo("repo2", "url2");
    private static List<Repo> REPO_LIST = new ArrayList<>();

    private final BehaviorDelegate<GitHubApiClient> delegate;

    public MockGitHubApiClient(BehaviorDelegate<GitHubApiClient> delegate) {
        this.delegate = delegate;

        // Mocked repo list
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);
    }

    @Override
    public Call<List<Repo>> searchReposByUsername(@Path("username") String username) {
        Call<List<Repo>> response = Calls.response(REPO_LIST);
        return delegate.returning(response).searchReposByUsername(username);
    }
}