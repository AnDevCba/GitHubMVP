package com.andevcba.githubmvp.data.repository;

import java.util.List;
import java.util.TreeMap;

import com.andevcba.githubmvp.data.model.Repo;

/**
 * Represents a cache for Repos.
 *
 * @author lucas.nobile
 */
public interface ReposCache {

    void put(String username, List<Repo> repos);

    List<Repo> get(String username);

    TreeMap<String, List<Repo>> getAll();

    boolean isCached(String username);
}
