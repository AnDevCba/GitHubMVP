package com.andevcba.githubmvp.presentation.show_repos.view;

import com.andevcba.githubmvp.presentation.add_repos.AddReposActivity;
import com.robotium.solo.Solo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
public class ShowReposActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<AddReposActivity> activityTestRule =
            new ActivityTestRule<>(AddReposActivity.class);


    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void searchRepos() throws Exception{
        //Unlock the lock screen
        solo.unlockScreen();
        //Click on action menu item add
        solo.clickOnButton("+");
        solo.assertCurrentActivity("wrong activity", ShowReposActivity.class);
        solo.enterText(0, "lucaslabs");
        solo.clickOnButton("SEARCH FOR REPOS");
        assertTrue(solo.searchText("android101"));
    }

}