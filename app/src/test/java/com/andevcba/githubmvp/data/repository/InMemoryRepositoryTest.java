package com.andevcba.githubmvp.data.repository;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link InMemoryRepository}.
 *
 * @author lucas.nobile
 */
public class InMemoryRepositoryTest {

    private final static String USERNAME = "AnDevCba";
    private final static String NOT_IN_CACHE_USERNAME = "NotInCache";

    private final static Repo REPO1 = new Repo("repo1", "url1");
    private final static Repo REPO2 = new Repo("repo2", "url2");
    private static List<Repo> REPO_LIST = new ArrayList<>();

    private static TreeMap<String, List<Repo>> REPOS_BY_USERNAME_MAP;
    private static ReposByUsername reposByUsername;

    @Mock
    private ReposCache reposCache;

    @Mock
    private ReposCallback reposCallback;

    @InjectMocks
    private InMemoryRepository inMemoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Mocked repo list
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);

        // Mocked repos by username
        REPOS_BY_USERNAME_MAP = new TreeMap<>();
        REPOS_BY_USERNAME_MAP.put(USERNAME, REPO_LIST);

        reposByUsername = new ReposByUsername(REPOS_BY_USERNAME_MAP, true /* is cached */);
    }

    @After
    public void tearDown() throws Exception {
        REPO_LIST.clear();
    }

    @Test
    public void searchReposByUsername_in_cache_shouldInvokeReposCallback() {
        // Given a cache with repos by username
        reposCache.put(USERNAME, REPO_LIST);

        // When
        when(reposCache.isCached(USERNAME)).thenReturn(true);
        inMemoryRepository.searchReposByUsername(USERNAME, reposCallback);

        // Then
        verify(reposCallback).onResponse(any(ReposByUsername.class));
    }

    @Test
    public void searchReposByUsername_not_in_cache_shouldNotInvokeReposCallback() {
        // Given a cache with repos by username
        reposCache.put(USERNAME, REPO_LIST);

        // When
        when(reposCache.isCached(NOT_IN_CACHE_USERNAME)).thenReturn(false);
        inMemoryRepository.searchReposByUsername(NOT_IN_CACHE_USERNAME, reposCallback);

        // Then
        verify(reposCallback, times(0)).onResponse(any(ReposByUsername.class));
    }

    @Test
    public void saveReposByUsername_shouldAddInCache() {
        // Given a mocked cache with NO repos by username

        // When
        inMemoryRepository.saveReposByUsername(reposByUsername);

        // Then
        verify(reposCache, times(1)).put(USERNAME, REPO_LIST);
    }

    @Test
    public void loadAllRepos_shouldInvokeReposCallback() {
        // Given a cache with repos by username
        reposCache.put(USERNAME, REPO_LIST);

        // When
        inMemoryRepository.loadAllRepos(reposCallback);

        // Then
        verify(reposCallback).onResponse(any(ReposByUsername.class));
    }
}