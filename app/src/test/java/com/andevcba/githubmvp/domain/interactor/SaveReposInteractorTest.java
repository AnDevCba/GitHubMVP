package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.repository.Repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.mockito.Mockito.verify;

/**
 * Unit test for {@link SaveReposInteractor}.
 *
 * @author lucas.nobile
 */
public class SaveReposInteractorTest {

    private final static String USERNAME = "AnDevCba";
    private final static Repo REPO1 = new Repo("repo1", "url1");
    private final static Repo REPO2 = new Repo("repo2", "url2");
    private static List<Repo> REPO_LIST = new ArrayList<>();

    private static TreeMap<String, List<Repo>> REPOS_BY_USERNAME_MAP;
    private static ReposByUsername reposByUsername;

    @Mock
    private Repository repository;

    @InjectMocks
    private SaveReposInteractor interactor;

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

    @Test
    public void execute_shouldSaveReposByUsername() {
        // When
        interactor.execute(reposByUsername);

        // Then
        verify(repository).saveReposByUsername(reposByUsername);
    }
}