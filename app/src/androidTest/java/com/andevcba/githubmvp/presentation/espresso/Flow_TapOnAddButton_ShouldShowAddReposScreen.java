package com.andevcba.githubmvp.presentation.espresso;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.andevcba.githubmvp.R;
import com.andevcba.githubmvp.presentation.add_repos.AddReposActivity;
import com.andevcba.githubmvp.presentation.show_repos.view.ShowReposActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

/**
 * UI tests for {@link AddReposActivity} using Espresso test recorder.
 *
 * @author lucas.nobile
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class Flow_TapOnAddButton_ShouldShowAddReposScreen {

    @Rule
    public ActivityTestRule<ShowReposActivity> mActivityTestRule = new ActivityTestRule<>(ShowReposActivity.class);

    @Test
    public void tapOnAddButton_shouldShowAddReposScreen() {
        ViewInteraction floatingActionButton = onView(allOf(withId(R.id.fab_add_repos), withParent(withId(R.id.coordinatorLayout)), isDisplayed()));
        floatingActionButton.perform(click());

//        onView(allOf(withId(R.id.tv_username), withText(R.string.enter_username_hint), isDisplayed()));
    }
}
