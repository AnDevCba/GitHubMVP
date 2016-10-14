package andevcba.com.githubmvp.data.net;

import java.io.IOException;
import java.util.List;

import andevcba.com.githubmvp.data.model.Repo;
import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;

/**
 * Mock {@link GitHubApiClient} for a fail response.
 *
 * @author lucas.nobile
 */
public class MockFailureResponseGitHubApiClient implements GitHubApiClient {

    private final IOException mockFailure = new IOException("Timeout!");
    private final BehaviorDelegate<GitHubApiClient> delegate;

    public MockFailureResponseGitHubApiClient(BehaviorDelegate<GitHubApiClient> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<List<Repo>> searchReposByUsername(@Path("username") String username) {
        Call<List<Repo>> response = Calls.failure(mockFailure);
        return delegate.returning(response).searchReposByUsername(username);
    }
}