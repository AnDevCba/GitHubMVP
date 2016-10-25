package com.andevcba.githubmvp.data.repository;

import com.andevcba.githubmvp.data.model.ErrorResponse;
import com.andevcba.githubmvp.data.model.ErrorResponseHelper;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.net.ApiConstants;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.Calls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link NetworkRepository}.
 *
 * @author lucas.nobile
 */
public class NetworkRepositoryTest {

    private final static String USERNAME = "AnDevCba";
    private final static String NOT_VALID_USERNAME = "Na_Na_Na_Na_Na_Na_Na_Naa_Batman";

    private final Repo REPO1 = new Repo("repo1", "url1");
    private final Repo REPO2 = new Repo("repo2", "url2");
    private List<Repo> REPO_LIST = new ArrayList<>();

    @Mock
    private GitHubApiClient gitHubApiClient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);
    }

    @After
    public void tearDown() throws Exception {
        REPO_LIST.clear();
    }

    @Test
    public void searchReposByUsername_shouldReturnAListOfRepos() throws IOException {
        // Given
        Response stubbedResponse = Response.success(REPO_LIST);
        when(gitHubApiClient.searchReposByUsername(USERNAME)).thenReturn(Calls.response(stubbedResponse));

        // When
        Call<List<Repo>> reposCall = gitHubApiClient.searchReposByUsername(USERNAME);
        Response<List<Repo>> response = reposCall.execute();

        // Then
        assertTrue(response.isSuccessful());
        assertEquals(2, response.body().size());
        assertEquals("repo1", response.body().get(0).getName());
        assertEquals("url1", response.body().get(0).getUrl());
        assertEquals("repo2", response.body().get(1).getName());
        assertEquals("url2", response.body().get(1).getUrl());
    }

    @Test
    public void searchReposByUsername_not_valid_on_GitHub_shouldReturnNotFoundResponse() throws IOException {
        // Given
        ErrorResponse stubbedErrorResponse = new ErrorResponse();
        stubbedErrorResponse.setCode(ApiConstants.NOT_FOUND_CODE);
        stubbedErrorResponse.setMessage(ApiConstants.NOT_FOUND_MESSAGE);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(stubbedErrorResponse);

        Response stubbedResponse = Response.error(ApiConstants.NOT_FOUND_CODE, ResponseBody.create(MediaType.parse(ApiConstants.JSON_TYPE), jsonStr));
        when(gitHubApiClient.searchReposByUsername(NOT_VALID_USERNAME)).thenReturn(Calls.response(stubbedResponse));

        // When
        Call<List<Repo>> reposCall = gitHubApiClient.searchReposByUsername(NOT_VALID_USERNAME);
        Response<List<Repo>> response = reposCall.execute();
        ErrorResponse errorResponse = ErrorResponseHelper.parseError(response);

        // Then
        assertFalse(response.isSuccessful());
        assertEquals(404, errorResponse.getCode());
        assertEquals("Not Found", errorResponse.getMessage());
    }

    @Test(expected = IOException.class)
    public void searchReposByUsername_timeout_shouldReturnFailureResponse() throws IOException {
        // Given
        IOException exception = new IOException("Timeout!");
        Call<List<Repo>> stubbedResponse = Calls.failure(exception);
        when(gitHubApiClient.searchReposByUsername(USERNAME)).thenReturn(stubbedResponse);

        // When
        Call<List<Repo>> reposCall = gitHubApiClient.searchReposByUsername(USERNAME);
        Response<List<Repo>> response = reposCall.execute();

        // Then
        assertFalse(response.isSuccessful());
    }
}