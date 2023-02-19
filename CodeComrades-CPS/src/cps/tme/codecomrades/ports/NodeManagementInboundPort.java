package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.components.Facade;
import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

import java.util.Set;

public class NodeManagementInboundPort extends AbstractInboundPort implements NodeManagementCI {
    public NodeManagementInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, NodeManagementCI.class, owner);
        assert uri != null;
    }

    public NodeManagementInboundPort(ComponentI owner) throws Exception {
        super(NodeManagementCI.class, owner);
    }

    @Override
    public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
        return this.owner.handleRequest(new AbstractComponent.AbstractService<Set<PeerNodeAddressI>>() {
            @Override
            public Set<PeerNodeAddressI> call() throws Exception {
                return ((Facade) this.getServiceOwner()).join(a);
            }
        });
    }

    @Override
    public void leave(PeerNodeAddressI a) throws Exception {
        ((Facade)this.getOwner()).leave(a);
    }
}
