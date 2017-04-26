package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.repository.InMemoryRepository;
import com.andevcba.githubmvp.data.repository.NetworkRepository;
import com.andevcba.githubmvp.data.repository.RepositoryFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link SearchReposByUsernameInteractor}.
 *
 * @author lucas.nobile
 */
public class SearchReposByUsernameInteractorTest {

    private final static String USERNAME = "AnDevCba";
    private final static String NOT_IN_CACHE_USERNAME = "NotInCache";

    @Mock
    private ReposCallback reposCallback;

    @Mock
    private ReposCache reposCache;

    @Mock
    private RepositoryFactory repositoryFactory;

    @Mock
    private NetworkRepository networkRepository;

    @Mock
    private InMemoryRepository inMemoryRepository;

    @InjectMocks
    private SearchReposByUsernameInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_with_username_not_in_cache_shouldRetrieveReposByUsernameFromNetwork() throws IOException {
        // Given a stubbed repos cache with username not in cache
        when(repositoryFactory.create(anyString())).thenReturn(networkRepository);

        // When
        interactor.execute(NOT_IN_CACHE_USERNAME, reposCallback);

        // Then
        verify(networkRepository).searchReposByUsername(NOT_IN_CACHE_USERNAME, reposCallback);
    }

    @Test
    public void execute_with_cached_username_shouldRetrieveReposByUsernameFromInMemoryCache() {
        // Given a stubbed repos cache with username cached
        when(repositoryFactory.create(anyString())).thenReturn(inMemoryRepository);
        when(reposCache.isCached(USERNAME)).thenReturn(true);

        // When
        interactor.execute(USERNAME, reposCallback);

        // Then
        verify(inMemoryRepository).searchReposByUsername(USERNAME, reposCallback);
    }
}