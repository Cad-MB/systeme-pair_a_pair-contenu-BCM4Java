package cps.tme.codecomrades.components;

import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import cps.tme.codecomrades.ports.NodeManagementInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;

import java.util.HashSet;
import java.util.Set;

@OfferedInterfaces(offered = {NodeManagementCI.class})

public class Facade extends AbstractComponent {

    protected final NodeManagementInboundPort nodeManagementInboundPort;
    protected Set<PeerNodeAddressI> rootNodes;

    protected Facade(String nodeManagementInboundPortURI) throws Exception {
        super(1, 0);
        assert nodeManagementInboundPortURI != null;

        this.nodeManagementInboundPort = new NodeManagementInboundPort(nodeManagementInboundPortURI, this);
        this.nodeManagementInboundPort.publishPort();

        this.getTracer().setTitle("Facade");

        AbstractComponent.checkImplementationInvariant(this);
        AbstractComponent.checkInvariant(this);
        this.rootNodes = new HashSet<>();
    }

    protected Facade(String reflectionInboundPortURI, String nodeManagementInboundPortURI) throws Exception {
        super(reflectionInboundPortURI, 1, 0);

        this.nodeManagementInboundPort = new NodeManagementInboundPort(nodeManagementInboundPortURI, this);
        this.nodeManagementInboundPort.publishPort();

        this.getTracer().setTitle("Facade");
        this.rootNodes = new HashSet<>();
    }

    public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
        System.out.println("Added peer node " + a.getNodeURI() + " as root.");
        Set<PeerNodeAddressI> neighborsOfA = new HashSet<>(this.rootNodes);
        this.rootNodes.add(a);
        System.out.println("Current root nodes of facade: " + this.rootNodes.toString());
        System.out.println("Neighbors returned: " + neighborsOfA);
        return neighborsOfA;
    }

    @Override
    public synchronized void finalise() throws Exception {
        this.nodeManagementInboundPort.unpublishPort();
        super.finalise();
    }
}
