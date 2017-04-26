package com.andevcba.githubmvp.presentation.di;

import android.app.Application;

import com.andevcba.githubmvp.domain.interactor.LoadReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SaveReposInteractor;
import com.andevcba.githubmvp.domain.interactor.SearchReposByUsernameInteractor;
import com.andevcba.githubmvp.presentation.add_repos.AddReposPresenter;
import com.andevcba.githubmvp.presentation.show_repos.presenter.ShowReposPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Module class to provide dependencies for presentation layer.
 *
 * @author lucas.nobile
 */
@Module
public class PresentationModule {

    private Application application;

    public PresentationModule(Application application) {
        this.application = application;
    }

    // TODO No needed
    @Provides
    AddReposPresenter provideAddReposPresenter(SearchReposByUsernameInteractor searchReposInteractor, SaveReposInteractor saveReposInteractor) {
        return new AddReposPresenter(searchReposInteractor, saveReposInteractor);
    }

    // TODO No needed
    @Provides
    ShowReposPresenter provideShowReposPresenter(LoadReposInteractor loadReposInteractor) {
        return new ShowReposPresenter(loadReposInteractor);
    }
}
