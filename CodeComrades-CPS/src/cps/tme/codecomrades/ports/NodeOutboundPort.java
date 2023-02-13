package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.interfaces.NodeCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class NodeOutboundPort extends AbstractOutboundPort implements NodeCI {
    public NodeOutboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, NodeCI.class, owner);
        assert uri != null;
    }

    public NodeOutboundPort(ComponentI owner) throws Exception {
        super(NodeCI.class, owner);
    }

    @Override
    public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception {
        return ((NodeCI) this.getConnector()).connect(a);
    }

    @Override
    public void disconnect(PeerNodeAddressI a) {

    }
}
