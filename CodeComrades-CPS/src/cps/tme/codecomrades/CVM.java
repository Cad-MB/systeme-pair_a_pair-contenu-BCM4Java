package cps.tme.codecomrades;

import cps.tme.codecomrades.components.Facade;
import cps.tme.codecomrades.components.Peer;
import cps.tme.codecomrades.connectors.NodeManagementConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM {


    protected static final String FACADE_NM_INBOUND_PORT_URI = "facade-nm-inbound-port";
    protected static final String FACADE_CM_OUTBOUND_PORT_URI = "facade-cm-outbound-port";
    protected static final String PEER1_NM_OUTBOUND_PORT_URI = "peer1-nm-outbound-port";
    protected static final String PEER1_NODE_OUTBOUND_PORT_URI = "peer1-node-outbound-port";
    protected static final String PEER1_NODE_INBOUND_PORT_URI = "peer1-node-inbound-port";
    protected static final String PEER1_CM_OUTBOUND_PORT_URI = "peer1-cm-outbound-port";
    protected static final String PEER1_CM_INBOUND_PORT_URI = "peer1-cm-inbound-port";
    protected static final String PEER2_NM_OUTBOUND_PORT_URI = "peer2-nm-outbound-port";
    protected static final String PEER2_NODE_OUTBOUND_PORT_URI = "peer2-node-outbound-port";
    protected static final String PEER2_NODE_INBOUND_PORT_URI = "peer2-node-inbound-port";
    protected static final String PEER2_CM_OUTBOUND_PORT_URI = "peer2-cm-outbound-port";
    protected static final String PEER2_CM_INBOUND_PORT_URI = "peer2-cm-inbound-port";
    protected static final int NUMBER_OF_PEERS = 10;

    protected String facadeURI;
    protected String[] peerURIs;

    public CVM() throws Exception {
        super();
    }

    @Override
    public void deploy() throws Exception {

        //this.facadeURI = AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[]{"Facade1", FACADE_NM_INBOUND_PORT_URI, FACADE_CM_OUTBOUND_PORT_URI});
        this.facadeURI = AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[]{"facade"});
        assert this.isDeployedComponent(this.facadeURI);
        this.toggleTracing(this.facadeURI);
        this.toggleLogging(this.facadeURI);

        peerURIs = new String[NUMBER_OF_PEERS];

        for (int i = 0; i < NUMBER_OF_PEERS; i++) {
            this.peerURIs[i] = AbstractComponent.createComponent(Peer.class.getCanonicalName(), new Object[]{i});
            assert this.isDeployedComponent(this.peerURIs[i]);
            this.doPortConnection(this.peerURIs[i], this.peerURIs[i] + "-nm-outbound-port", this.facadeURI + "-nm-inbound-port", NodeManagementConnector.class.getCanonicalName());
            this.toggleTracing(this.peerURIs[i]);
            Thread.sleep(900L);
        }

        /*this.peer1URI = AbstractComponent.createComponent(Peer.class.getCanonicalName(), new Object[]{"Peer1", PEER1_NM_OUTBOUND_PORT_URI, PEER1_NODE_OUTBOUND_PORT_URI, PEER1_NODE_INBOUND_PORT_URI, PEER1_CM_OUTBOUND_PORT_URI, PEER1_CM_INBOUND_PORT_URI});
        this.peer1URI = AbstractComponent.createComponent(Peer.class.getCanonicalName(), new Object[]{"peer1"});
        assert this.isDeployedComponent(this.peer1URI);
        this.doPortConnection(this.peer1URI, this.peer1URI + "-nm-outbound-port", this.facadeURI + "-nm-inbound-port", NodeManagementConnector.class.getCanonicalName());

        this.toggleTracing(this.peer1URI);
        Thread.sleep(1000L);

        this.peer2URI = AbstractComponent.createComponent(Peer.class.getCanonicalName(), new Object[]{"peer2", PEER2_NM_OUTBOUND_PORT_URI, PEER2_NODE_OUTBOUND_PORT_URI, PEER2_NODE_INBOUND_PORT_URI, PEER2_CM_OUTBOUND_PORT_URI, PEER2_CM_INBOUND_PORT_URI});
        assert this.isDeployedComponent(this.peer2URI);
        this.doPortConnection(this.peer2URI, PEER2_NM_OUTBOUND_PORT_URI, this.facadeURI + "-nm-inbound-port", NodeManagementConnector.class.getCanonicalName());

        this.toggleTracing(this.peer2URI);*/

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
            a.startStandardLifeCycle(90000L);
            Thread.sleep(5000L);
            System.exit(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
