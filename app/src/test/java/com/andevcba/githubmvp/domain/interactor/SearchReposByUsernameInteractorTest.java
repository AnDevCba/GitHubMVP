package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.net.MockGitHubApiClient;
import com.andevcba.githubmvp.data.repository.InMemoryRepository;
import com.andevcba.githubmvp.data.repository.NetworkRepository;
import com.andevcba.githubmvp.data.repository.Repository;
import com.andevcba.githubmvp.data.repository.RepositoryFactory;

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
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link SearchReposByUsernameInteractor}.
 *
 * @author lucas.nobile
 */
public class SearchReposByUsernameInteractorTest {

    private final static String USERNAME = "AnDevCba";
    private final static String NOT_IN_CACHE_USERNAME = "NotInCache";

    private final static Repo REPO1 = new Repo("repo1", "url1");
    private final static Repo REPO2 = new Repo("repo2", "url2");
    private static List<Repo> REPO_LIST = new ArrayList<>();

    private BehaviorDelegate<GitHubApiClient> delegate;

    @Mock
    private ReposCallback reposCallback;

    @Mock
    private ReposCache reposCache;

    @InjectMocks
    private RepositoryFactory repositoryFactory;

    @InjectMocks
    private SearchReposByUsernameInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://test.com").build();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit).build();
        delegate = mockRetrofit.create(GitHubApiClient.class);
    }

    @After
    public void tearDown() {
        REPO_LIST.clear();
    }

    @Test
    public void execute_with_username_not_in_cache_shouldRetrieveReposByUsernameFromNetwork() throws IOException {
        // Given a mocked GitHub API client
        GitHubApiClient mockGitHubApiClient = new MockGitHubApiClient(delegate);

        // When
        Repository repository = repositoryFactory.create(NOT_IN_CACHE_USERNAME);

        // NOT call to repository.searchReposByUsername(NOT_IN_CACHE_USERNAME, reposCallback);
        // but call mocked method :)
        Call<List<Repo>> reposCall = mockGitHubApiClient.searchReposByUsername(NOT_IN_CACHE_USERNAME);
        Response<List<Repo>> response = reposCall.execute();

        // Then
        assertThat(repository, instanceOf(NetworkRepository.class));
        assertTrue(response.isSuccessful());
        assertEquals(response.body().size(), 2);
        assertEquals(response.body().get(0).getName(), "repo1");
        assertEquals(response.body().get(0).getUrl(), "url1");
        assertEquals(response.body().get(1).getName(), "repo2");
        assertEquals(response.body().get(1).getUrl(), "url2");
    }

    @Test
    public void execute_with_cached_username_shouldRetrieveReposByUsernameFromInMemoryCache() {
        // Given
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);
        reposCache.put(USERNAME, REPO_LIST);

        // When
        when(reposCache.isCached(USERNAME)).thenReturn(true);
        Repository repository = repositoryFactory.create(USERNAME);
        repository.searchReposByUsername(USERNAME, reposCallback);

        // Then
        assertThat(repository, instanceOf(InMemoryRepository.class));
        verify(reposCallback).onResponse(any(ReposByUsername.class));
    }
}