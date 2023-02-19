package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.ApplicationNodeAddressI;

public class ApplicationNodeAddress extends FacadeNodeAddress implements ApplicationNodeAddressI {
    private final String contentManagementURI;
    public ApplicationNodeAddress(String nodeManagementURI, String contentManagementURI) {
        super(nodeManagementURI);
        this.contentManagementURI = contentManagementURI;
    }

    @Override
    public String getContentManagementURI() {
        return this.contentManagementURI;
    }
}
