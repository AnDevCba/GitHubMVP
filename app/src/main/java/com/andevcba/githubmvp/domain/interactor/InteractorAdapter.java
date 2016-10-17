package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;

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
}
