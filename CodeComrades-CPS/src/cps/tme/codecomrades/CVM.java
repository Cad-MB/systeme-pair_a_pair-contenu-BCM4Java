package cps.tme.codecomrades;

import cps.tme.codecomrades.components.Facade;
import cps.tme.codecomrades.components.Peer;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM {

    protected static final String FACADE_NM_INBOUND_PORT_URI = "facade-nm-inbound-port";
    protected static final String PEER1_NM_OUTBOUND_PORT_URI = "peer1-nm-outbound-port";
    protected static final String PEER1_NODE_OUTBOUND_PORT_URI = "peer1-node-outbound-port";
    protected static final String PEER1_NODE_INBOUND_PORT_URI = "peer1-node-inbound-port";
    protected static final String PEER2_NM_OUTBOUND_PORT_URI = "peer2-nm-outbound-port";
    protected static final String PEER2_NODE_OUTBOUND_PORT_URI = "peer2-node-outbound-port";
    protected static final String PEER2_NODE_INBOUND_PORT_URI = "peer2-node-inbound-port";

    protected String facadeURI;
    protected String peerURI;
    protected String peer1URI;
    protected String peer2URI;

    public CVM() throws Exception {
        super();
    }

    @Override
    public void deploy() throws Exception {

        this.facadeURI = AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[]{FACADE_NM_INBOUND_PORT_URI});
        assert this.isDeployedComponent(this.facadeURI);
        this.toggleTracing(this.facadeURI);
        this.toggleLogging(this.facadeURI);

        /*this.peerURI = AbstractComponent.createComponent(Peer.class.getCanonicalName(), new Object[]{PEER_OUTBOUND_PORT_URI, FACADE_INBOUND_PORT_URI});
        assert this.isDeployedComponent(this.peerURI);
        this.toggleTracing(this.peerURI);
        this.toggleLogging(this.peerURI);*/

        this.peer1URI = AbstractComponent.createComponent(Peer.class.getCanonicalName(), new Object[]{PEER1_NM_OUTBOUND_PORT_URI, PEER1_NODE_OUTBOUND_PORT_URI, PEER1_NODE_INBOUND_PORT_URI, PEER2_NODE_INBOUND_PORT_URI, FACADE_NM_INBOUND_PORT_URI});
        assert this.isDeployedComponent(this.peer1URI);
        this.peer2URI = AbstractComponent.createComponent(Peer.class.getCanonicalName(), new Object[]{PEER2_NM_OUTBOUND_PORT_URI, PEER2_NODE_OUTBOUND_PORT_URI, PEER2_NODE_INBOUND_PORT_URI, PEER1_NODE_INBOUND_PORT_URI, FACADE_NM_INBOUND_PORT_URI});
        assert this.isDeployedComponent(this.peer2URI);
        super.deploy();
        assert this.deploymentDone();
    }

    @Override
    public void finalise() throws Exception {

        super.finalise();
    }

    @Override
    public void shutdown() throws Exception {
        assert this.allFinalised();
        super.shutdown();
    }

    public static void main(String[] args) {
        try {
            CVM a = new CVM();
            a.startStandardLifeCycle(20000L);
            Thread.sleep(5000L);
            System.exit(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
