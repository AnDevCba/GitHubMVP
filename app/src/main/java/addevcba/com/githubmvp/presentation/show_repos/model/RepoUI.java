package addevcba.com.githubmvp.presentation.show_repos.model;

import addevcba.com.githubmvp.data.model.Repo;
import addevcba.com.githubmvp.presentation.show_repos.view.ViewType;

/**
 * UI model of {@link Repo}.
 *
 * @author lucas.nobile
 */
public class RepoUI extends Repo implements ViewType {

    public RepoUI(long id, String name, String url, String createdAt, int stars, String language) {
        super(id, name, url, createdAt, stars, language);
    }

    @Override
    public int getType() {
        return REPO_ITEM;
    }
}
