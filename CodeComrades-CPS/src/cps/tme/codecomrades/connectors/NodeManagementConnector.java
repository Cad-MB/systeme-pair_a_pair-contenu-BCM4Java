package cps.tme.codecomrades.connectors;

import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

import java.util.Set;

public class NodeManagementConnector extends AbstractConnector implements NodeManagementCI {
	@Override
	public Set<PeerNodeAddressI> joinOld(PeerNodeAddressI a) throws Exception {
		return ((NodeManagementCI) this.offering).joinOld(a);
	}

	@Override
	public void join(PeerNodeAddressI a) throws Exception {
		((NodeManagementCI) this.offering).join(a);
	}

	@Override
	public void leaveOld(PeerNodeAddressI a) throws Exception {
		((NodeManagementCI) this.offering).leaveOld(a);
	}

	@Override
	public void leave(PeerNodeAddressI a) throws Exception {
		((NodeManagementCI) this.offering).leave(a);
	}

	@Override
	public void acceptProbed(PeerNodeAddressI peer, String requestURI) throws Exception {
		((NodeManagementCI) this.offering).acceptProbed(peer, requestURI);
	}
}
