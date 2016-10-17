package com.andevcba.githubmvp.presentation.show_repos.presenter;

import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.repository.ReposCallback;
import com.andevcba.githubmvp.domain.interactor.LoadReposInteractor;
import com.andevcba.githubmvp.presentation.show_repos.ReposContract;
import com.andevcba.githubmvp.presentation.show_repos.model.RepoUI;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;
import com.andevcba.githubmvp.presentation.show_repos.view.ReposFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Presenter that handles user actions from {@link ReposFragment} view,
 * shows repos by a given user name and updates the view.
 * <p>
 * Notice that Presenter knows nothing about Android framework.
 *
 * @author lucas.nobile
 */
public class ReposPresenter implements ReposContract.Presenter, ReposCallback {

    private final ReposContract.View view;
    private LoadReposInteractor loadReposInteractor;
    private ReposByUsernameUI uiModel;

    public ReposPresenter(LoadReposInteractor loadReposInteractor, ReposContract.View view) {
        this.loadReposInteractor = loadReposInteractor;
        this.view = view;
    }

    @Override
    public void loadAllRepos() {
        loadReposInteractor.execute(this);
    }

    @Override
    public void onResponse(ReposByUsername response) {
        uiModel = transformModelToUiModel(response);
        view.showRepos(uiModel, true /* go to top */);
    }

    @Override
    public void onError(String error) {
        view.showError(error);
    }

    @Override
    public void goToAddReposScreen() {
        view.navigateToAddReposScreen();
    }

    @Override
    public void goToGitHubUsernamePage(String username) {
        String url = GitHubApiClient.BASE_URL + username;
        view.browseGitHubUsernamePage(url);
    }

    @Override
    public void goToGitHubRepoPage(String url) {
        view.browseGitHubRepoPage(url);
    }

    @Override
    public void restoreStateAndShowRepos(ReposByUsernameUI uiModel) {
        if (uiModel != null) {
            this.uiModel = uiModel;
            view.showRepos(uiModel, false /*not going to top*/);
        }
    }

    @Override
    public ReposByUsernameUI transformModelToUiModel(ReposByUsername model) {
        TreeMap<String, List<RepoUI>> reposMap = new TreeMap<>();
        for (TreeMap.Entry<String, List<Repo>> entry : model.getReposByUsername().entrySet()) {
            String username = entry.getKey();
            List<RepoUI> repos = new ArrayList<>();
            for (Repo repo : entry.getValue()) {
                repos.add(new RepoUI(repo.getName(), repo.getUrl()));
            }
            reposMap.put(username, repos);
        }
        return new ReposByUsernameUI(reposMap, model.isCached());
    }

    public ReposByUsernameUI getUiModel() {
        return uiModel;
    }
}
