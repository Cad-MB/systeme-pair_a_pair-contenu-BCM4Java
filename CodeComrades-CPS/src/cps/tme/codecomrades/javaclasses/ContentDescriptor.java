package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;

import java.util.Set;

public class ContentDescriptor extends ContentTemplate implements ContentDescriptorI {
    private final long size;
    private String title;
    private String albumTitle;
    private Set<String> interpreters;
    private Set<String> composers;
    private ContentNodeAddressI contentNodeAddress;

    public ContentDescriptor(long size, String title, String albumTitle, Set<String> interpreters, Set<String> composers) {
        super(title,albumTitle,interpreters,composers);
        this.size = size;
    }

    public ContentNodeAddressI getContentNodeAddress() {
        return this.contentNodeAddress;
    }

    public long getSize() {
        return this.size;
    }

    public boolean equals(ContentDescriptorI cd) {
        return this.title.equals(cd.getTitle()) &&
                this.albumTitle.equals(cd.getAlbumTitle()) &&
                this.interpreters.equals(cd.getInterpreters()) &&
                this.composers.equals(cd.getComposers()) &&
                this.size == cd.getSize();
    }

    public boolean match(ContentTemplateI t) {
        return this.title.equals(t.getTitle()) ||
                this.albumTitle.equals(t.getAlbumTitle()) ||
                this.interpreters.equals(t.getInterpreters()) ||
                this.composers.equals(t.getComposers());
    }
}
