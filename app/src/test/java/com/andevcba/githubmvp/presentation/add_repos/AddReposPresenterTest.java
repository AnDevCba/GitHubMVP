package com.andevcba.githubmvp.presentation.add_repos;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.domain.interactor.SaveReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SearchReposByUsernameInteractor;
import com.andevcba.githubmvp.presentation.show_repos.model.RepoUI;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link AddReposPresenter}.
 *
 * @author lucas.nobile
 */
public class AddReposPresenterTest {

    private final static String EMPTY_USERNAME = "";
    private final static String USERNAME = "andevcba";
    private final static String NOT_IN_CACHE_USERNAME = "no_in_cache";
    private final static String NOT_VALID_USERNAME = "na_na_na_na_na_na_na_naa_batman";
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
    private AddReposContract.View view;

    @Mock
    private SearchReposByUsernameInteractor searchReposByUsernameInteractor;

    @Mock
    private SaveReposInteractor saveReposInteractor;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<ReposCallback> reposCallbackArgumentCaptor;

    @InjectMocks
    private AddReposPresenter presenter;

    @Before
    public void setUp() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Stubbed repo list
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);

        // Stubbed repos by username
        REPOS_BY_USERNAME_MAP = new TreeMap<>();
        REPOS_BY_USERNAME_MAP.put(USERNAME, REPO_LIST);

        // Stubbed repo UI list
        REPO_UI_LIST.add(REPO_UI1);
        REPO_UI_LIST.add(REPO_UI2);

        // Stubbed repos by username UI model
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
    public void searchReposByUsername_with_empty_username_shouldShowEmptyUsernameError() {
        // Given an empty username

        // When
        presenter.searchReposByUsername(EMPTY_USERNAME);

        // Then
        verify(view).showEmptyUsernameError();
    }

    @Test
    public void searchReposByUsername_with_not_valid_username_shouldShowError() {
        // Given a not valid username

        // When
        presenter.searchReposByUsername(NOT_VALID_USERNAME);

        // Callback is captured and invoked with stubbed repos by username
        verify(searchReposByUsernameInteractor).execute(eq(NOT_VALID_USERNAME), reposCallbackArgumentCaptor.capture());
        reposCallbackArgumentCaptor.getValue().onError(ERROR_MESSAGE);

        // Then
        verify(view).showError(ERROR_MESSAGE);
    }

    @Test
    public void searchReposByUsername_with_username_not_in_cache_shouldShowRepos() {
        // Given a stubbed model
        model = new ReposByUsername(REPOS_BY_USERNAME_MAP, false /* is not cached */);

        // When
        presenter.searchReposByUsername(NOT_IN_CACHE_USERNAME);

        // Callback is captured and invoked with stubbed repos by username
        verify(searchReposByUsernameInteractor).execute(eq(NOT_IN_CACHE_USERNAME), reposCallbackArgumentCaptor.capture());
        reposCallbackArgumentCaptor.getValue().onResponse(model);

        // Then
        // progress indicator is hidden
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).showProgressBar(true);
        inOrder.verify(view).hideSoftKeyboard();
        inOrder.verify(view).showProgressBar(false);
        // and repos are shown in UI
        ReposByUsernameUI reposByUsernameUI = presenter.getUiModel();
        verify(view).showRepos(reposByUsernameUI);
        verify(view).showSaveReposButton(true);
    }

    @Test
    public void searchReposByUsername_with_username_in_cache_shouldShowRepos() {
        // Given a stubbed model
        model = new ReposByUsername(REPOS_BY_USERNAME_MAP, true /* is cached */);

        // When
        presenter.searchReposByUsername(USERNAME);

        // Callback is captured and invoked with stubbed repos by username
        verify(searchReposByUsernameInteractor).execute(eq(USERNAME), reposCallbackArgumentCaptor.capture());
        reposCallbackArgumentCaptor.getValue().onResponse(model);

        // Then
        // progress indicator is hidden
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).showProgressBar(true);
        inOrder.verify(view).hideSoftKeyboard();
        inOrder.verify(view).showProgressBar(false);
        // and repos are shown in UI
        ReposByUsernameUI uiModel = presenter.getUiModel();
        verify(view).showRepos(uiModel);
        verify(view).showReposAlreadySaved();
        verify(view).showSaveReposButton(false);
    }

    @Test
    public void saveReposByUsername_shouldNavigateToReposScreen() {
        // When
        presenter.saveReposByUsername();

        // Then
        verify(view).navigateToReposScreen();
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
    public void restoreStateAndShowRepos_with_uiModel_not_in_cache_shouldShowRepos() {
        // Given a stubbed uiModel
        uiModel = new ReposByUsernameUI(REPOS_BY_USERNAME_UI_MAP, false /* is not cached*/);

        // When
        presenter.restoreStateAndShowRepos(uiModel);

        // Then
        verify(view).showRepos(uiModel);
        verify(view).showSaveReposButton(true);
    }

    @Test
    public void restoreStateAndShowRepos_with_uiModel_in_cache_shouldShowRepos() {
        // Given a stubbed uiModel
        uiModel = new ReposByUsernameUI(REPOS_BY_USERNAME_UI_MAP, true /* is cached*/);

        // When
        presenter.restoreStateAndShowRepos(uiModel);

        // Then
        verify(view).showRepos(uiModel);
        verify(view).showReposAlreadySaved();
        verify(view).showSaveReposButton(false);
    }

    @Test
    public void transformModelToUiModel_shouldTransformDataProperly() {
        // Given a stubbed model
        model = new ReposByUsername(REPOS_BY_USERNAME_MAP, true /* is cached */);

        // When
        ReposByUsernameUI localUiModel = presenter.transformModelToUiModel(model);

        // Then
        assertEquals(model.isCached(), localUiModel.isCached());
        assertEquals(model.getReposByUsername().firstKey(), localUiModel.getUiModel().firstKey());
        assertEquals(model.getReposByUsername().get(USERNAME).size(), localUiModel.getUiModel().get(USERNAME).size());
        assertEquals(model.getReposByUsername().get(USERNAME).get(0).getName(), localUiModel.getUiModel().get(USERNAME).get(0).getName());
        assertEquals(model.getReposByUsername().get(USERNAME).get(0).getUrl(), localUiModel.getUiModel().get(USERNAME).get(0).getUrl());
        assertEquals(model.getReposByUsername().get(USERNAME).get(1).getName(), localUiModel.getUiModel().get(USERNAME).get(1).getName());
        assertEquals(model.getReposByUsername().get(USERNAME).get(1).getUrl(), localUiModel.getUiModel().get(USERNAME).get(1).getUrl());
    }

    @Test
    public void transformUiModelToModel_shouldTransformDataProperly() {
        // Given a stubbed uiModel
        uiModel = new ReposByUsernameUI(REPOS_BY_USERNAME_UI_MAP, true /* is cached */);

        // When
        ReposByUsername localModel = presenter.transformUiModelToModel(uiModel);

        // Then
        assertEquals(uiModel.isCached(), localModel.isCached());
        assertEquals(uiModel.getUiModel().firstKey(), localModel.getReposByUsername().firstKey());
        assertEquals(uiModel.getUiModel().get(USERNAME).size(), localModel.getReposByUsername().get(USERNAME).size());
        assertEquals(uiModel.getUiModel().get(USERNAME).get(0).getName(), localModel.getReposByUsername().get(USERNAME).get(0).getName());
        assertEquals(uiModel.getUiModel().get(USERNAME).get(0).getUrl(), localModel.getReposByUsername().get(USERNAME).get(0).getUrl());
        assertEquals(uiModel.getUiModel().get(USERNAME).get(1).getName(), localModel.getReposByUsername().get(USERNAME).get(1).getName());
        assertEquals(uiModel.getUiModel().get(USERNAME).get(1).getUrl(), localModel.getReposByUsername().get(USERNAME).get(1).getUrl());
    }
}