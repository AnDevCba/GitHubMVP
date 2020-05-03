package com.andevcba.githubmvp.presentation.show_repos.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.andevcba.githubmvp.R;

/**
 * Displays a screen to show repos by username if any.
 *
 * @author lucas.nobile
 */
public class ShowReposActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_repos);
        setUpToolbar();

        if (savedInstanceState == null) {
            initFragment(ShowReposFragment.newInstance());
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // TODO Use Toolbar?
    }

    private void initFragment(Fragment showReposFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, showReposFragment);
        transaction.commit();
    }
}
