package com.andevcba.githubmvp.presentation.di;

import com.andevcba.githubmvp.data.di.DataModule;
import com.andevcba.githubmvp.domain.di.DomainModule;
import com.andevcba.githubmvp.presentation.add_repos.AddReposFragment;
import com.andevcba.githubmvp.presentation.add_repos.AddReposPresenter;
import com.andevcba.githubmvp.presentation.show_repos.presenter.ShowReposPresenter;
import com.andevcba.githubmvp.presentation.show_repos.view.ShowReposFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component whose life cycle if the life cycle of the application.
 *
 * @author lucas.nobile
 */
@Singleton
@Component(
        modules = {DataModule.class, DomainModule.class, PresentationModule.class}
)
public interface PresentationComponent {
    void inject(ShowReposFragment showReposFragment);

    void inject(AddReposFragment addReposFragment);

    AddReposPresenter addReposPresenter();

    ShowReposPresenter showReposPresenter();
}
