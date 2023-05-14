package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.interfaces.NodeCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

import java.util.Set;

public class NodeOutboundPort extends AbstractOutboundPort implements NodeCI {
	public NodeOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, NodeCI.class, owner);
		assert uri != null;
	}

	public NodeOutboundPort(ComponentI owner) throws Exception {
		super(NodeCI.class, owner);
	}

	@Override
	public PeerNodeAddressI connectOld(PeerNodeAddressI a) throws Exception {
		return ((NodeCI) this.getConnector()).connectOld(a);
	}

	@Override
	public void connect(PeerNodeAddressI peer) throws Exception {
		((NodeCI) this.getConnector()).connect(peer);
	}

	@Override
	public void disconnectOld(PeerNodeAddressI a) throws Exception {
		((NodeCI) this.getConnector()).disconnectOld(a);
	}

	@Override
	public void disconnect(PeerNodeAddressI neighbor) throws Exception {
		((NodeCI) this.getConnector()).disconnect(neighbor);
	}

	@Override
	public void acceptNeighbors(Set<PeerNodeAddressI> neighbors) throws Exception {
		((NodeCI) this.getConnector()).acceptNeighbors(neighbors);
	}

	@Override
	public void acceptConnected(PeerNodeAddressI neighbor) throws Exception {
		((NodeCI) this.getConnector()).acceptConnected(neighbor);
	}
}
