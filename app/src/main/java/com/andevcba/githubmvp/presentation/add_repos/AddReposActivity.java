package com.andevcba.githubmvp.presentation.add_repos;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.andevcba.githubmvp.R;

/**
 * Displays a screen to search for and to show repos by username.
 *
 * @author lucas.nobile
 */
public class AddReposActivity extends AppCompatActivity {

    public static final String TAG = AddReposActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repos);
        ActivityCompat.postponeEnterTransition(this);
        setUpToolbar();

        if (savedInstanceState == null) {
            initFragment(AddReposFragment.newInstance());
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.add_repos_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initFragment(Fragment addReposFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, addReposFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
