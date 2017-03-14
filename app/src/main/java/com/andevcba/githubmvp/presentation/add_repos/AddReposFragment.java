package com.andevcba.githubmvp.presentation.add_repos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andevcba.githubmvp.R;
import com.andevcba.githubmvp.data.DependencyProvider;
import com.andevcba.githubmvp.presentation.show_repos.model.RepoUI;
import com.andevcba.githubmvp.presentation.show_repos.model.ReposByUsernameUI;
import com.andevcba.githubmvp.presentation.show_repos.model.StickyHeaderUI;
import com.andevcba.githubmvp.presentation.show_repos.view.ReposAdapter;
import com.andevcba.githubmvp.presentation.show_repos.view.ReposFragment;
import com.andevcba.githubmvp.presentation.show_repos.view.ViewType;
import com.andevcba.githubmvp.presentation.views.custom.ImageDialog;
import com.brandongogetap.stickyheaders.StickyLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows the user to search for repos by username and shows repos if any.
 *
 * @author lucas.nobile
 */
public class AddReposFragment extends Fragment implements AddReposContract.View {

    private static final String KEY_UI_MODEL = "ui_model";

    private AddReposContract.Presenter presenter;

    private ReposAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvShowRepos;
    private RelativeLayout relativeLayout;
    private FloatingActionButton fab;
    private String lastQuery = "";

    private ReposFragment.OnItemSelectedListener itemSelectedListener = new ReposFragment.OnItemSelectedListener() {
        @Override
        public void onStickyHeaderSelected(StickyHeaderUI stickyHeaderUI) {
            presenter.goToGitHubUsernamePage(stickyHeaderUI.getName());
        }

        @Override
        public void onRepoSelected(RepoUI repo) {
            presenter.goToGitHubRepoPage(repo.getUrl());
        }

        @Override
        public void onImageSelected(ImageView circleImageView) {
            ImageDialog dialogFragment = new ImageDialog();
            dialogFragment.show(getFragmentManager()
                    .beginTransaction()
                    .addSharedElement(circleImageView, circleImageView.getTransitionName()), "ImageDialog");
        }
    };

    public static AddReposFragment newInstance() {
        return new AddReposFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AddReposPresenter(DependencyProvider.provideSearchReposByUsernameInteractor(), DependencyProvider.provideSaveReposInteractor(), this);
        adapter = new ReposAdapter(new ArrayList<ViewType>(), R.string.empty_search_repos_message, itemSelectedListener, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_repos, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);

        setUpRecyclerView(view);
        setUpListeners(view);
        setUpSwipeToRefresh();
        setHasOptionsMenu(true);

        return view;
    }

    private void setUpSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.refreshRepos(lastQuery);
                    }
                }
        );

        swipeRefreshLayout.setColorSchemeResources(
                R.color.md_indigo_50,
                R.color.md_indigo_300,
                R.color.md_indigo_600,
                R.color.md_indigo_900
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate( R.menu.menu, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        myActionMenuItem.expandActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                rvShowRepos.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                lastQuery = query;

                presenter.searchReposByUsername(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                //Not implemented
                return true;
            }
        });
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
            ReposByUsernameUI uiModel = savedInstanceState.getParcelable(KEY_UI_MODEL);
            presenter.restoreStateAndShowRepos(uiModel);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_UI_MODEL, presenter.getUiModel());
    }

    @Override
    public void showEmptyUsernameError() {
        Snackbar.make(relativeLayout, getString(R.string.empty_username_error_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showRepos(ReposByUsernameUI reposByUsernameUI) {
        List<ViewType> repos = reposByUsernameUI.getViewTypes();
        rvShowRepos.setVisibility(View.VISIBLE);
        adapter.addAll(repos);
    }

    @Override
    public void showSaveReposButton(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        fab.setVisibility(visibility);
    }

    @Override
    public void showProgressBar(boolean show) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(show);
        }
    }

    @Override
    public void showError(String error) {
        adapter.clearAll();
        rvShowRepos.setVisibility(View.VISIBLE);
        Snackbar.make(relativeLayout, getString(R.string.error_searching_for_repos_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void hideSoftKeyboard() {
        View view = this.getView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void navigateToReposScreen() {
        navigateWithAnimation();
    }

    private void navigateWithAnimation() {
        final Rect viewRect = new Rect();
        rvShowRepos.getChildAt(0).getGlobalVisibleRect(viewRect);

        Explode explode = new Explode();
        explode.setEpicenterCallback(new Transition.EpicenterCallback() {
            @Override
            public Rect onGetEpicenter(Transition transition) {
                return viewRect;
            }
        });

        explode.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

        TransitionManager.beginDelayedTransition(rvShowRepos, explode);
        rvShowRepos.setAdapter(null);
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
    public void showReposAlreadySaved() {
        Snackbar.make(relativeLayout, getString(R.string.error_repos_already_saved_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
        showEmptyUsernameError();
    }

    private void browse(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return true;
    }
}