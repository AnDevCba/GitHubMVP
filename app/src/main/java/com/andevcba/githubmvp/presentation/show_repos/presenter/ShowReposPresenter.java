package com.andevcba.githubmvp.presentation.show_repos.presenter;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.domain.interactor.Interactor;
import com.andevcba.githubmvp.domain.interactor.LoadReposInteractor;
import com.andevcba.githubmvp.presentation.show_repos.ShowReposContract;
import com.andevcba.githubmvp.presentation.show_repos.model.RepoUI;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;
import com.andevcba.githubmvp.presentation.show_repos.view.ShowReposFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Presenter that handles user actions from {@link ShowReposFragment} view,
 * shows repos by a given user name and updates the view.
 * <p>
 * Notice that Presenter knows nothing about Android framework.
 *
 * @author lucas.nobile
 */
@Singleton
public class ShowReposPresenter implements ShowReposContract.Presenter, ReposCallback {

    private Interactor loadReposInteractor;
    private ShowReposContract.View view;
    private ReposByUsernameUI uiModel;

    @Inject
    public ShowReposPresenter(LoadReposInteractor loadReposInteractor) {
        this.loadReposInteractor = loadReposInteractor;
    }

    @Override
    public void attachView(ShowReposContract.View view) {
        this.view = view;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void loadAllRepos() {
        loadReposInteractor.execute(this);
    }

    @Override
    public void onResponse(ReposByUsername response) {
        if (isViewAttached()) {
            uiModel = transformModelToUiModel(response);
            view.showRepos(uiModel, true /* go to top */);
        }
    }

    @Override
    public void onError(String error) {
        if (isViewAttached()) {
            view.showError(error);
        }
    }

    @Override
    public void goToAddReposScreen() {
        if (isViewAttached()) {
            view.navigateToAddReposScreen();
        }
    }

    @Override
    public void goToGitHubUsernamePage(String username) {
        if (isViewAttached()) {
            String url = GitHubApiClient.BASE_URL + username;
            view.browseGitHubUsernamePage(url);
        }
    }

    @Override
    public void goToGitHubRepoPage(String url) {
        if (isViewAttached()) {
            view.browseGitHubRepoPage(url);
        }
    }

    @Override
    public void restoreStateAndShowRepos(ReposByUsernameUI uiModel) {
        if (isViewAttached()) {
            if (uiModel != null) {
                this.uiModel = uiModel;
                view.showRepos(uiModel, false /*not going to top*/);
            }
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
