package andevcba.com.githubmvp.presentation.show_repos.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import andevcba.com.githubmvp.data.model.Repo;
import andevcba.com.githubmvp.data.model.ReposByUsername;
import andevcba.com.githubmvp.data.net.GitHubApiClient;
import andevcba.com.githubmvp.data.repository.ReposCallback;
import andevcba.com.githubmvp.domain.interactor.LoadReposInteractor;
import andevcba.com.githubmvp.presentation.show_repos.ShowReposContract;
import andevcba.com.githubmvp.presentation.show_repos.model.RepoUI;
import andevcba.com.githubmvp.presentation.show_repos.model.ReposByUsernameUI;
import andevcba.com.githubmvp.presentation.show_repos.view.ShowReposFragment;
import andevcba.com.githubmvp.presentation.show_repos.view.ViewType;

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
    private LoadReposInteractor loadReposInteractor;
    private ReposByUsernameUI reposByUsernameUI;

    public ShowReposPresenter(ShowReposFragment view) {
        this.view = view;
        loadReposInteractor = new LoadReposInteractor(this);
    }

    @Override
    public void loadAllRepos() {
        loadReposInteractor.execute();
    }

    @Override
    public void onResponse(ReposByUsername response) {
        reposByUsernameUI = transformDataToViewModel(response);
        List<ViewType> items = reposByUsernameUI.getViewTypes();
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

    public ReposByUsernameUI getReposByUsernameUI() {
        return reposByUsernameUI;
    }

    @Override
    public void restoreStateAndShowReposByUsername(ReposByUsernameUI reposByUsername) {
        if (reposByUsername != null) {
            this.reposByUsernameUI = reposByUsername;
            List<ViewType> items = reposByUsernameUI.getViewTypes();
            view.showReposByUsername(items, false /*not going to top*/);
        }
    }

    private ReposByUsernameUI transformDataToViewModel(ReposByUsername data) {
        TreeMap<String, List<RepoUI>> reposMap = new TreeMap<>();
        for (TreeMap.Entry<String, List<Repo>> entry : data.getReposByUsername().entrySet()) {
            String username = entry.getKey();
            List<RepoUI> repos = new ArrayList<>();
            for (Repo repo : entry.getValue()) {
                repos.add(new RepoUI(repo.getName(), repo.getUrl()));
            }
            reposMap.put(username, repos);
        }
        return new ReposByUsernameUI(reposMap, data.isCached());
    }
}
