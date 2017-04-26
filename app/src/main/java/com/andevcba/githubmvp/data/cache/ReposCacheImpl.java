package com.andevcba.githubmvp.data.cache;

import com.andevcba.githubmvp.data.model.Repo;

import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link ReposCache} implementation.
 *
 * @author lucas.nobile
 */
@Singleton
public class ReposCacheImpl implements ReposCache {

    private TreeMap<String, List<Repo>> cachedRepos; // username, list of repos

    @Inject
    public ReposCacheImpl(TreeMap<String, List<Repo>> cachedRepos) {
        this.cachedRepos = cachedRepos;
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

    @Override
    public int size() {
        return cachedRepos.size();
    }
}
