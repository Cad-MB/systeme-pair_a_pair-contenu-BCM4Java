package cps.tme.codecomrades.plugins;

import cps.tme.codecomrades.components.Peer;
import cps.tme.codecomrades.connectors.NodeConnector;
import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.interfaces.NodeCI;
import cps.tme.codecomrades.javaclasses.ContentNodeAddress;
import cps.tme.codecomrades.javaclasses.PeerNodeAddress;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import cps.tme.codecomrades.ports.ContentManagementInboundPort;
import cps.tme.codecomrades.ports.NodeInboundPort;
import cps.tme.codecomrades.ports.NodeOutboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PeerNodePlugin extends AbstractPlugin {

	protected NodeOutboundPort nop;
	protected NodeInboundPort nip;
	protected ContentManagementInboundPort cmip;
	protected Set<PeerNodeAddressI> neighboringNodes;
	protected PeerNodeAddressI pnAdr;

	protected Map<PeerNodeAddressI, NodeOutboundPort> neighborsMap;

	public PeerNodePlugin() {
		super();
	}

	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);

		this.pnAdr = new PeerNodeAddress(((Peer) getOwner()).getReflectionURI() + "nip");

		this.addOfferedInterface(NodeCI.class);
		this.addOfferedInterface(ContentManagementCI.class);
		this.addRequiredInterface(NodeCI.class);

		this.nop = new NodeOutboundPort(this.getOwner());
		this.nop.publishPort();

		this.neighborsMap = new HashMap<>();
	}

	@Override
	public void initialise() throws Exception {
		super.initialise();

		this.cmip = new ContentManagementInboundPort(this.getOwner());
		this.cmip.publishPort();

		this.nip = new NodeInboundPort(this.pnAdr.getNodeURI(), this.getOwner());
		this.nip.publishPort();

		this.neighboringNodes = new HashSet<>();
	}

	@Override
	public void finalise() throws Exception {
		this.getOwner().doPortDisconnection(this.nop.getPortURI());

		super.finalise();
	}

	@Override
	public void uninstall() throws Exception {
		this.nop.unpublishPort();
		this.nop.destroyPort();
		this.removeOfferedInterface(NodeCI.class);

		this.nip.unpublishPort();
		this.nip.destroyPort();
		this.removeRequiredInterface(NodeCI.class);

		this.cmip.unpublishPort();
		this.cmip.destroyPort();
		this.removeRequiredInterface(ContentManagementCI.class);

		super.uninstall();
	}

	public PeerNodeAddressI connectOld(PeerNodeAddressI a) throws Exception {
		if (!getOwner().isPortConnected(this.nop.getPortURI())) {
			this.getOwner().traceMessage("Connecting " + this.nop.getPortURI() + " to " + a.getNodeURI() + "\n");
			this.getOwner().doPortConnection(this.nop.getPortURI(), a.getNodeURI(), NodeConnector.class.getCanonicalName());
			PeerNodeAddress myself = new ContentNodeAddress(this.nip.getPortURI(), this.cmip.getPortURI());
			this.neighboringNodes.add(a);
			return myself;
		}
		return null;
	}

	void connect(PeerNodeAddressI peer) throws Exception {
		NodeOutboundPort nop;
		if (!neighborsMap.containsKey(peer)) {
			this.getOwner().traceMessage("Connecting " + this.pnAdr.getNodeIdentifier() + " to " + peer.getNodeURI() + "\n");
			nop = new NodeOutboundPort(this.pnAdr.getNodeURI() + "-nop-" + neighborsMap.size(), this.getOwner());
			nop.publishPort();
			nop.doConnection(peer.getNodeURI(), NodeConnector.class.getCanonicalName());
			this.getOwner().runTask(new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						nop.acceptConnected(pnAdr);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
	}

	public void disconnectOld(PeerNodeAddressI a) throws Exception {
		if (getOwner().isPortConnected(this.nop.getPortURI())) {
			this.getOwner().doPortDisconnection(this.nop.getPortURI());
			this.neighboringNodes.remove(a);
		}
	}

	void disconnect(PeerNodeAddressI neighbor) throws Exception {
		//
	}
}
