package com.andevcba.githubmvp.data.repository;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.model.ErrorResponse;
import com.andevcba.githubmvp.data.model.ErrorResponseHelper;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.Calls;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link NetworkRepository}.
 *
 * @author lucas.nobile
 */
public class NetworkRepositoryTest {

    private final static String USERNAME = "AnDevCba";
    private final static String NOT_VALID_USERNAME = "Na_Na_Na_Na_Na_Na_Na_Naa_Batman";

    private final Repo REPO1 = new Repo("repo1", "url1");
    private final Repo REPO2 = new Repo("repo2", "url2");
    private List<Repo> REPO_LIST = new ArrayList<>();

    private List<Repo> EMPTY_REPO_LIST = new ArrayList<>();

    @Mock
    private ErrorResponseHelper errorResponseHelper;

    @Mock
    private GitHubApiClient gitHubApiClient;

    @Mock
    private ReposCallback reposCallback;

    @InjectMocks
    private NetworkRepository networkRepository;

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
        Call<List<Repo>> stubbedCallResponse = Calls.response(stubbedResponse);
        when(gitHubApiClient.searchReposByUsername(USERNAME)).thenReturn(stubbedCallResponse);

        // When
        networkRepository.searchReposByUsername(USERNAME, reposCallback);

        // Then
        verify(reposCallback).onResponse(any(ReposByUsername.class));
    }

    @Test
    public void searchReposByUsername_not_valid_on_GitHub_shouldReturnNotFoundResponse() throws IOException {
        // Given
        Response stubbedResponse = Response.success(EMPTY_REPO_LIST);
        Call<List<Repo>> stubbedCallResponse = Calls.response(stubbedResponse);
        when(gitHubApiClient.searchReposByUsername(NOT_VALID_USERNAME)).thenReturn(stubbedCallResponse);

        ErrorResponse mockErrorResponse = mock(ErrorResponse.class);
        when(errorResponseHelper.parseError(any(Response.class))).thenReturn(mockErrorResponse);

        // When
        networkRepository.searchReposByUsername(NOT_VALID_USERNAME, reposCallback);

        // Then
        verify(reposCallback).onError(anyString());
    }

    @Test
    public void searchReposByUsername_timeout_shouldReturnFailureResponse() throws IOException {
        // Given
        IOException exception = new IOException("Timeout!");
        Call<List<Repo>> stubbedResponse = Calls.failure(exception);
        when(gitHubApiClient.searchReposByUsername(USERNAME)).thenReturn(stubbedResponse);

        // When
        networkRepository.searchReposByUsername(USERNAME, reposCallback);

        // Then
        verify(reposCallback).onError(anyString());
    }
}