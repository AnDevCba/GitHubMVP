package andevcba.com.githubmvp.data.repository;

import java.util.List;
import java.util.TreeMap;

import andevcba.com.githubmvp.data.model.Repo;

/**
 * Callback for repo response or error.
 *
 * @author lucas.nobile
 */
public interface ReposCallback {
    void onResponse(TreeMap<String, List<Repo>> reposByUsername);

    void onError(String error);
}
