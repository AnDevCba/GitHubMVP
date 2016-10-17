package com.andevcba.githubmvp.presentation.show_repos.presenter;

import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.repository.ReposCallback;
import com.andevcba.githubmvp.domain.interactor.LoadReposInteractor;
import com.andevcba.githubmvp.presentation.show_repos.ReposContract;
import com.andevcba.githubmvp.presentation.show_repos.model.RepoUI;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link ReposPresenter}.
 *
 * @author lucas.nobile
 */
public class ReposPresenterTest {

    private final static String USERNAME = "AnDevCba";
    private final static String REPO_NAME = "RepoName";
    private final static String ERROR_MESSAGE = "Error message";

    private final static Repo REPO1 = new Repo("repo1", "url1");
    private final static Repo REPO2 = new Repo("repo2", "url2");
    private static List<Repo> REPO_LIST = new ArrayList<>();

    private final static RepoUI REPO_UI1 = new RepoUI("repo1", "url1");
    private final static RepoUI REPO_UI2 = new RepoUI("repo2", "url2");
    private static List<RepoUI> REPO_UI_LIST = new ArrayList<>();

    private static TreeMap<String, List<Repo>> REPOS_BY_USERNAME_MAP;
    private static TreeMap<String, List<RepoUI>> REPOS_BY_USERNAME_UI_MAP;
    private static ReposByUsername model;
    private static ReposByUsernameUI uiModel;

    @Mock
    private ReposContract.View view;

    @Mock
    private LoadReposInteractor loadReposInteractor;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<ReposCallback> reposCallbackArgumentCaptor;

    @InjectMocks
    private ReposPresenter presenter;

    @Before
    public void setUp() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Mocked repo list
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);

        // Mocked repos by username
        REPOS_BY_USERNAME_MAP = new TreeMap<>();
        REPOS_BY_USERNAME_MAP.put(USERNAME, REPO_LIST);

        // Mocked repo list
        REPO_UI_LIST.add(REPO_UI1);
        REPO_UI_LIST.add(REPO_UI2);

        // Mocked repos by username
        REPOS_BY_USERNAME_UI_MAP = new TreeMap<>();
        REPOS_BY_USERNAME_UI_MAP.put(USERNAME, REPO_UI_LIST);
    }

    @After
    public void tearDown() {
        REPO_LIST.clear();
        REPOS_BY_USERNAME_MAP.clear();
        REPO_UI_LIST.clear();
        REPOS_BY_USERNAME_UI_MAP.clear();
    }

    @Test
    public void loadAllRepos_shouldShowRepos() {
        // Given a a stubbed model
        model = new ReposByUsername(REPOS_BY_USERNAME_MAP, true /* is cached */);

        // When
        presenter.loadAllRepos();

        // Callback is captured and invoked with stubbed repos by username
        verify(loadReposInteractor).execute(reposCallbackArgumentCaptor.capture());
        reposCallbackArgumentCaptor.getValue().onResponse(model);

        // Then
        ReposByUsernameUI uiModel = presenter.getUiModel();
        verify(view).showRepos(uiModel, true /* go to top*/);
    }

    @Test
    public void loadAllRepos_with_error_shouldShowError() {
        // Given an error

        // When
        presenter.loadAllRepos();

        // Callback is captured and invoked with stubbed repos by username
        verify(loadReposInteractor).execute(reposCallbackArgumentCaptor.capture());
        reposCallbackArgumentCaptor.getValue().onError(ERROR_MESSAGE);

        // Then
        verify(view).showError(ERROR_MESSAGE);
    }

    @Test
    public void goToAddReposScreen_shouldNavigateToAddReposScreen() {
        // When
        presenter.goToAddReposScreen();

        // Then
        verify(view).navigateToAddReposScreen();
    }

    @Test
    public void goToGitHubUsernamePage_shouldBrowseGitHubUsernamePage() {
        // Given a stubbed url
        String url = GitHubApiClient.BASE_URL + USERNAME;

        // When
        presenter.goToGitHubUsernamePage(USERNAME);

        // Then
        verify(view).browseGitHubUsernamePage(url);
    }

    @Test
    public void goToGitHubRepoPage_shouldBrowseGitHubRepoPage() {
        // Given a stubbed repo url
        String url = GitHubApiClient.BASE_URL + USERNAME + "/" + REPO_NAME;

        // When
        presenter.goToGitHubRepoPage(url);

        // Then
        verify(view).browseGitHubRepoPage(url);
    }

    @Test
    public void restoreStateAndShowRepos_with_uiModel_in_cache_shouldShowRepos() {
        // Given a stubbed uiModel
        uiModel = new ReposByUsernameUI(REPOS_BY_USERNAME_UI_MAP, true /* is cached*/);

        // When
        presenter.restoreStateAndShowRepos(uiModel);

        // Then
        verify(view).showRepos(uiModel, false /*not going to top*/);
    }

    @Test
    public void transformModelToUiModel_shouldTransformDataProperly() {
        // Given a stubbed model
        model = new ReposByUsername(REPOS_BY_USERNAME_MAP, true /* is cached */);

        // When
        ReposByUsernameUI localUiModel = presenter.transformModelToUiModel(model);

        // Then
        assertEquals(model.isCached(), localUiModel.isCached());
        assertEquals(model.getReposByUsername().firstKey(), localUiModel.getReposByUsername().firstKey());
        assertEquals(model.getReposByUsername().get(USERNAME).size(), localUiModel.getReposByUsername().get(USERNAME).size());
        assertEquals(model.getReposByUsername().get(USERNAME).get(0).getName(), localUiModel.getReposByUsername().get(USERNAME).get(0).getName());
        assertEquals(model.getReposByUsername().get(USERNAME).get(0).getUrl(), localUiModel.getReposByUsername().get(USERNAME).get(0).getUrl());
        assertEquals(model.getReposByUsername().get(USERNAME).get(1).getName(), localUiModel.getReposByUsername().get(USERNAME).get(1).getName());
        assertEquals(model.getReposByUsername().get(USERNAME).get(1).getUrl(), localUiModel.getReposByUsername().get(USERNAME).get(1).getUrl());
    }
}