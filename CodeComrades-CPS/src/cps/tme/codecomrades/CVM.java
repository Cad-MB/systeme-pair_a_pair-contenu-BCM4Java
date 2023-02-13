package cps.tme.codecomrades;

import cps.tme.codecomrades.components.Facade;
import cps.tme.codecomrades.components.Peer;
import cps.tme.codecomrades.connectors.NodeManagementConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM {

    protected static final String FACADE_INBOUND_PORT_URI = "facade-inbound-port";
    protected static final String PEER_OUTBOUND_PORT_URI = "peer-outbound-port";

    protected String facadeURI;
    protected String peerURI;

    public CVM() throws Exception {
        super();
    }

    @Override
    public void deploy() throws Exception {

        this.facadeURI = AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[]{FACADE_INBOUND_PORT_URI});
        assert this.isDeployedComponent(this.facadeURI);
        this.toggleTracing(this.facadeURI);
        this.toggleLogging(this.facadeURI);

        this.peerURI = AbstractComponent.createComponent(Peer.class.getCanonicalName(), new Object[]{FACADE_INBOUND_PORT_URI});
        assert this.isDeployedComponent(this.peerURI);
        this.toggleTracing(this.peerURI);
        this.toggleLogging(this.peerURI);

        //this.doPortConnection(this.peerURI, PEER_OUTBOUND_PORT_URI, FACADE_INBOUND_PORT_URI, NodeManagementConnector.class.getCanonicalName());
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
