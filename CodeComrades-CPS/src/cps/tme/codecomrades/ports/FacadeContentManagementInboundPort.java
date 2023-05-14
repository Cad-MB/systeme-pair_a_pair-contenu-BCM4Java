package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.components.Facade;
import cps.tme.codecomrades.components.Peer;
import cps.tme.codecomrades.interfaces.FacadeContentManagementCI;
import cps.tme.codecomrades.javainterfaces.ApplicationNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

import java.util.Set;

public class FacadeContentManagementInboundPort extends AbstractInboundPort implements FacadeContentManagementCI {
	public FacadeContentManagementInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, FacadeContentManagementCI.class, owner);
		assert uri != null;
	}

	public FacadeContentManagementInboundPort(ComponentI owner) throws Exception {
		super(FacadeContentManagementCI.class, owner);
	}

	@Override
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception {
		this.owner.runTask(new AbstractComponent.AbstractTask() {
			@Override
			public void run() {
				((Facade) this.getTaskOwner()).acceptFound(found, requestURI);
			}
		});
	}

	@Override
	public void acceptMatched(Set<ContentDescriptorI> matched, String requestURI) throws Exception {
		this.owner.runTask(new AbstractComponent.AbstractTask() {
			@Override
			public void run() {
				((Facade) this.getTaskOwner()).acceptMatched(matched, requestURI);
			}
		});
	}

	@Override
	public ContentDescriptorI findOld(ContentTemplateI cd, int hops) throws Exception {
		return null;
	}

	@Override
	public void find(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI) throws Exception {
		this.owner.runTask(new AbstractComponent.AbstractTask() {
			@Override
			public void run() {
				((Peer) this.getTaskOwner()).find(cd, hops, requester, requestURI);
			}
		});
	}

	@Override
	public Set<ContentDescriptorI> matchOld(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws Exception {
		return null;
	}

	@Override
	public void match(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI, Set<ContentDescriptorI> matched) throws Exception {
		this.owner.runTask(new AbstractComponent.AbstractTask() {
			@Override
			public void run() {
				((Peer) this.getTaskOwner()).find(cd, hops, requester, requestURI, matched);
			}
		});
	}
}
