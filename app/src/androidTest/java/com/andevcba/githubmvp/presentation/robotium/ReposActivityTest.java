package com.andevcba.githubmvp.presentation.robotium;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.andevcba.githubmvp.R;
import com.andevcba.githubmvp.presentation.add_repos.AddReposActivity;
import com.andevcba.githubmvp.presentation.show_repos.view.ReposActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Test UI using robotium.
 * Created by Matias on 16/10/2016.
 */
@RunWith(AndroidJUnit4.class)
public class ReposActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<ReposActivity> activityTestRule =
            new ActivityTestRule<>(ReposActivity.class);


    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
        //Unlock the lock screen
        solo.unlockScreen();
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void tapOnAddButtonShouldShowAddReposScreen() {
        solo.clickOnView(solo.getView(R.id.fab_add_repos));
        solo.assertCurrentActivity(AddReposActivity.TAG, AddReposActivity.class);
    }

    @Test
    public void tapOnSearchForReposButtonWithNotValidUsernameShouldShowEmptyMessage() {
        solo.clickOnView(solo.getView(R.id.fab_add_repos));
        solo.enterText((EditText) solo.getView(R.id.tv_username), "asd");
        solo.clickOnView(solo.getView(com.andevcba.githubmvp.R.id.btn_search_repos));
        assertTrue(solo.searchText(solo.getString(R.string.empty_search_repos_message)));
        assertTrue(solo.waitForText(solo.getString(R.string.error_searching_for_repos_message)));
    }
}