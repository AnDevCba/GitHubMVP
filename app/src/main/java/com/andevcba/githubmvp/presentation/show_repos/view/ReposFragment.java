package com.andevcba.githubmvp.presentation.show_repos.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andevcba.githubmvp.R;
import com.andevcba.githubmvp.data.DependencyProvider;
import com.andevcba.githubmvp.presentation.add_repos.AddReposActivity;
import com.andevcba.githubmvp.presentation.show_repos.ReposContract;
import com.andevcba.githubmvp.presentation.show_repos.model.RepoUI;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;
import com.andevcba.githubmvp.presentation.show_repos.model.StickyHeaderUI;
import com.andevcba.githubmvp.presentation.show_repos.presenter.ReposPresenter;
import com.brandongogetap.stickyheaders.StickyLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows a list of repos grouped by username and sorted alphabetically.
 *
 * @author lucas.nobile
 */
public class ReposFragment extends Fragment implements ReposContract.View {

    private static final int REQUEST_ADD_REPOS = 1;
    private static final String KEY_REPOS_BY_USERNAME = "repos_by_username";

    private RecyclerView rvShowRepos;

    public interface OnItemSelectedListener {
        void onStickyHeaderSelected(StickyHeaderUI stickyHeaderUI);

        void onRepoSelected(RepoUI repo);
    }

    private ReposContract.Presenter presenter;

    private ReposAdapter adapter;

    private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onStickyHeaderSelected(StickyHeaderUI stickyHeaderUI) {
            presenter.goToGitHubUsernamePage(stickyHeaderUI.getName());
        }

        @Override
        public void onRepoSelected(RepoUI repo) {
            presenter.goToGitHubRepoPage(repo.getUrl());
        }
    };

    public static ReposFragment newInstance() {
        return new ReposFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReposPresenter(DependencyProvider.provideLoadReposInteractor(), this);
        adapter = new ReposAdapter(new ArrayList<ViewType>(), R.string.empty_show_repos_message, itemSelectedListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.container_show_repos, container, false);

        setUpRecyclerView(view);
        setUpFloatingActionButton();

        return view;
    }

    private void setUpRecyclerView(View view) {
        rvShowRepos = (RecyclerView) view.findViewById(R.id.rv_show_repos);
        rvShowRepos.setAdapter(adapter);

        rvShowRepos.setHasFixedSize(true);

        // Set layout manager
        StickyLayoutManager layoutManager = new StickyLayoutManager(getContext(), adapter);
        layoutManager.elevateHeaders(true);
        rvShowRepos.setLayoutManager(layoutManager);
    }

    private void setUpFloatingActionButton() {
        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_save_repos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goToAddReposScreen();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            ReposByUsernameUI reposByUsernameUI = savedInstanceState.getParcelable(KEY_REPOS_BY_USERNAME);
            presenter.restoreStateAndShowRepos(reposByUsernameUI);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_REPOS_BY_USERNAME, presenter.getUiModel());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadAllRepos();
    }

    @Override
    public void showRepos(ReposByUsernameUI uiModel, boolean goToTop) {
        if (goToTop) {
            rvShowRepos.smoothScrollToPosition(0);
        }
        List<ViewType> items = uiModel.getViewTypes();
        adapter.addAll(items);
    }

    @Override
    public void navigateToAddReposScreen() {
        Intent intent = new Intent(getContext(), AddReposActivity.class);
        startActivityForResult(intent, REQUEST_ADD_REPOS);
    }

    @Override
    public void browseGitHubUsernamePage(String url) {
        Uri uri = Uri.parse(url);
        browse(uri);
    }

    @Override
    public void browseGitHubRepoPage(String url) {
        Uri uri = Uri.parse(url);
        browse(uri);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(getView(), getString(R.string.error_loading_repos_message), Snackbar.LENGTH_LONG).show();
    }

    private void browse(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_ADD_REPOS == requestCode && Activity.RESULT_OK == resultCode) {
            Snackbar.make(getView(), getString(R.string.repos_saved_message), Snackbar.LENGTH_SHORT).show();
        }
    }
}
