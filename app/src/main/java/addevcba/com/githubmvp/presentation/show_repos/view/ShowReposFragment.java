package addevcba.com.githubmvp.presentation.show_repos.view;

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

import com.brandongogetap.stickyheaders.StickyLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import addevcba.com.githubmvp.R;
import addevcba.com.githubmvp.data.model.Repo;
import addevcba.com.githubmvp.presentation.show_repos.ShowReposContract;
import addevcba.com.githubmvp.presentation.show_repos.model.RepoUI;
import addevcba.com.githubmvp.presentation.show_repos.model.StickyHeaderUI;
import addevcba.com.githubmvp.presentation.show_repos.presenter.ShowReposPresenter;

/**
 * Shows a list of repos grouped by username and sorted alphabetically.
 *
 * @author lucas.nobile
 */
public class ShowReposFragment extends Fragment implements ShowReposContract.View {

    private static final int REQUEST_ADD_REPOS = 1;
    private static final String KEY_REPOS_BY_USERNAME = "repos_by_username";

    private RecyclerView rvShowRepos;

    public interface OnItemSelectedListener {
        void onStickyHeaderSelected(StickyHeaderUI stickyHeaderUI);

        void onRepoSelected(RepoUI repo);
    }

    private ShowReposContract.Presenter presenter;

    private ShowReposAdapter adapter;

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

    public static ShowReposFragment newInstance() {
        return new ShowReposFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ShowReposPresenter(this);
        adapter = new ShowReposAdapter(new ArrayList<ViewType>(), R.string.empty_show_repos_message, itemSelectedListener);
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
            TreeMap<String, List<Repo>> reposByUsername = (TreeMap<String, List<Repo>>) savedInstanceState.getSerializable(KEY_REPOS_BY_USERNAME);
            presenter.restoreStateAndShowReposByUsername(reposByUsername);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_REPOS_BY_USERNAME, presenter.getReposByUsername());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadReposByUsername();
    }

    @Override
    public void showReposByUsername(List<ViewType> reposByUsername, boolean goToTop) {
        if (goToTop) {
            rvShowRepos.smoothScrollToPosition(0);
        }
        adapter.addAll(reposByUsername);
    }

    @Override
    public void showAddReposScreen() {
        // TODO Go to Add repos screen
//        Intent intent = new Intent(getContext(), AddReposActivity.class);
//        startActivityForResult(intent, REQUEST_ADD_REPOS);
    }

    @Override
    public void showGitHubUsernamePage(String url) {
        Uri uri = Uri.parse(url);
        browse(uri);
    }

    @Override
    public void showGitHubRepoPage(String url) {
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
