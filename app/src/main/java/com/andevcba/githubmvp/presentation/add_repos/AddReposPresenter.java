package com.andevcba.githubmvp.presentation.add_repos;

import com.andevcba.githubmvp.data.model.Repo;
import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.repository.ReposCallback;
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

    private SearchReposByUsernameInteractor searchReposInteractor;
    private SaveReposInteractor saveReposInteractor;
    private AddReposContract.View view;
    private ReposByUsernameUI reposByUsernameUI;

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
            searchReposInteractor.setUsername(formattedUsername);
            searchReposInteractor.execute(this);
        }
    }

    @Override
    public void onResponse(ReposByUsername data) {
        view.showProgressBar(false);
        reposByUsernameUI = transformDataToViewModel(data);
        view.showRepos(reposByUsernameUI);

        if (data.isCached()) {
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
        saveReposInteractor.execute(this);

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
    public void restoreStateAndShowReposByUsername(ReposByUsernameUI data) {
        if (data != null) {
            this.reposByUsernameUI = data;
            view.showRepos(reposByUsernameUI);

            if (data.isCached()) {
                view.showReposAlreadySaved();
                view.showSaveReposButton(false);
            } else {
                view.showSaveReposButton(true);
            }
        }
    }

    @Override
    public ReposByUsernameUI transformDataToViewModel(ReposByUsername data) {
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

    @Override
    public ReposByUsername transformViewModelToData(ReposByUsernameUI data) {
        TreeMap<String, List<Repo>> reposMap = new TreeMap<>();
        for (TreeMap.Entry<String, List<RepoUI>> entry : data.getReposByUsername().entrySet()) {
            String username = entry.getKey();
            List<Repo> repos = new ArrayList<>();
            for (RepoUI repo : entry.getValue()) {
                repos.add(new Repo(repo.getName(), repo.getUrl()));
            }
            reposMap.put(username, repos);
        }
        return new ReposByUsername(reposMap, data.isCached());
    }
}
