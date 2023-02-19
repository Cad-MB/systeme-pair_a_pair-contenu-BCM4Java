package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.ContentNodeAddressI;

public class ContentNodeAddress extends PeerNodeAddress implements ContentNodeAddressI {
    private final String contentManagementURI;
    public ContentNodeAddress(String nodeURI, String contentManagementURI) {
        super(nodeURI);
        this.contentManagementURI = contentManagementURI;
    }

    @Override
    public String getContentManagementURI() {
        return this.contentManagementURI;
    }
}
