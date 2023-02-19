package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.FacadeNodeAddressI;

public class FacadeNodeAddress implements FacadeNodeAddressI {
    private final String nodeManagementURI;

    public FacadeNodeAddress(String nodeManagementURI) {
        this.nodeManagementURI = nodeManagementURI;
    }

    @Override
    public String getNodeManagementURI() {
        return this.nodeManagementURI;
    }

    @Override
    public String getNodeIdentifier() {
        return this.nodeManagementURI;
    }

    @Override
    public boolean isFacade() {
        return true;
    }

    @Override
    public boolean isPeer() {
        return false;
    }
}
