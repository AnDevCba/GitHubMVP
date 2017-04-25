package com.andevcba.githubmvp.presentation.add_repos;

import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;

/**
 * Contract between {@link AddReposFragment} view and {@link AddReposPresenter}.
 *
 * @author lucas.nobile
 */
public interface AddReposContract {

    interface View {

        void showEmptyUsernameError();

        void showRepos(ReposByUsernameUI reposByUsernameUI);

        void showSaveReposButton(boolean show);

        void showProgressBar(boolean show);

        void showError(String error);

        void hideSoftKeyboard();

        void navigateToReposScreen();

        void browseGitHubUsernamePage(String url);

        void browseGitHubRepoPage(String url);

        void showReposAlreadySaved();

        void stopRefreshing();
    }

    interface Presenter {

        void attachView(AddReposContract.View view);

        boolean isViewAttached();

        void searchReposByUsername(String username);

        void saveReposByUsername();

        void goToGitHubUsernamePage(String username);

        void goToGitHubRepoPage(String url);

        void restoreStateAndShowRepos(ReposByUsernameUI data);

        ReposByUsernameUI transformModelToUiModel(ReposByUsername data);

        ReposByUsername transformUiModelToModel(ReposByUsernameUI data);

        ReposByUsernameUI getUiModel();

        void refreshRepos(String lastQuery);
    }
}
