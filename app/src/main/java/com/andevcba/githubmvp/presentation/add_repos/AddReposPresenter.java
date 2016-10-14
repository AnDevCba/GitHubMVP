package com.andevcba.githubmvp.presentation.add_repos;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.repository.ReposCallback;
import com.andevcba.githubmvp.domain.interactor.SaveReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SearchReposByUsernameInteractor;
import com.andevcba.githubmvp.presentation.show_repos.model.RepoUI;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;
import com.andevcba.githubmvp.presentation.show_repos.view.ViewType;

/**
 * Presenter that handles user actions from {@link AddReposFragment} view,
 * shows repos by a given user name and updates the view.
 * <p>
 * Notice that Presenter knows nothing about Android framework.
 *
 * @author lucas.nobile
 */
public class AddReposPresenter implements AddReposContract.Presenter, ReposCallback {

    private AddReposContract.View view;
    private SearchReposByUsernameInteractor searchReposInteractor;
    private SaveReposInteractor saveReposInteractor;
    private ReposByUsernameUI reposByUsernameUI;

    public AddReposPresenter(AddReposContract.View view) {
        this.view = view;
        searchReposInteractor = new SearchReposByUsernameInteractor(this);
        saveReposInteractor = new SaveReposInteractor();
    }

    @Override
    public void searchReposByUsername(final String username) {
        if (username.isEmpty()) {
            view.showEmptyUsernameError();
        } else {
            view.hideSoftKeyboard();
            view.showProgressBar(true);

            String formattedUsername = username.toLowerCase().trim();
            searchReposInteractor.setUsername(formattedUsername);
            searchReposInteractor.execute();
        }
    }

    @Override
    public void onResponse(ReposByUsername response) {
        view.showProgressBar(false);
        reposByUsernameUI = transformDataToViewModel(response);
        List<ViewType> items = reposByUsernameUI.getViewTypes();
        view.showRepos(items);

        if (response.isCached()) {
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
        ReposByUsername data = transformViewModelToData(reposByUsernameUI);
        saveReposInteractor.setReposByUsername(data);
        saveReposInteractor.execute();

        view.goToShowReposScreen();
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
            view.showRepos(items);

            if (reposByUsername.isCached()) {
                view.showReposAlreadySaved();
                view.showSaveReposButton(false);
            } else {
                view.showSaveReposButton(true);
            }
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

    private ReposByUsername transformViewModelToData(ReposByUsernameUI reposByUsername) {
        TreeMap<String, List<Repo>> reposMap = new TreeMap<>();
        for (TreeMap.Entry<String, List<RepoUI>> entry : reposByUsername.getReposByUsername().entrySet()) {
            String username = entry.getKey();
            List<Repo> repos = new ArrayList<>();
            for (RepoUI repo : entry.getValue()) {
                repos.add(new Repo(repo.getName(), repo.getUrl()));
            }
            reposMap.put(username, repos);
        }
        return new ReposByUsername(reposMap, reposByUsername.isCached());
    }
}
