package cps.tme.codecomrades.connectors;

import cps.tme.codecomrades.interfaces.NodeCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

import java.util.Set;

public class NodeConnector extends AbstractConnector implements NodeCI {
	@Override
	public PeerNodeAddressI connectOld(PeerNodeAddressI a) throws Exception {
		return ((NodeCI) this.offering).connectOld(a);
	}

	@Override
	public void connect(PeerNodeAddressI peer) throws Exception {
		((NodeCI) this.offering).connect(peer);
	}

	@Override
	public void disconnectOld(PeerNodeAddressI a) throws Exception {
		((NodeCI) this.offering).disconnectOld(a);
	}

	@Override
	public void disconnect(PeerNodeAddressI neighbor) throws Exception {
		((NodeCI) this.offering).disconnect(neighbor);
	}

	@Override
	public void acceptNeighbors(Set<PeerNodeAddressI> neighbors) throws Exception {
		((NodeCI) this.offering).acceptNeighbors(neighbors);
	}

	@Override
	public void acceptConnected(PeerNodeAddressI neighbor) throws Exception {
		((NodeCI) this.offering).acceptConnected(neighbor);
	}
}
