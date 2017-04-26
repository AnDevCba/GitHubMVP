package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.model.ReposByUsername;
import com.andevcba.githubmvp.data.repository.Repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link SaveReposInteractor}.
 *
 * @author lucas.nobile
 */
public class SaveReposInteractorTest {

    @Mock
    private Repository repository;

    @Mock
    private ReposByUsername reposByUsername;

    @InjectMocks
    private SaveReposInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_shouldSaveReposByUsername() {
        // When
        interactor.execute(reposByUsername);

        // Then
        verify(repository).saveReposByUsername(reposByUsername);
    }
}