package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

import java.util.Set;

public class NodeManagementOutboundPort extends AbstractOutboundPort implements NodeManagementCI {
	public NodeManagementOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, NodeManagementCI.class, owner);
		assert uri != null;
	}

	public NodeManagementOutboundPort(ComponentI owner) throws Exception {
		super(NodeManagementCI.class, owner);
	}

	@Override
	public Set<PeerNodeAddressI> joinOld(PeerNodeAddressI a) throws Exception {
		return ((NodeManagementCI) this.getConnector()).joinOld(a);
	}

	@Override
	public void join(PeerNodeAddressI a) throws Exception {
		((NodeManagementCI) this.getConnector()).join(a);
	}

	@Override
	public void leaveOld(PeerNodeAddressI a) throws Exception {
		((NodeManagementCI) this.getConnector()).leaveOld(a);
	}

	@Override
	public void leave(PeerNodeAddressI a) throws Exception {
		((NodeManagementCI) this.getConnector()).leave(a);
	}

	@Override
	public void acceptProbed(PeerNodeAddressI peer, String requestURI) throws Exception {
		((NodeManagementCI) this.getConnector()).acceptProbed(peer, requestURI);
	}
}
