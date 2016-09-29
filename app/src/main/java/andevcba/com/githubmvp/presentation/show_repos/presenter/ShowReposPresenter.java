package andevcba.com.githubmvp.presentation.show_repos.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import andevcba.com.githubmvp.data.net.GitHubApiClient;
import andevcba.com.githubmvp.data.model.Repo;
import andevcba.com.githubmvp.data.repository.ReposCache;
import andevcba.com.githubmvp.data.repository.ReposCallback;
import andevcba.com.githubmvp.data.repository.Repository;
import andevcba.com.githubmvp.presentation.show_repos.ShowReposContract;
import andevcba.com.githubmvp.presentation.show_repos.model.RepoUI;
import andevcba.com.githubmvp.presentation.show_repos.model.StickyHeaderUI;
import andevcba.com.githubmvp.presentation.show_repos.view.ShowReposFragment;
import andevcba.com.githubmvp.presentation.show_repos.view.ViewType;
import andevcba.com.githubmvp.data.DependencyProvider;

/**
 * Presenter that handles user actions from {@link ShowReposFragment} view,
 * shows repos by a given user name and updates the view.
 * <p>
 * Notice that Presenter knows nothing about Android framework.
 *
 * @author lucas.nobile
 */
public class ShowReposPresenter implements ShowReposContract.Presenter, ReposCallback {

    private final ShowReposContract.View view;
    private Repository repository;
    private TreeMap<String, List<Repo>> reposByUsername;

    public ShowReposPresenter(ShowReposFragment view) {
        this.view = view;
        ReposCache reposCache = DependencyProvider.provideReposCache();
        repository = DependencyProvider.provideInMemoryRepository(reposCache);
    }

    @Override
    public void loadReposByUsername() {
        repository.loadReposByUsername(this);
    }

    @Override
    public void onResponse(TreeMap<String, List<Repo>> reposByUsername) {
        this.reposByUsername = reposByUsername;
        List<ViewType> items = transformReposByUsernameToViewTypes(reposByUsername);
        view.showReposByUsername(items, true /* go to top */);
    }

    @Override
    public void onError(String error) {
        view.showError(error);
    }

    @Override
    public void goToAddReposScreen() {
        view.showAddReposScreen();
    }

    @Override
    public void goToGitHubUsernamePage(String username) {
        String url = GitHubApiClient.BASE_URL + username;
        view.showGitHubUsernamePage(url);
    }

    @Override
    public void goToGitHubRepoPage(String url) {
        view.showGitHubRepoPage(url);
    }

    @Override
    public TreeMap<String, List<Repo>> getReposByUsername() {
        return reposByUsername;
    }

    @Override
    public void restoreStateAndShowReposByUsername(TreeMap<String, List<Repo>> reposByUsername) {
        if (reposByUsername != null) {
            this.reposByUsername = reposByUsername;
            List<ViewType> items = transformReposByUsernameToViewTypes(reposByUsername);
            view.showReposByUsername(items, false /*not going to top*/);
        }
    }

    private List<ViewType> transformReposByUsernameToViewTypes(TreeMap<String, List<Repo>> reposByUsername) {
        List<ViewType> items = new ArrayList<>();
        for (String username : reposByUsername.keySet()) {
            items.add(new StickyHeaderUI(username));
            List<Repo> repos = reposByUsername.get(username);
            for (Repo repo : repos) {
                items.add(new RepoUI(repo.getId(), repo.getName(), repo.getUrl(), repo.getCreatedAt(), repo.getStars(), repo.getLanguage()));
            }
        }
        return items;
    }
}
