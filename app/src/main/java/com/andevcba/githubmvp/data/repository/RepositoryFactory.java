package com.andevcba.githubmvp.data.repository;

import com.andevcba.githubmvp.data.cache.ReposCache;
import com.andevcba.githubmvp.data.di.InjectionName;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Creates a concrete {@link Repository} by a given username;
 *
 * @author lucas.nobile
 */
@Singleton
public class RepositoryFactory {

    private ReposCache reposCache;
    private Repository inMemoryRepository; // TODO Lazy
    private Repository networkRepository; // TODO Lazy

    @Inject
    public RepositoryFactory(ReposCache reposCache,
                             @Named(InjectionName.IN_MEMORY_NAME) Repository inMemoryRepository,
                             @Named(InjectionName.NETWORK_NAME) Repository networkRepository) {
        this.reposCache = reposCache;
        this.inMemoryRepository = inMemoryRepository;
        this.networkRepository = networkRepository;
    }

    public Repository create(String username) {
        if (reposCache.isCached(username)) {
            return inMemoryRepository;
        }
        return networkRepository;
    }
}
