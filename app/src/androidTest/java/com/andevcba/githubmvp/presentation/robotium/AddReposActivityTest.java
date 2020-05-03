package com.andevcba.githubmvp.presentation.robotium;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.andevcba.githubmvp.R;
import com.andevcba.githubmvp.presentation.add_repos.AddReposActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * UI tests for {@link AddReposActivity} using Robotium framework.
 *
 * @author matias.martinez.blanquier
 * @author lucas.nobile
 */
@RunWith(AndroidJUnit4.class)
public class AddReposActivityTest {

    private final static String EMPTY_USERNAME = "";
    private final static String USERNAME = "AnDevCba";

    private Solo solo;

    @Rule
    public ActivityTestRule<AddReposActivity> activityTestRule =
            new ActivityTestRule<>(AddReposActivity.class);


    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityTestRule.getActivity());
        //Unlock the lock screen
        solo.unlockScreen();
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void addReposActivity_onLaunch_shouldDisplayEditTextToEnterUsername() {
        //Given activity automatically launched
        solo.assertCurrentActivity(AddReposActivity.TAG, AddReposActivity.class);

        //When user doesn't interact with the view

        //Then
//        assertTrue(solo.waitForView(solo.getView(R.id.tv_username)));
    }

    @Test
    public void tapOnSearchForReposButton_withEmptyUsername_shouldShowEmptyView() {
        // Given Add repos screen is shown

        // When the user type an empty username and tap on search for repos button
//        solo.typeText((EditText) solo.getView(R.id.tv_username), EMPTY_USERNAME);
//        solo.clickOnView(solo.getView(R.id.btn_search_repos));

        // Then show Snackbar with error message
        assertTrue(solo.waitForText(solo.getString(R.string.empty_username_error_message)));
    }

    @Test
    public void tapOnSearchForReposButton_withValidUsername_shouldShowReposByUsername() {
        // Given Add repos screen is shown

        // When the user type an empty username and tap on search for repos button
//        solo.typeText((EditText) solo.getView(R.id.tv_username), USERNAME);
//        solo.clickOnView(solo.getView(R.id.btn_search_repos));

        // Then verify the repos are displayed on screen
        assertTrue(solo.waitForText("andevcba"));
        assertTrue(solo.waitForText("repo1"));
        assertTrue(solo.waitForText("repo2"));

        // and save button is displayed as well
        assertTrue(solo.waitForView(solo.getView(R.id.fab_save_repos)));
    }
}