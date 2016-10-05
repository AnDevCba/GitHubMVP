package andevcba.com.githubmvp.data.repository;

import andevcba.com.githubmvp.data.model.ReposByUsername;

/**
 * Callback for repo response or error.
 *
 * @author lucas.nobile
 */
public interface ReposCallback {
    void onResponse(ReposByUsername response);

    void onError(String error);
}
