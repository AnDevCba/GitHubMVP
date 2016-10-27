package com.andevcba.githubmvp.presentation.show_repos.model;

import com.andevcba.githubmvp.presentation.show_repos.view.ViewType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@link ReposByUsernameUI} model.
 *
 * @author lucas.nobile
 */
public class ReposByUsernameUITest {

    private final static String USERNAME = "AnDevCba";
    private final static RepoUI REPO_UI1 = new RepoUI("repo1", "url1");
    private final static RepoUI REPO_UI2 = new RepoUI("repo2", "url2");
    private static List<RepoUI> REPO_UI_LIST = new ArrayList<>();

    private static TreeMap<String, List<RepoUI>> REPOS_BY_USERNAME_UI_MAP;
    private static ReposByUsernameUI uiModel;

    @Before
    public void setUp() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Mocked repo list
        REPO_UI_LIST.add(REPO_UI1);
        REPO_UI_LIST.add(REPO_UI2);

        // Mocked repos by username
        REPOS_BY_USERNAME_UI_MAP = new TreeMap<>();
        REPOS_BY_USERNAME_UI_MAP.put(USERNAME, REPO_UI_LIST);
    }

    @After
    public void tearDown() {
        REPO_UI_LIST.clear();
        REPOS_BY_USERNAME_UI_MAP.clear();
    }

    @Test
    public void getViewTypes_shouldReturnAListOfItems() {
        // Given a stubbed UI Model
        uiModel = new ReposByUsernameUI(REPOS_BY_USERNAME_UI_MAP, true /*is cached*/);

        // When
        List<ViewType> items = uiModel.getViewTypes();

        // Then
        assertNotNull(items);
        assertEquals(3, items.size());
        // This is because we must use matchers on 2nd param
        assertThat(items.get(0), instanceOf(StickyHeaderUI.class));
        assertThat(items.get(0).getType(), is(ViewType.STICKY_HEADER));
        assertEquals(items.get(0).getName(), USERNAME);
        assertThat(items.get(1), instanceOf(RepoUI.class));
        assertThat(items.get(1).getType(), is(ViewType.REPO_ITEM));
        assertEquals(items.get(1).getName(), "repo1");
        assertThat(items.get(2), instanceOf(RepoUI.class));
        assertThat(items.get(2).getType(), is(ViewType.REPO_ITEM));
        assertEquals(items.get(2).getName(), "repo2");
    }
}