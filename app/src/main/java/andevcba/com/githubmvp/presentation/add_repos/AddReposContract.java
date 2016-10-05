package andevcba.com.githubmvp.presentation.add_repos;

import java.util.List;

import andevcba.com.githubmvp.presentation.show_repos.model.ReposByUsernameUI;
import andevcba.com.githubmvp.presentation.show_repos.view.ViewType;

/**
 * Contract between {@link AddReposFragment} view and {@link AddReposPresenter}.
 *
 * @author lucas.nobile
 */
public interface AddReposContract {

    interface View {
        void showEmptyUsernameError();

        void showRepos(List<ViewType> repos);

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

        ReposByUsernameUI getReposByUsernameUI();

        void restoreStateAndShowReposByUsername(ReposByUsernameUI reposByUsername);
    }
}
