package com.andevcba.githubmvp.presentation.show_repos;

import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;
import com.andevcba.githubmvp.presentation.show_repos.presenter.ReposPresenter;
import com.andevcba.githubmvp.presentation.show_repos.view.ReposFragment;

/**
 * Contract between {@link ReposFragment} and {@link ReposPresenter}
 *
 * @author lucas.nobile
 */
public interface ReposContract {

    interface View {

        void showRepos(ReposByUsernameUI uiModel, boolean goToTop);

        void showError(String error);

        void navigateToAddReposScreen();

        void browseGitHubUsernamePage(String username);

        void browseGitHubRepoPage(String url);
    }

    interface Presenter {

        void loadAllRepos();

        void goToAddReposScreen();

        void goToGitHubUsernamePage(String username);

        void goToGitHubRepoPage(String url);

        void restoreStateAndShowRepos(ReposByUsernameUI uiModel);

        ReposByUsernameUI transformModelToUiModel(ReposByUsername model);

        ReposByUsernameUI getUiModel();
    }
}
