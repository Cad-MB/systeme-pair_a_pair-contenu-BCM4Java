package cps.tme.codecomrades.javaclasses;

import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;

public class PeerNodeAddress implements PeerNodeAddressI {
    private final String nodeURI;
    private final String nodeIdentifier;

    public PeerNodeAddress(String nodeURI, String nodeIdentifier) {
        this.nodeURI = nodeURI;
        this.nodeIdentifier = nodeIdentifier;
    }

    @Override
    public String getNodeIdentifier() {
        return this.nodeIdentifier;
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
