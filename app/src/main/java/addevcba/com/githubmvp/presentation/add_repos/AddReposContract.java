package addevcba.com.githubmvp.presentation.add_repos;

import java.util.List;
import java.util.TreeMap;

import addevcba.com.githubmvp.data.model.Repo;
import addevcba.com.githubmvp.presentation.show_repos.view.ViewType;

/**
 * Contract between {@link AddReposFragment} view and {@link AddReposPresenter}.
 *
 * @author lucas.nobile
 */
public interface AddReposContract {

    interface View {
        void showEmptyUsernameError();

        void showRepos(List<ViewType> repos);

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

        TreeMap<String, List<Repo>> getReposByUsername();

        void restoreStateAndShowReposByUsername(TreeMap<String, List<Repo>> reposByUsername);
    }
}
