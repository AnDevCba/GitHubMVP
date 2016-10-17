package com.andevcba.githubmvp.presentation.add_repos;

import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.repository.ReposCallback;
import com.andevcba.githubmvp.domain.interactor.SaveReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SearchReposByUsernameInteractor;
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

import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link AddReposPresenter}.
 *
 * @author lucas.nobile
 */
public class AddReposPresenterTest {

    private final static String EMPTY_USERNAME = "";
    private final static String USERNAME = "AnDevCba";
    private final static String NOT_IN_CACHE_USERNAME = "NotInCache";
    private final static String NOT_VALID_USERNAME = "Na_Na_Na_Na_Na_Na_Na_Naa_Batman";
    private final static String REPO_NAME = "RepoName";

    private final static String ERROR_MESSAGE = "Error message";

    private final static Repo REPO1 = new Repo("repo1", "url1");
    private final static Repo REPO2 = new Repo("repo2", "url2");
    private static List<Repo> REPO_LIST = new ArrayList<>();

    private static TreeMap<String, List<Repo>> REPOS_BY_USERNAME_MAP;
    private static ReposByUsername reposByUsername;

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

        // Mocked repo list
        REPO_LIST.add(REPO1);
        REPO_LIST.add(REPO2);

        // Mocked repos by username
        REPOS_BY_USERNAME_MAP = new TreeMap<>();
        REPOS_BY_USERNAME_MAP.put(USERNAME, REPO_LIST);
    }

    @After
    public void tearDown() {
        REPO_LIST.clear();
        REPOS_BY_USERNAME_MAP.clear();
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
        verify(searchReposByUsernameInteractor).execute(reposCallbackArgumentCaptor.capture());
        reposCallbackArgumentCaptor.getValue().onError(ERROR_MESSAGE);

        // Then
        verify(view).showError(ERROR_MESSAGE);
    }

    @Test
    public void searchReposByUsername_with_username_not_in_cache_usingInteractorAndLoadIntoView() {
        // Given a mocked repos by username that is not cached
        reposByUsername = new ReposByUsername(REPOS_BY_USERNAME_MAP, false /* is not cached */);

        // When
        presenter.searchReposByUsername(NOT_IN_CACHE_USERNAME);

        // Callback is captured and invoked with stubbed repos by username
        verify(searchReposByUsernameInteractor).execute(reposCallbackArgumentCaptor.capture());
        reposCallbackArgumentCaptor.getValue().onResponse(reposByUsername);

        // Then
        // progress indicator is hidden
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).hideSoftKeyboard();
        inOrder.verify(view).showProgressBar(true);
        inOrder.verify(view).showProgressBar(false);
        // and repos are shown in UI
        ReposByUsernameUI reposByUsernameUI = presenter.getUiModel();
        verify(view).showRepos(reposByUsernameUI);
        verify(view).showSaveReposButton(true);
    }

    @Test
    public void searchReposByUsername_with_username_in_cache_usingInteractorAndLoadIntoView() {
        // Given a mocked repos by username that is cached
        reposByUsername = new ReposByUsername(REPOS_BY_USERNAME_MAP, true /* is cached */);

        // When
        presenter.searchReposByUsername(USERNAME);

        // Callback is captured and invoked with stubbed repos by username
        verify(searchReposByUsernameInteractor).execute(reposCallbackArgumentCaptor.capture());
        reposCallbackArgumentCaptor.getValue().onResponse(reposByUsername);

        // Then
        // progress indicator is hidden
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).hideSoftKeyboard();
        inOrder.verify(view).showProgressBar(true);
        inOrder.verify(view).showProgressBar(false);
        // and repos are shown in UI
        ReposByUsernameUI reposByUsernameUI = presenter.getUiModel();
        verify(view).showRepos(reposByUsernameUI);
        verify(view).showReposAlreadySaved();
        verify(view).showSaveReposButton(false);
    }

    @Test
    public void saveReposByUsername_shouldGoToShowReposScreen() {
        // Given a a mocked repos by username that is cached
        reposByUsername = new ReposByUsername(REPOS_BY_USERNAME_MAP, true /* is cached */);

        // When
        presenter.saveReposByUsername();

        // Callback is captured and invoked with stubbed repos by username
        verify(saveReposInteractor).execute(reposCallbackArgumentCaptor.capture());
        reposCallbackArgumentCaptor.getValue().onResponse(reposByUsername);

        // Then
        verify(view).goToShowReposScreen();
    }

    @Test
    public void goToGitHubUsernamePage_shouldShowGitHubUsernamePage() {
        // Given a stubbed url
        String url = GitHubApiClient.BASE_URL + USERNAME;

        // When
        presenter.goToGitHubUsernamePage(USERNAME);

        // Then
        verify(view).showGitHubUsernamePage(url);
    }

    @Test
    public void goToGitHubRepoPage_shouldShowGitHubRepoPage() {
        // Given a stubbed repo url
        String url = GitHubApiClient.BASE_URL + USERNAME + "/" + REPO_NAME;

        // When
        presenter.goToGitHubRepoPage(url);

        // Then
        verify(view).showGitHubRepoPage(url);
    }
}