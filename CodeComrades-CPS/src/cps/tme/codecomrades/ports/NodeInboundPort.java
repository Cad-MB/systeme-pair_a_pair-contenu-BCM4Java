package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.components.Peer;
import cps.tme.codecomrades.interfaces.NodeCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

import java.util.Set;

public class NodeInboundPort extends AbstractInboundPort implements NodeCI {
    public NodeInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, NodeCI.class, owner);
        assert uri != null;
    }

    public NodeInboundPort(ComponentI owner) throws Exception {
        super(NodeCI.class, owner);
    }

    @Override
    public PeerNodeAddressI connectOld(PeerNodeAddressI a) throws Exception {
        return ((Peer) this.getOwner()).connect(a);
        /*return this.owner.handleRequest(new AbstractComponent.AbstractService<PeerNodeAddressI>() {
            @Override
            public PeerNodeAddressI call() throws Exception {
                return ((Peer)this.getServiceOwner()).connect(a);
            }
        });*/
    }

    @Override
    public void connect(PeerNodeAddressI peer) throws Exception {
        this.owner.runTask(new AbstractComponent.AbstractTask() {
            @Override
            public void run() {
                try {
                    ((Peer)this.getTaskOwner()).connect(peer);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void disconnectOld(PeerNodeAddressI a) throws Exception {
        ((Peer) this.getOwner()).disconnect(a);
    }

    @Override
    public void disconnect(PeerNodeAddressI neighbor) throws Exception {
        this.owner.runTask(new AbstractComponent.AbstractTask() {
            @Override
            public void run() {
                try {
                    ((Peer)this.getTaskOwner()).disconnect(neighbor);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void acceptNeighbors(Set<PeerNodeAddressI> neighbors) {
        this.owner.runTask(new AbstractComponent.AbstractTask() {
            @Override
            public void run() {
                ((Peer)this.getTaskOwner()).acceptNeighbors();
            }
        });
    }

    @Override
    public void acceptConnected(PeerNodeAddressI neighbor) {
        this.owner.runTask(new AbstractComponent.AbstractTask() {
            @Override
            public void run() {
                ((Peer)this.getTaskOwner()).acceptConnected();
            }
        });
    }
}
