package com.andevcba.githubmvp.domain.interactor;

import com.andevcba.githubmvp.data.ReposCallback;

/**
 * Base interactor.
 *
 * @author lucas.nobile
 */
public interface Interactor {

    void execute();

    void execute(ReposCallback callback);
}
