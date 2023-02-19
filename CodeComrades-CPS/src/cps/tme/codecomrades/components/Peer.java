package cps.tme.codecomrades.components;

import cps.tme.codecomrades.connectors.NodeConnector;
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

import java.util.HashSet;
import java.util.Set;

@RequiredInterfaces(required = {NodeManagementCI.class, NodeCI.class})
@OfferedInterfaces(offered = {NodeCI.class})

public class Peer extends AbstractComponent {

    protected NodeManagementOutboundPort nodeManagementOutboundPort;
    protected NodeOutboundPort nodeOutboundPort;
    protected NodeInboundPort nodeInboundPort;
    protected String reflectionURI;
    protected Set<PeerNodeAddressI> neighboringNodes;


    protected Peer(String nodeManagementOutboundPortURI, String nodeOutboundPortURI, String nodeInboundPortURI) throws Exception {
        super(1, 0);
        this.nodeManagementOutboundPort = new NodeManagementOutboundPort(nodeManagementOutboundPortURI, this);
        this.nodeManagementOutboundPort.publishPort();
        this.nodeOutboundPort = new NodeOutboundPort(nodeOutboundPortURI, this);
        this.nodeOutboundPort.publishPort();
        this.nodeInboundPort = new NodeInboundPort(nodeInboundPortURI, this);
        this.nodeInboundPort.publishPort();

        this.getTracer().setTitle("Peer");
        this.neighboringNodes = new HashSet<>();
    }

    protected Peer(String reflectionInboundPortURI, String nodeManagementOutboundPortURI, String nodeOutboundPortURI, String nodeInboundPortURI) throws Exception {
        super(reflectionInboundPortURI, 1, 0);
        this.reflectionURI = reflectionInboundPortURI;
        this.nodeManagementOutboundPort = new NodeManagementOutboundPort(nodeManagementOutboundPortURI, this);
        this.nodeManagementOutboundPort.publishPort();
        this.nodeOutboundPort = new NodeOutboundPort(nodeOutboundPortURI, this);
        this.nodeOutboundPort.publishPort();
        this.nodeInboundPort = new NodeInboundPort(nodeInboundPortURI, this);
        this.nodeInboundPort.publishPort();

        this.getTracer().setTitle("Peer");
        this.neighboringNodes = new HashSet<>();
    }

    @Override
    public void execute() throws Exception {
        super.execute();
        this.neighboringNodes = this.nodeManagementOutboundPort.join(new PeerNodeAddress(this.nodeInboundPort.getPortURI()));
        System.out.println("BEEP " + this.reflectionURI);
        for (PeerNodeAddressI neighbor:
             this.neighboringNodes) {
            connect(neighbor);
        }
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

    public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception {
        System.out.println("Connecting " + this.nodeOutboundPort.getPortURI() + " to " + a.getNodeURI());
        this.doPortConnection(this.nodeOutboundPort.getPortURI(), a.getNodeURI(), NodeConnector.class.getCanonicalName());
        PeerNodeAddress myself = new PeerNodeAddress(this.nodeInboundPort.getPortURI());
        this.nodeOutboundPort.connect(myself);
        return myself;
    }
}

