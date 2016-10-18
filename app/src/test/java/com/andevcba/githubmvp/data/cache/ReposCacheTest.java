package com.andevcba.githubmvp.data.cache;

import com.andevcba.githubmvp.data.model.Repo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link ReposCache}.
 *
 * @author lucas.nobile
 */
public class ReposCacheTest {

    private final static String USERNAME = "AnDevCba";
    private final static String NOT_IN_CACHE_USERNAME = "NotInCache";

    private final static Repo REPO1 = new Repo("repo1", "url1");
    private final static Repo REPO2 = new Repo("repo2", "url2");
    private static List<Repo> REPO_LIST = new ArrayList<>();

    private static TreeMap<String, List<Repo>> REPOS_BY_USERNAME = new TreeMap<>();

    // System Under Test (SUT)
    private ReposCacheImpl reposCache;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        reposCache = new ReposCacheImpl();

        // Stubbed repo list
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);

        // Stubbed repos by username
        REPOS_BY_USERNAME.put(USERNAME, REPO_LIST);
    }

    @After
    public void tearDown() throws Exception {
        REPO_LIST.clear();
    }

    @Test
    public void put_shouldSaveUsernameAndListOfRepos() {
        // Given (pre-condition(s)) a stubbed username and repo list

        // When
        reposCache.put(USERNAME, REPO_LIST);

        // Then
        // Expected, Actual
        assertTrue(!reposCache.cachedRepos.isEmpty());
        assertEquals(1, reposCache.cachedRepos.size());
        assertEquals(2, reposCache.cachedRepos.get(USERNAME).size());
        assertEquals("repo1", reposCache.cachedRepos.get(USERNAME).get(0).getName());
        assertEquals("url1", reposCache.cachedRepos.get(USERNAME).get(0).getUrl());
        assertEquals("repo2", reposCache.cachedRepos.get(USERNAME).get(1).getName());
        assertEquals("url2", reposCache.cachedRepos.get(USERNAME).get(1).getUrl());
    }

    @Test
    public void get_by_username_in_cache_shouldReturnAListOfRepos() {
        // Given a stubbed cache with repos by username
        reposCache.put(USERNAME, REPO_LIST);

        // When
        List<Repo> repos = reposCache.get(USERNAME);

        // Then
        assertTrue(!repos.isEmpty());
        assertEquals(2, repos.size());
        assertEquals(REPO1, repos.get(0));
        assertEquals(REPO2, repos.get(1));
    }

    @Test
    public void get_by_username_not_in_cache_shouldReturnNull() {
        // Given a stubbed cache with repos by username
        reposCache.put(USERNAME, REPO_LIST);

        // When
        List<Repo> repos = reposCache.get(NOT_IN_CACHE_USERNAME);

        // Then
        assertThat(repos, is(nullValue()));
    }

    @Test
    public void getAll_shouldReturnAMapOfReposByUsername() {
        // Given a stubbed cache with repos by username
        reposCache.put(USERNAME, REPO_LIST);

        // When
        TreeMap<String, List<Repo>> reposByUsername = reposCache.getAll();

        // Then
        assertTrue(!reposByUsername.isEmpty());
        assertEquals(1, reposByUsername.size());
        assertEquals(USERNAME, reposByUsername.firstKey());
        assertEquals(REPO_LIST, reposByUsername.get(USERNAME));
    }

    @Test
    public void isCached_shouldReturnTrueIfUsernameIsInCache() {
        // Given a stubbed cache with repos by username
        reposCache.put(USERNAME, REPO_LIST);

        // When
        boolean cached = reposCache.isCached(USERNAME);

        // Then
        assertTrue(cached);
    }

    @Test
    public void isCached_shouldReturnFalseIfUsernameIsNotInCache() {
        // Given a stubbed cache with repos by username
        reposCache.put(USERNAME, REPO_LIST);

        // When
        boolean cached = reposCache.isCached(NOT_IN_CACHE_USERNAME);

        // Then
        assertFalse(cached);
    }
}