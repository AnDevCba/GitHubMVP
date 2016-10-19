package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.cache.ReposCacheImpl;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.repository.InMemoryRepository;
import com.andevcba.githubmvp.data.repository.NetworkRepository;
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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
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

    @Mock
    private ReposCallback reposCallback;

    @Mock
    private ReposCacheImpl reposCache;

    @InjectMocks
    private RepositoryFactory repositoryFactory;

    @InjectMocks
    private SearchReposByUsernameInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        REPO_LIST.clear();
    }

    @Test
    public void execute_with_username_not_in_cache_shouldRetrieveReposByUsernameFromNetwork() throws IOException {
        // Given a stubbed repos cache with username not in cache

        // When
        interactor.setUsername(NOT_IN_CACHE_USERNAME);
        interactor.execute(reposCallback);

        // Then
        assertThat(repositoryFactory.create(NOT_IN_CACHE_USERNAME), instanceOf(NetworkRepository.class));
    }

    @Test
    public void execute_with_cached_username_shouldRetrieveReposByUsernameFromInMemoryCache() {
        // Given a stubbed repos cache with username cached
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);
        reposCache.put(USERNAME, REPO_LIST);

        // When
        when(reposCache.isCached(USERNAME)).thenReturn(true);
        interactor.setUsername(USERNAME);
        interactor.execute(reposCallback);

        // Then
        assertThat(repositoryFactory.create(USERNAME), instanceOf(InMemoryRepository.class));
    }
}