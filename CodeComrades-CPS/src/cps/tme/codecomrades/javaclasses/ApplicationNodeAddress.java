package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.ApplicationNodeAddressI;

public class ApplicationNodeAddress extends FacadeNodeAddress implements ApplicationNodeAddressI {
    private final String facadeContentManagementURI;
    public ApplicationNodeAddress(String nodeManagementURI, String facadeContentManagementURI) {
        super(nodeManagementURI);
        this.facadeContentManagementURI = facadeContentManagementURI;
    }

    @Override
    public String getContentManagementURI() {
        return this.facadeContentManagementURI;
    }
}
