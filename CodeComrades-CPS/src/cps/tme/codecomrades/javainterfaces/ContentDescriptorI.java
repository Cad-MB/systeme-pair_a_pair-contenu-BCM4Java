package cps.tme.codecomrades.javainterfaces;

public interface ContentDescriptorI extends ContentTemplateI {
    ContentNodeAddressI getContentNodeAddress();
    long getSize();
    boolean equals(ContentDescriptorI cd);
    boolean match(ContentTemplateI t);
}
