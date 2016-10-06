package andevcba.com.githubmvp.data.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import andevcba.com.githubmvp.data.model.Repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link ReposCache} tests.
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

    @Mock
    private ReposCache reposCache;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Mocked repo list
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);

        // Mocked repos by username
        REPOS_BY_USERNAME.put(USERNAME, REPO_LIST);
    }

    @After
    public void tearDown() throws Exception {
        REPO_LIST.clear();
    }

    @Test
    public void put_shouldReturnAListOfRepos() {
        // Given (pre-condition(s)) a mocked username and repo list

        // When
        when(reposCache.put(USERNAME, REPO_LIST)).thenReturn(REPO_LIST);
        List<Repo> repos = putReposByUsername();

        // Then
        verify(reposCache, times(1)).put(USERNAME, REPO_LIST);
        assertTrue(!repos.isEmpty());
        assertEquals(repos.size(), 2);
        assertEquals(repos.get(0).getName(), "repo1");
        assertEquals(repos.get(0).getUrl(), "url1");
        assertEquals(repos.get(1).getName(), "repo2");
        assertEquals(repos.get(1).getUrl(), "url2");
    }

    @Test
    public void get_by_username_in_cache_shouldReturnAListOfRepos() {
        // Given a cache with repos by username
        putReposByUsername();

        // When
        when(reposCache.get(USERNAME)).thenReturn(REPO_LIST);
        List<Repo> repos = reposCache.get(USERNAME);

        // Then
        verify(reposCache, times(1)).get(USERNAME);
        assertTrue(!repos.isEmpty());
        assertEquals(repos.size(), 2);
        assertEquals(repos.get(0).getName(), "repo1");
        assertEquals(repos.get(0).getUrl(), "url1");
        assertEquals(repos.get(1).getName(), "repo2");
        assertEquals(repos.get(1).getUrl(), "url2");
    }

    @Test
    public void get_by_username_not_in_cache_shouldReturnAnEmptyListOfRepos() {
        // Given a cache with repos by username
        putReposByUsername();

        // When
        when(reposCache.get(NOT_IN_CACHE_USERNAME)).thenReturn(new ArrayList<Repo>());
        List<Repo> repos = reposCache.get(NOT_IN_CACHE_USERNAME);

        // Then
        verify(reposCache, times(1)).get(NOT_IN_CACHE_USERNAME);
        assertTrue(repos.isEmpty());
    }

    @Test
    public void getAll_shouldReturnAMapOfReposByUsername() {
        // Given a cache with repos by username
        putReposByUsername();

        // When
        when(reposCache.getAll()).thenReturn(REPOS_BY_USERNAME);
        TreeMap<String, List<Repo>> reposByUsername = reposCache.getAll();

        // Then
        verify(reposCache, times(1)).getAll();
        assertTrue(!reposByUsername.isEmpty());
        assertEquals(reposByUsername.size(), 1);
        assertEquals(reposByUsername.firstKey(), USERNAME);
        assertEquals(reposByUsername.get(USERNAME), REPO_LIST);
    }

    @Test
    public void isCached_shouldReturnTrueIfUsernameIsInCache() {
        // Given a cache with repos by username
        putReposByUsername();

        // When
        when(reposCache.isCached(USERNAME)).thenReturn(true);
        boolean cached = reposCache.isCached(USERNAME);

        // Then
        verify(reposCache, times(1)).isCached(USERNAME);
        assertTrue(cached);
    }

    @Test
    public void isCached_shouldReturnFalseIfUsernameIsNotInCache() {
        // Given a cache with repos by username
        putReposByUsername();

        // When
        when(reposCache.isCached(NOT_IN_CACHE_USERNAME)).thenReturn(false);
        boolean cached = reposCache.isCached(NOT_IN_CACHE_USERNAME);

        // Then
        verify(reposCache, times(1)).isCached(NOT_IN_CACHE_USERNAME);
        assertFalse(cached);
    }

    private List<Repo> putReposByUsername() {
        return reposCache.put(USERNAME, REPO_LIST);
    }
}