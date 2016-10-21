package com.andevcba.githubmvp.presentation.add_repos;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.domain.interactor.Interactor;
import com.andevcba.githubmvp.domain.interactor.SaveReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SearchReposByUsernameInteractor;
import com.andevcba.githubmvp.presentation.show_repos.model.RepoUI;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Presenter that handles user actions from {@link AddReposFragment} view,
 * shows repos by a given user name and updates the view.
 * <p>
 * Notice that Presenter knows nothing about Android framework.
 *
 * @author lucas.nobile
 */
public class AddReposPresenter implements AddReposContract.Presenter, ReposCallback {

    private Interactor searchReposInteractor;
    private Interactor saveReposInteractor;
    private AddReposContract.View view;
    private ReposByUsername model;
    private ReposByUsernameUI uiModel;

    public AddReposPresenter(SearchReposByUsernameInteractor searchReposInteractor, SaveReposInteractor saveReposInteractor, AddReposContract.View view) {
        this.searchReposInteractor = searchReposInteractor;
        this.saveReposInteractor = saveReposInteractor;
        this.view = view;
    }

    @Override
    public void searchReposByUsername(final String username) {
        if (username.isEmpty()) {
            view.showEmptyUsernameError();
        } else {
            view.hideSoftKeyboard();
            view.showProgressBar(true);

            String formattedUsername = username.toLowerCase().trim();
            searchReposInteractor.execute(formattedUsername, this);
        }
    }

    @Override
    public void onResponse(ReposByUsername model) {
        view.showProgressBar(false);

        this.model = model;
        uiModel = transformModelToUiModel(model);

        view.showRepos(uiModel);

        if (model.isCached()) {
            view.showReposAlreadySaved();
            view.showSaveReposButton(false);
        } else {
            view.showSaveReposButton(true);
        }
    }

    @Override
    public void onError(String error) {
        view.showProgressBar(false);
        view.showError(error);
        view.showSaveReposButton(false);
    }

    @Override
    public void saveReposByUsername() {
        saveReposInteractor.execute(model);

        view.navigateToReposScreen();
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
            this.model = transformUiModelToModel(uiModel);
            view.showRepos(this.uiModel);

            if (uiModel.isCached()) {
                view.showReposAlreadySaved();
                view.showSaveReposButton(false);
            } else {
                view.showSaveReposButton(true);
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

    @Override
    public ReposByUsername transformUiModelToModel(ReposByUsernameUI uiModel) {
        TreeMap<String, List<Repo>> reposMap = new TreeMap<>();
        for (TreeMap.Entry<String, List<RepoUI>> entry : uiModel.getUiModel().entrySet()) {
            String username = entry.getKey();
            List<Repo> repos = new ArrayList<>();
            for (RepoUI repo : entry.getValue()) {
                repos.add(new Repo(repo.getName(), repo.getUrl()));
            }
            reposMap.put(username, repos);
        }
        return new ReposByUsername(reposMap, uiModel.isCached());
    }

    public ReposByUsernameUI getUiModel() {
        return uiModel;
    }
}
