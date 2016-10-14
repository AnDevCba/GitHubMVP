package com.andevcba.githubmvp.presentation.show_repos.model;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;

import com.andevcba.githubmvp.presentation.show_repos.view.ViewType;

/**
 * UI model for a sticky header.
 *
 * @author lucas.nobile
 */
public class StickyHeaderUI implements ViewType, StickyHeader {

    private String name;

    public StickyHeaderUI(String name) {
        this.name = name;
    }

    @Override
    public int getType() {
        return STICKY_HEADER;
    }

    @Override
    public String getName() {
        return name;
    }
}
