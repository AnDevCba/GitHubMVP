package andevcba.com.githubmvp.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Immutable model class for a Repo.
 *
 * @author lucas.nobile
 */
public class Repo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final long id;
    private final String name;
    @SerializedName("created_at")
    private final String createdAt;
    @SerializedName("html_url")
    private final String url;
    @SerializedName("stargazers_count")
    private final int stars;
    private final String language;

    public Repo() {
        this.id = 0;
        this.name = "";
        this.url = "";
        this.createdAt = "";
        this.stars = 0;
        this.language = "";
    }

    public Repo(long id, String name, String url, String createdAt, int stars, String language) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.createdAt = createdAt;
        this.stars = stars;
        this.language = language;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getStars() {
        return stars;
    }

    public String getLanguage() {
        return language;
    }
}
