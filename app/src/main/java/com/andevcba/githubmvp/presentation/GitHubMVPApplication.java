package com.andevcba.githubmvp.presentation;

import android.app.Application;

import com.andevcba.githubmvp.data.di.DataModule;
import com.andevcba.githubmvp.domain.di.DomainModule;
import com.andevcba.githubmvp.presentation.di.DaggerPresentationComponent;
import com.andevcba.githubmvp.presentation.di.PresentationComponent;
import com.andevcba.githubmvp.presentation.di.PresentationModule;

/**
 * Main entry point of the app.
 *
 * @author lucas.nobile
 */
public class GitHubMVPApplication extends Application {

    private PresentationComponent presentationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        createPresentationComponent();
    }

    private void createPresentationComponent() {
        presentationComponent = DaggerPresentationComponent.builder()
                .dataModule(new DataModule())
                .domainModule(new DomainModule())
                .presentationModule(new PresentationModule(this))
                .build();
    }

    public PresentationComponent getPresentationComponent() {
        return presentationComponent;
    }
}
