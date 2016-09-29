package andevcba.com.githubmvp.presentation.show_repos.view;

/**
 * Set of constants to define a type of view.
 *
 * @author lucas.nobile
 */
public interface ViewType {

    // Constants starting from 1000
    int EMPTY = 1000;
    int STICKY_HEADER = 10001;
    int REPO_ITEM = 1002;

    int getType();

    String getName();
}
