package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;
import com.andevcba.githubmvp.data.model.ReposByUsername;

/**
 * Base interactor.
 *
 * @author lucas.nobile
 */
public interface Interactor {

    void execute();

    void execute(ReposCallback callback);

    void execute(String username, ReposCallback callback);

    void execute(ReposByUsername reposByUsername);
}
