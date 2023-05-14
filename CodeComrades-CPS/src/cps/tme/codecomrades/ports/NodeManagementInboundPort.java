package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.components.Facade;
import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

import java.util.Set;

public class NodeManagementInboundPort extends AbstractInboundPort implements NodeManagementCI {
	public NodeManagementInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, NodeManagementCI.class, owner);
		assert uri != null;
	}

	public NodeManagementInboundPort(ComponentI owner) throws Exception {
		super(NodeManagementCI.class, owner);
	}

	@Override
	public Set<PeerNodeAddressI> joinOld(PeerNodeAddressI a) throws Exception {
		return this.owner.handleRequest(new AbstractComponent.AbstractService<Set<PeerNodeAddressI>>() {
			@Override
			public Set<PeerNodeAddressI> call() throws Exception {
				return ((Facade) this.getServiceOwner()).join(a);
			}
		});
	}

	@Override
	public void join(PeerNodeAddressI a) throws Exception {
		this.owner.runTask(new AbstractComponent.AbstractTask() {
			@Override
			public void run() {
				try {
					((Facade) this.getTaskOwner()).join(a);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@Override
	public void leaveOld(PeerNodeAddressI a) throws Exception {
		((Facade) this.getOwner()).leave(a);
	}

	@Override
	public void leave(PeerNodeAddressI a) throws Exception {
		this.owner.runTask(new AbstractComponent.AbstractTask() {
			@Override
			public void run() {
				try {
					((Facade) this.getTaskOwner()).leave(a);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@Override
	public void acceptProbed(PeerNodeAddressI peer, String requestURI) {
		this.owner.runTask(new AbstractComponent.AbstractTask() {
			@Override
			public void run() {
				((Facade) this.getTaskOwner()).acceptProbed(peer, requestURI);
			}
		});
	}
}
