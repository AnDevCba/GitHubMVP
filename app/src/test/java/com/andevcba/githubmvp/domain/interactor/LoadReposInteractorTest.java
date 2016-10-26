package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.repository.Repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for  {@link LoadReposInteractor}.
 *
 * @author #AnDevCba
 */
public class LoadReposInteractorTest {

    @Mock
    private Repository repository;

    @Mock
    private ReposCallback reposCallback;

    @InjectMocks
    private LoadReposInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_shouldLoadAllRepos() {

        // When
        interactor.execute(reposCallback);

        // Then
        verify(repository).loadAllRepos(reposCallback);
    }

}