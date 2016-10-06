package andevcba.com.githubmvp.data.repository;

import java.util.List;
import java.util.TreeMap;

import andevcba.com.githubmvp.data.model.Repo;

/**
 * Represents a cache for Repos.
 *
 * @author lucas.nobile
 */
public interface ReposCache {

    List<Repo> put(String username, List<Repo> repos);

    List<Repo> get(String username);

    TreeMap<String, List<Repo>> getAll();

    boolean isCached(String username);
}
