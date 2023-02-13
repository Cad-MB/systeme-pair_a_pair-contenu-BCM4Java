package cps.tme.codecomrades.components;

import cps.tme.codecomrades.connectors.NodeManagementConnector;
import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javaclasses.PeerNodeAddress;
import cps.tme.codecomrades.javainterfaces.NodeAddressI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import cps.tme.codecomrades.ports.NodeManagementOutboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

import java.util.Set;

@RequiredInterfaces(required = {NodeManagementCI.class})
public class Peer extends AbstractComponent {

    protected NodeManagementOutboundPort outboundPort;
    protected String nodeManagementInboundPortURI;


    protected Peer(String nodeManagementInboundPortURI) throws Exception {
        super(1, 0);
        this.nodeManagementInboundPortURI = nodeManagementInboundPortURI;
        this.outboundPort = new NodeManagementOutboundPort(this);
        this.outboundPort.publishPort();

        this.getTracer().setTitle("Peer");
    }

    protected Peer(String uri, String nodeManagementInboundPortURI) throws Exception {
        super(uri, 1, 0);
        this.nodeManagementInboundPortURI = nodeManagementInboundPortURI;
        this.outboundPort = new NodeManagementOutboundPort(this);
        this.outboundPort.publishPort();

        this.getTracer().setTitle("Peer");
    }

    @Override
    public void execute() throws Exception {
        super.execute();
        this.outboundPort.join(new PeerNodeAddress(this.nodeManagementInboundPortURI, "aaa"));
    }

    @Override
    public synchronized void start() throws ComponentStartException {
        super.start();
        try {
            this.doPortConnection(this.outboundPort.getPortURI(), nodeManagementInboundPortURI, NodeManagementConnector.class.getCanonicalName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void finalise() throws Exception {
        this.doPortDisconnection(this.outboundPort.getPortURI());
        this.outboundPort.unpublishPort();
        super.finalise();
    }
}

