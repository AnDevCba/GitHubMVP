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

        void goToShowReposScreen();

        void showGitHubUsernamePage(String url);

        void showGitHubRepoPage(String url);

        void showReposAlreadySaved();
    }

    interface Presenter {
        void searchReposByUsername(String username);

        void saveReposByUsername();

        void goToGitHubUsernamePage(String username);

        void goToGitHubRepoPage(String url);

        ReposByUsernameUI transformModelToUiModel(ReposByUsername data);

        ReposByUsername transformUiModelToModel(ReposByUsernameUI data);

        ReposByUsernameUI getUiModel();

        void restoreStateAndShowReposByUsername(ReposByUsernameUI data);
    }
}
