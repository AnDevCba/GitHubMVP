package addevcba.com.githubmvp.presentation.add_repos;

import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.brandongogetap.stickyheaders.StickyLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import addevcba.com.githubmvp.R;
import addevcba.com.githubmvp.data.model.Repo;
import addevcba.com.githubmvp.presentation.show_repos.model.RepoUI;
import addevcba.com.githubmvp.presentation.show_repos.model.StickyHeaderUI;
import addevcba.com.githubmvp.presentation.show_repos.view.ShowReposAdapter;
import addevcba.com.githubmvp.presentation.show_repos.view.ShowReposFragment;
import addevcba.com.githubmvp.presentation.show_repos.view.ViewType;

/**
 * Allows the user to search for repos by username and shows repos if any.
 *
 * @author lucas.nobile
 */
public class AddReposFragment extends Fragment implements AddReposContract.View {

    private static final String KEY_REPOS_BY_USERNAME = "repos_by_username";

    private AddReposContract.Presenter presenter;

    private ShowReposAdapter adapter;

    private EditText tvUsername;
    private ProgressBar progressBar;
    private RecyclerView rvShowRepos;
    private FloatingActionButton fab;

    private ShowReposFragment.OnItemSelectedListener itemSelectedListener = new ShowReposFragment.OnItemSelectedListener() {
        @Override
        public void onStickyHeaderSelected(StickyHeaderUI stickyHeaderUI) {
            presenter.goToGitHubUsernamePage(stickyHeaderUI.getName());
        }

        @Override
        public void onRepoSelected(RepoUI repo) {
            presenter.goToGitHubRepoPage(repo.getUrl());
        }
    };

    public static AddReposFragment newInstance() {
        return new AddReposFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AddReposPresenter(this);
        adapter = new ShowReposAdapter(new ArrayList<ViewType>(), R.string.empty_search_repos_message, itemSelectedListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_repos, container, false);

        tvUsername = (EditText) view.findViewById(R.id.tv_username);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        setUpRecyclerView(view);
        setUpListeners(view);

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

    private void setUpListeners(View view) {
        Button btnSearchRepos = (Button) view.findViewById(R.id.btn_search_repos);
        btnSearchRepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rvShowRepos.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

                String username = tvUsername.getText().toString();

                // DO NOT validate if it is empty on the View!!!
                presenter.searchReposByUsername(username);
            }
        });

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_save_repos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveReposByUsername();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            TreeMap<String, List<Repo>> data = (TreeMap<String, List<Repo>>) savedInstanceState.getSerializable(KEY_REPOS_BY_USERNAME);
            presenter.restoreStateAndShowReposByUsername(data);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_REPOS_BY_USERNAME, presenter.getReposByUsername());
    }

    @Override
    public void showEmptyUsernameError() {
        Snackbar.make(tvUsername, getString(R.string.empty_username_error_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showRepos(List<ViewType> repos) {
        rvShowRepos.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        adapter.addAll(repos);
    }

    @Override
    public void showProgressBar(boolean show) {
        if (progressBar != null) {
            int visibility = show ? View.VISIBLE : View.GONE;
            progressBar.setVisibility(visibility);
        }
    }

    @Override
    public void showError(String error) {
        rvShowRepos.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);
        Snackbar.make(getView(), getString(R.string.error_searching_for_repos_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void hideSoftKeyboard() {
        View view = this.getView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void goToShowReposScreen() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
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
    public void showReposAlreadySaved() {
        Snackbar.make(getView(), getString(R.string.error_repos_already_saved_message), Snackbar.LENGTH_LONG).show();
    }

    private void browse(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }
}