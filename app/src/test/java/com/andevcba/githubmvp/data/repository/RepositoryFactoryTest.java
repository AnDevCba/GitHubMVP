package com.andevcba.githubmvp.data.repository;

import com.andevcba.githubmvp.data.cache.ReposCache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Unit tests for {@link RepositoryFactory}.
 *
 * @author lucas.nobile
 */
public class RepositoryFactoryTest {

    @Mock
    private ReposCache reposCache;

    @Mock
    private InMemoryRepository inMemoryRepository;

    @Mock
    private NetworkRepository networkRepository;

    @InjectMocks
    private RepositoryFactory repositoryFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_withUserInCache_shouldReturnInMemoryRepository() {
        // Given
        given(reposCache.isCached(anyString())).willReturn(true);

        // When
        Repository repository = repositoryFactory.create(anyString());

        // Then
        assertThat(repository, is(notNullValue()));
        // FIXME
        // assertThat(repository, is(instanceOf(InMemoryRepository.class)));
    }

    @Test
    public void create_withUserNotInCache_shouldReturnNetworkRepository() {
        // Given
        given(reposCache.isCached(anyString())).willReturn(false);

        // When
        Repository repository = repositoryFactory.create(anyString());

        // Then
        assertThat(repository, is(notNullValue()));
        // FIXME
        // assertThat(repository, is(instanceOf(NetworkRepository.class)));
    }
}