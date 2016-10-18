package com.andevcba.githubmvp.data;

import com.andevcba.githubmvp.data.model.ReposByUsername;

/**
 * Callback for repo response or error.
 *
 * @author lucas.nobile
 */
public interface ReposCallback {

    void onResponse(ReposByUsername response);

    void onError(String error);
}
