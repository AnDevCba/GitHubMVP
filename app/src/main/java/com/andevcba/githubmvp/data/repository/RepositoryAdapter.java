package com.andevcba.githubmvp.data.repository;

import com.andevcba.githubmvp.data.model.ReposByUsername;

/**
 * Adapter class for {@link Repository}.
 *
 * @author lucas.nobile
 */

public class RepositoryAdapter implements Repository {
    
    @Override
    public void searchReposByUsername(String username, ReposCallback callback) {
        // Add behavior on subclass.
    }

    @Override
    public void saveReposByUsername(ReposByUsername reposByUsername) {
        // Add behavior on subclass.
    }

    @Override
    public void loadAllRepos(ReposCallback callback) {
        // Add behavior on subclass.
    }
}
