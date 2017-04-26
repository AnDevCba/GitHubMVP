package com.andevcba.githubmvp.domain.di;

import com.andevcba.githubmvp.domain.interactor.LoadReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SaveReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SearchReposByUsernameInteractor;

import dagger.Subcomponent;

/**
 * Component for domain layer.
 *
 * @author lucas.nobile
 */
@Subcomponent
public interface DomainSubComponent {
    LoadReposInteractor loadReposInteractor();

    SaveReposInteractor saveReposInteractor();

    SearchReposByUsernameInteractor searchReposByUsernameInteractor();
}
