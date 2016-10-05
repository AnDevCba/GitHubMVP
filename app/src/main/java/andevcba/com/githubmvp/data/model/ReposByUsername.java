package andevcba.com.githubmvp.data.model;

import java.util.List;
import java.util.TreeMap;

/**
 * Model class for repos by username.
 * <p>
 * True if is cached, flase otherwise.
 *
 * @author lucas.nobile
 */
public class ReposByUsername {

    private TreeMap<String, List<Repo>> reposByUsername;
    private boolean cached;

    public ReposByUsername(TreeMap<String, List<Repo>> reposByUsername, boolean cached) {
        this.reposByUsername = reposByUsername;
        this.cached = cached;
    }

    public TreeMap<String, List<Repo>> getReposByUsername() {
        return reposByUsername;
    }

    public boolean isCached() {
        return cached;
    }
}
