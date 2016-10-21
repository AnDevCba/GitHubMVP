package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.model.ReposByUsername;

/**
 * Adapter class for {@link Interactor}.
 *
 * @author lucas.nobile
 */
public class InteractorAdapter implements Interactor {

    @Override
    public void execute() {
        // Add behavior on subclass.
    }

    @Override
    public void execute(ReposCallback callback) {
        // Add behavior on subclass.
    }

    @Override
    public void execute(String username, ReposCallback callback) {
        // Add behavior on subclass.
    }

    @Override
    public void execute(ReposByUsername reposByUsername) {
        // Add behavior on subclass.
    }
}
