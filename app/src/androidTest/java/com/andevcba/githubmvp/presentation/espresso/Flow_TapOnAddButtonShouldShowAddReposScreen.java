package com.andevcba.githubmvp.presentation.espresso;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.andevcba.githubmvp.R;
import com.andevcba.githubmvp.presentation.show_repos.view.ReposActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Flow_TapOnAddButtonShouldShowAddReposScreen {

    @Rule
    public ActivityTestRule<ReposActivity> mActivityTestRule = new ActivityTestRule<>(ReposActivity.class);

    @Test
    public void tapOnAddButtonShouldShowAddReposScreen() {
        ViewInteraction floatingActionButton = onView(allOf(withId(R.id.fab_add_repos), withParent(withId(R.id.coordinatorLayout)), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction editText = onView(allOf(withId(R.id.tv_username), withText(R.string.enter_username_hint), isDisplayed()));
    }
}
