package andevcba.com.githubmvp.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Immutable model class for a Repo.
 *
 * @author lucas.nobile
 */
public class Repo {

    private final String name;
    @SerializedName("html_url")
    private final String url;

    public Repo() {
        this.name = "";
        this.url = "";
    }

    public Repo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
