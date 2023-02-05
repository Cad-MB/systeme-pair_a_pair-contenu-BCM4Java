package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.ContentTemplateI;

import java.util.Set;

public class ContentTemplate implements ContentTemplateI {
    private String title;
    private String albumTitle;
    private Set<String> interpreters;
    private Set<String> composers;

    public ContentTemplate(String title, String albumTitle, Set<String> interpreters, Set<String> composers) {
        this.title = title;
        this.albumTitle = albumTitle;
        this.interpreters = interpreters;
        this.composers = composers;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAlbumTitle() {
        return this.albumTitle;
    }

    public Set<String> getInterpreters() {
        return this.interpreters;
    }

    public Set<String> getComposers() {
        return this.composers;
    }
}
