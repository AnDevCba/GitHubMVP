package com.andevcba.githubmvp.data.net;

import com.google.gson.Gson;

import java.util.List;

import com.andevcba.githubmvp.data.model.ErrorResponse;
import com.andevcba.githubmvp.data.model.Repo;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;

/**
 * Mock {@link GitHubApiClient} for a not found response.
 *
 * @author lucas.nobile
 */
public class MockNotFoundResponseGitHubApiClient implements GitHubApiClient {


    private final BehaviorDelegate<GitHubApiClient> delegate;

    public MockNotFoundResponseGitHubApiClient(BehaviorDelegate<GitHubApiClient> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<List<Repo>> searchReposByUsername(@Path("username") String username) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ApiConstants.NOT_FOUND_CODE);
        errorResponse.setMessage(ApiConstants.NOT_FOUND_MESSAGE);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(errorResponse);

        Response response = Response.error(ApiConstants.NOT_FOUND_CODE, ResponseBody.create(MediaType.parse(ApiConstants.JSON_TYPE), jsonStr));
        return delegate.returning(Calls.response(response)).searchReposByUsername(username);
    }
}