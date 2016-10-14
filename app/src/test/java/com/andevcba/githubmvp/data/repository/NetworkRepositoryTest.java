package com.andevcba.githubmvp.data.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import com.andevcba.githubmvp.data.model.ErrorResponse;
import com.andevcba.githubmvp.data.model.ErrorResponseHelper;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.net.MockFailureResponseGitHubApiClient;
import com.andevcba.githubmvp.data.net.MockGitHubApiClient;
import com.andevcba.githubmvp.data.net.MockNotFoundResponseGitHubApiClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link NetworkRepository}.
 *
 * @author lucas.nobile
 */
public class NetworkRepositoryTest {

    private final static String USERNAME = "AnDevCba";
    private final static String NOT_VALID_USERNAME = "Na_Na_Na_Na_Na_Na_Na_Naa_Batman";

    private BehaviorDelegate<GitHubApiClient> delegate;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://test.com").build();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit).build();
        delegate = mockRetrofit.create(GitHubApiClient.class);
    }

    @After
    public void tearDown() throws Exception {
        // No-op
    }

    @Test
    public void searchReposByUsername_shouldReturnAListOfRepos() throws IOException {
        // Given a mocked GitHub API client
        GitHubApiClient mockGitHubApiClient = new MockGitHubApiClient(delegate);

        // When
        Call<List<Repo>> reposCall = mockGitHubApiClient.searchReposByUsername(USERNAME);
        Response<List<Repo>> response = reposCall.execute();

        // Then
        assertTrue(response.isSuccessful());
        assertEquals(response.body().size(), 2);
        assertEquals(response.body().get(0).getName(), "repo1");
        assertEquals(response.body().get(0).getUrl(), "url1");
        assertEquals(response.body().get(1).getName(), "repo2");
        assertEquals(response.body().get(1).getUrl(), "url2");
    }

    @Test
    public void searchReposByUsername_not_valid_on_GitHub_shouldReturnNotFoundResponse() throws IOException {
        // Given a mocked GitHub API client for not found response
        GitHubApiClient mockGitHubApiClient = new MockNotFoundResponseGitHubApiClient(delegate);

        // When
        Call<List<Repo>> reposCall = mockGitHubApiClient.searchReposByUsername(NOT_VALID_USERNAME);
        Response<List<Repo>> response = reposCall.execute();
        ErrorResponse errorResponse = ErrorResponseHelper.parseError(response);

        // Then
        assertFalse(response.isSuccessful());
        assertEquals(errorResponse.getCode(), 404);
        assertEquals(errorResponse.getMessage(), "Not Found");
    }

    @Test(expected = IOException.class)
    public void searchReposByUsername_shouldReturnFailureResponse() throws IOException {
        // Given a mocked GitHub API client for failure response
        GitHubApiClient mockGitHubApiClient = new MockFailureResponseGitHubApiClient(delegate);

        // When
        Call<List<Repo>> reposCall = mockGitHubApiClient.searchReposByUsername(USERNAME);
        Response<List<Repo>> response = reposCall.execute();

        // Then
        assertFalse(response.isSuccessful());
    }
}