package cps.tme.codecomrades.connectors;

import cps.tme.codecomrades.interfaces.NodeCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class NodeConnector extends AbstractConnector implements NodeCI {
    @Override
    public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception {
        return ((NodeCI) this.offering).connect(a);
    }

    @Override
    public void disconnect(PeerNodeAddressI a) {

    }
}
