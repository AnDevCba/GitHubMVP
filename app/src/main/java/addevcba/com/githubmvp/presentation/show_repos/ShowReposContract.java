package addevcba.com.githubmvp.presentation.show_repos;

import java.util.List;
import java.util.TreeMap;

import addevcba.com.githubmvp.data.model.Repo;
import addevcba.com.githubmvp.presentation.show_repos.presenter.ShowReposPresenter;
import addevcba.com.githubmvp.presentation.show_repos.view.ShowReposFragment;
import addevcba.com.githubmvp.presentation.show_repos.view.ViewType;

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

        void loadReposByUsername();

        void goToAddReposScreen();

        void goToGitHubUsernamePage(String username);

        void goToGitHubRepoPage(String url);

        TreeMap<String, List<Repo>> getReposByUsername();

        void restoreStateAndShowReposByUsername(TreeMap<String, List<Repo>> data);
    }
}
