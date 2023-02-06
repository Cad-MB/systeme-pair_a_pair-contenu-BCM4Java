package cps.tme.codecomrades.connectors;

import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

import java.util.Set;

public class NodeManagementConnector extends AbstractConnector implements NodeManagementCI {
    @Override
    public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
        return ((NodeManagementCI)this.offering).join(a);
    }

    @Override
    public void leave(PeerNodeAddressI a) {

    }
}
