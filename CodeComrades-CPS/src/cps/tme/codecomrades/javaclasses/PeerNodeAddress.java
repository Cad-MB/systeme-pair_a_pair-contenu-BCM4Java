package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;

public class PeerNodeAddress implements PeerNodeAddressI {
    private final String nodeURI;

    public PeerNodeAddress(String nodeURI) {
        this.nodeURI = nodeURI;
    }

    @Override
    public String getNodeIdentifier() {
        return this.nodeURI;
    }

    @Override
    public boolean isFacade() {
        return false;
    }

    @Override
    public boolean isPeer() {
        return true;
    }

    @Override
    public String getNodeURI() {
        return this.nodeURI;
    }
}
