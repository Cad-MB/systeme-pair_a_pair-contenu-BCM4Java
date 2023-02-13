package cps.tme.codecomrades.components;

import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import cps.tme.codecomrades.ports.NodeManagementInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;

import java.util.Set;

@OfferedInterfaces(offered = {NodeManagementCI.class})

public class Facade extends AbstractComponent {

    protected final NodeManagementInboundPort inboundPort;

    protected Facade(String nodeManagementInboundPortURI) throws Exception {
        super(1, 0);
        assert nodeManagementInboundPortURI != null;

        this.inboundPort = new NodeManagementInboundPort(nodeManagementInboundPortURI, this);
        this.inboundPort.publishPort();

        this.getTracer().setTitle("Facade");

        AbstractComponent.checkImplementationInvariant(this);
        AbstractComponent.checkInvariant(this);
    }

    protected Facade(String reflectionInboundPortURI, String nodeManagementInboundPortURI) throws Exception {
        super(reflectionInboundPortURI, 1, 0);

        this.inboundPort = new NodeManagementInboundPort(nodeManagementInboundPortURI, this);
        this.inboundPort.publishPort();

        this.getTracer().setTitle("Facade");
    }

    public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
        System.out.println("URI = " + a.getNodeURI());
        return null;
    }

    @Override
    public synchronized void finalise() throws Exception {
        this.inboundPort.unpublishPort();
        super.finalise();
    }
}
