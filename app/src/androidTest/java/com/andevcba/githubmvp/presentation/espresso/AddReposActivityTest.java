package com.andevcba.githubmvp.presentation.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.andevcba.githubmvp.R;
import com.andevcba.githubmvp.presentation.add_repos.AddReposActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkArgument;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * UI tests for {@link AddReposActivity} using Espresso framework.
 *
 * @author lucas.nobile
 */
public class AddReposActivityTest {

    private final static String EMPTY_USERNAME = "";
    private final static String USERNAME = "AnDevCba";

    @Rule
    public ActivityTestRule<AddReposActivity> mActivityTestRule = new ActivityTestRule<>(AddReposActivity.class);

    @Test
    public void addReposActivity_onLaunch_shouldDisplayEditTextToEnterUsername() {
        //Given activity automatically launched

        //When user doesn't interact with the view

        //Then
        onView(withId(R.id.tv_username))
                .check(matches(isDisplayed()));
    }

    @Test
    public void tapOnSearchForReposButton_withEmptyUsername_shouldShowEmptyView() {
        // Given Add repos screen is shown
        onView(allOf(withId(R.id.tv_username), isDisplayed()))
                .perform(click());

        // When the user type an empty username and tap on search for repos button
        onView(allOf(withId(R.id.tv_username), isDisplayed()))
                .perform(typeText(EMPTY_USERNAME), closeSoftKeyboard());
        onView(allOf(withId(R.id.btn_search_repos), withText(R.string.search_repos_button),
                isDisplayed()))
                .perform(click());

        // Then show Snackbar with error message
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(R.string.empty_username_error_message),
                isDisplayed()));
    }

    @Test
    public void tapOnSearchForReposButton_withValidUsername_shouldShowReposByUsername() {
        // Given Add repos screen is shown

        // When the user types a valid username and then taps on search for repos button
        onView(allOf(withId(R.id.tv_username), isDisplayed()))
                .perform(typeText(USERNAME), closeSoftKeyboard());
        onView(allOf(withId(R.id.btn_search_repos), withText(R.string.search_repos_button),
                isDisplayed()))
                .perform(click());

        // Then verify the repos are displayed on screen
        onView(withItemText("andevcba")).check(matches(isDisplayed())); // Sticky header
        onView(withItemText("repo1")).check(matches(isDisplayed())); // Repo 1
        onView(withItemText("repo2")).check(matches(isDisplayed())); // Repo 2
        // and save button is displayed as well
        onView(allOf(withId(R.id.fab_save_repos), isDisplayed()));
    }

    /**
     * A custom {@link Matcher} which matches an item in a {@link RecyclerView} by its text.
     * <p>
     * <p>
     * View constraints:
     * <ul>
     * <li>View must be a child of a {@link RecyclerView}
     * <ul>
     *
     * @param itemText the text to match
     * @return Matcher that matches text in the given view
     */
    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText))
                        .matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA Recycler View with text " + itemText);
            }
        };
    }
}
