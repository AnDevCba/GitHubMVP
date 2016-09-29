package andevcba.com.githubmvp.data.repository;

import java.util.List;
import java.util.TreeMap;

import andevcba.com.githubmvp.data.model.Repo;

/**
 * {@link ReposCache} implementation.
 *
 * @author lucas.nobile
 */
public class ReposCacheImpl implements ReposCache {

    private TreeMap<String, List<Repo>> cachedRepos; // username, list of repos

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
