package addevcba.com.githubmvp.data.repository;

import java.util.List;
import java.util.TreeMap;

import addevcba.com.githubmvp.data.net.GitHubApiClient;
import addevcba.com.githubmvp.data.model.Repo;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Concrete implementation to load {@link Repo}s from network.
 *
 * @author lucas.nobile
 */
public class NetworkRepository implements Repository {

    private GitHubApiClient apiClient;

    public NetworkRepository(GitHubApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public void searchReposByUsername(final String username, final ReposCallback callback) {
        Call<List<Repo>> call = apiClient.searchReposByUsername(username);
        call.enqueue(new retrofit2.Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if (response.isSuccessful() && !response.body().isEmpty()) {
                    TreeMap<String, List<Repo>> repos = new TreeMap<>();
                    repos.put(username, response.body());
                    callback.onResponse(repos);
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void loadReposByUsername(ReposCallback callback) {
        throw new UnsupportedOperationException("Invalid operation!");
    }
}
