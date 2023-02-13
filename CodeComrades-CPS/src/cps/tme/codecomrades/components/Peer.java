package cps.tme.codecomrades.components;

import cps.tme.codecomrades.connectors.NodeConnector;
import cps.tme.codecomrades.connectors.NodeManagementConnector;
import cps.tme.codecomrades.interfaces.NodeCI;
import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javaclasses.PeerNodeAddress;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import cps.tme.codecomrades.ports.NodeInboundPort;
import cps.tme.codecomrades.ports.NodeManagementOutboundPort;
import cps.tme.codecomrades.ports.NodeOutboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

@RequiredInterfaces(required = {NodeManagementCI.class, NodeCI.class})
@OfferedInterfaces(offered = {NodeCI.class})

public class Peer extends AbstractComponent {

    protected NodeManagementOutboundPort nodeManagementOutboundPort;
    protected NodeOutboundPort nodeOutboundPort;
    protected NodeInboundPort nodeInboundPort;
    protected String otherNodeManagementInboundPortURI;
    protected String otherNodeInboundPortURI;


    protected Peer(String nodeManagementOutboundPortURI, String nodeOutboundPortURI, String nodeInboundPortURI, String otherNodeInboundPortURI, String otherNodeManagementInboundPortURI) throws Exception {
        super(1, 0);
        this.otherNodeManagementInboundPortURI = otherNodeManagementInboundPortURI;
        this.nodeManagementOutboundPort = new NodeManagementOutboundPort(nodeManagementOutboundPortURI, this);
        this.nodeManagementOutboundPort.publishPort();

        this.otherNodeInboundPortURI = otherNodeInboundPortURI;
        this.nodeOutboundPort = new NodeOutboundPort(nodeOutboundPortURI, this);
        this.nodeOutboundPort.publishPort();
        this.nodeInboundPort = new NodeInboundPort(nodeInboundPortURI, this);
        this.nodeInboundPort.publishPort();

        this.getTracer().setTitle("Peer");
    }

    protected Peer(String reflectionInboundPortURI, String nodeManagementOutboundPortURI, String nodeOutboundPortURI, String nodeInboundPortURI, String otherNodeInboundPortURI, String otherNodeManagementInboundPortURI) throws Exception {
        super(reflectionInboundPortURI, 1, 0);
        this.otherNodeManagementInboundPortURI = otherNodeManagementInboundPortURI;
        this.nodeManagementOutboundPort = new NodeManagementOutboundPort(nodeManagementOutboundPortURI, this);
        this.nodeManagementOutboundPort.publishPort();
        this.otherNodeInboundPortURI = otherNodeInboundPortURI;
        this.nodeOutboundPort = new NodeOutboundPort(nodeOutboundPortURI, this);
        this.nodeOutboundPort.publishPort();
        this.nodeInboundPort = new NodeInboundPort(nodeInboundPortURI, this);
        this.nodeInboundPort.publishPort();

        this.getTracer().setTitle("Peer");
    }

    @Override
    public void execute() throws Exception {
        super.execute();
        try {
            this.doPortConnection(this.nodeManagementOutboundPort.getPortURI(), otherNodeManagementInboundPortURI, NodeManagementConnector.class.getCanonicalName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            this.doPortConnection(this.nodeOutboundPort.getPortURI(), this.otherNodeInboundPortURI, NodeConnector.class.getCanonicalName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.nodeManagementOutboundPort.join(new PeerNodeAddress(this.otherNodeManagementInboundPortURI, "aaa"));
        this.nodeOutboundPort.connect(new PeerNodeAddress(this.otherNodeInboundPortURI, this.otherNodeInboundPortURI));
    }

    @Override
    public synchronized void start() throws ComponentStartException {
        super.start();

    }

    @Override
    public synchronized void finalise() throws Exception {
        this.doPortDisconnection(this.nodeManagementOutboundPort.getPortURI());
        this.nodeManagementOutboundPort.unpublishPort();
        this.doPortDisconnection(this.nodeOutboundPort.getPortURI());
        this.nodeOutboundPort.unpublishPort();
        this.nodeInboundPort.unpublishPort();
        super.finalise();
    }

    public PeerNodeAddressI connect(PeerNodeAddressI a) {
        System.out.println("Connect : URI = " + a.getNodeURI());
        return null;
    }
}

