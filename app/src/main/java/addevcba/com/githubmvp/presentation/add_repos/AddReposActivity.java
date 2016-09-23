package addevcba.com.githubmvp.presentation.add_repos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import addevcba.com.githubmvp.R;

/**
 * Displays a screen to search for and to show repos by username.
 *
 * @author lucas.nobile
 */
public class AddReposActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repos);

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
    }

    private void initFragment(Fragment addReposFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, addReposFragment);
        transaction.commit();
    }
}
