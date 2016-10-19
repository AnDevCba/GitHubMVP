package com.andevcba.githubmvp.data.cache;

import android.support.annotation.VisibleForTesting;

import com.andevcba.githubmvp.data.model.Repo;

import java.util.List;
import java.util.TreeMap;

/**
 * {@link ReposCache} implementation.
 *
 * @author lucas.nobile
 */
public class ReposCacheImpl implements ReposCache {

    /**
     * This method has reduced visibility for testing
     * and is only visible to tests in the same package.
     */
    @VisibleForTesting
    TreeMap<String, List<Repo>> cachedRepos; // username, list of repos

    public ReposCacheImpl() {
        cachedRepos = new TreeMap<>();
    }

    @Override
    public void put(String username, List<Repo> repos) {
        cachedRepos.put(username, repos);
    }

    @Override
    public List<Repo> get(String username) {
        return cachedRepos.get(username);
    }

    @Override
    public TreeMap<String, List<Repo>> getAll() {
        return cachedRepos;
    }

    @Override
    public boolean isCached(String username) {
        return cachedRepos.containsKey(username);
    }
}
