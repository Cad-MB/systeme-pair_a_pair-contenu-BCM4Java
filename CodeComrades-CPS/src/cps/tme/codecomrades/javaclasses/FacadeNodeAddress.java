package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.FacadeNodeAddressI;

public class FacadeNodeAddress implements FacadeNodeAddressI {
    private final String nodeManagementURI;
    private final String nodeIdentifier;

    public FacadeNodeAddress(String nodeManagementURI, String nodeIdentifier) {
        this.nodeManagementURI = nodeManagementURI;
        this.nodeIdentifier = nodeIdentifier;
    }

    @Override
    public String getNodeManagementURI() {
        return this.nodeManagementURI;
    }

    @Override
    public String getNodeIdentifier() {
        return this.nodeIdentifier;
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
