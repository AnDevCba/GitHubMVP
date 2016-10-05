package andevcba.com.githubmvp.presentation.show_repos;

import java.util.List;

import andevcba.com.githubmvp.presentation.show_repos.model.ReposByUsernameUI;
import andevcba.com.githubmvp.presentation.show_repos.presenter.ShowReposPresenter;
import andevcba.com.githubmvp.presentation.show_repos.view.ShowReposFragment;
import andevcba.com.githubmvp.presentation.show_repos.view.ViewType;

/**
 * Contract between {@link ShowReposFragment} and {@link ShowReposPresenter}
 *
 * @author lucas.nobile
 */
public interface ShowReposContract {

    interface View {

        void showReposByUsername(List<ViewType> items, boolean goToTop);

        void showAddReposScreen();

        void showGitHubUsernamePage(String username);

        void showGitHubRepoPage(String url);

        void showError(String error);
    }

    interface Presenter {

        void loadAllRepos();

        void goToAddReposScreen();

        void goToGitHubUsernamePage(String username);

        void goToGitHubRepoPage(String url);

        ReposByUsernameUI getReposByUsernameUI();

        void restoreStateAndShowReposByUsername(ReposByUsernameUI data);
    }
}
