package com.andevcba.githubmvp.presentation.show_repos.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.andevcba.githubmvp.presentation.show_repos.view.ViewType;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * UI model for {@link com.andevcba.githubmvp.data.model.ReposByUsername}.
 * Parcelable data to transfer between fragments and to save/restore on orientation changes.
 *
 * @author lucas.nobile
 */
public class ReposByUsernameUI implements Parcelable {

    private TreeMap<String, List<RepoUI>> uiModel;
    private boolean cached;

    public ReposByUsernameUI(TreeMap<String, List<RepoUI>> uiModel, boolean cached) {
        this.uiModel = uiModel;
        this.cached = cached;
    }

    protected ReposByUsernameUI(Parcel in) {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String username = in.readString();
            List<RepoUI> repos = new ArrayList<>();
            in.readTypedList(repos, RepoUI.CREATOR);
            uiModel.put(username, repos);
        }
    }

    public TreeMap<String, List<RepoUI>> getUiModel() {
        return uiModel;
    }

    public boolean isCached() {
        return cached;
    }

    public List<ViewType> getViewTypes() {
        List<ViewType> items = new ArrayList<>();
        if (uiModel != null) {
            for (TreeMap.Entry<String, List<RepoUI>> entry : uiModel.entrySet()) {
                String username = entry.getKey();
                items.add(new StickyHeaderUI(username));
                items.addAll(entry.getValue());
            }
        }
        return items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uiModel.size());
        for (TreeMap.Entry<String, List<RepoUI>> entry : uiModel.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeTypedList(entry.getValue());
        }
    }

    public static final Creator<ReposByUsernameUI> CREATOR = new Creator<ReposByUsernameUI>() {
        @Override
        public ReposByUsernameUI createFromParcel(Parcel in) {
            return new ReposByUsernameUI(in);
        }

        @Override
        public ReposByUsernameUI[] newArray(int size) {
            return new ReposByUsernameUI[size];
        }
    };
}
