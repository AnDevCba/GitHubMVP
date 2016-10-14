package com.andevcba.githubmvp.data.net;

import java.util.List;

import com.andevcba.githubmvp.data.model.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * GitHub API client.
 *
 * @author lucas.nobile
 */
public interface GitHubApiClient {

    String ENDPOINT = "https://api.github.com/";
    String BASE_URL = "https://github.com/";

    /**
     * List public repositories for the specified username.
     *
     * @param username - the username used to search repos.
     * @return a list of public repos.
     */
    @Headers("User-Agent: GitHubMVP-App")
    @GET("users/{username}/repos")
    Call<List<Repo>> searchReposByUsername(@Path("username") String username);
}
