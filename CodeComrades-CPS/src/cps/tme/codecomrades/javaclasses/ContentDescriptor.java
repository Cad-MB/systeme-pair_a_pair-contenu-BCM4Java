package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;

import java.util.Set;

public class ContentDescriptor extends ContentTemplate implements ContentDescriptorI {
    private final long size;
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
        return this.getTitle().equals(cd.getTitle()) &&
                this.getAlbumTitle().equals(cd.getAlbumTitle()) &&
                this.getInterpreters().equals(cd.getInterpreters()) &&
                this.getComposers().equals(cd.getComposers()) &&
                this.size == cd.getSize();
    }

    public boolean match(ContentTemplateI t) {
        return this.getTitle().equals(t.getTitle()) ||
                this.getAlbumTitle().equals(t.getAlbumTitle()) ||
                this.getInterpreters().containsAll(t.getInterpreters()) ||
                this.getComposers().containsAll(t.getComposers());
    }
}
