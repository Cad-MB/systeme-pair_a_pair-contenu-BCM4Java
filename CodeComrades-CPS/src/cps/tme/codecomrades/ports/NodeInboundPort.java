package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.components.Peer;
import cps.tme.codecomrades.interfaces.NodeCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class NodeInboundPort extends AbstractInboundPort implements NodeCI {
    public NodeInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, NodeCI.class, owner);
        assert uri != null;
    }

    public NodeInboundPort(ComponentI owner) throws Exception {
        super(NodeCI.class, owner);
    }

    @Override
    public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception {
        //return ((Peer)this.getOwner()).connect(a);
        return this.owner.handleRequest(new AbstractComponent.AbstractService<PeerNodeAddressI>() {
            @Override
            public PeerNodeAddressI call() throws Exception {
                return ((Peer)this.getServiceOwner()).connect(a);
            }
        });
    }

    @Override
    public void disconnect(PeerNodeAddressI a) {

    }
}
