package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.components.Peer;
import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.javainterfaces.ApplicationNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ContentManagementInboundPort extends AbstractInboundPort implements ContentManagementCI {
	public ContentManagementInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ContentManagementCI.class, owner);
		assert uri != null;
	}

	public ContentManagementInboundPort(ComponentI owner) throws Exception {
		super(ContentManagementCI.class, owner);
	}

	@Override
	public ContentDescriptorI findOld(ContentTemplateI cd, int hops) throws Exception {
		return this.owner.handleRequest(new AbstractComponent.AbstractService<ContentDescriptorI>() {
			@Override
			public ContentDescriptorI call() throws Exception {
				return ((Peer) this.getServiceOwner()).find(cd, hops);
			}
		});
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
	public Set<ContentDescriptorI> matchOld(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws ExecutionException, InterruptedException {
		return this.owner.handleRequest(new AbstractComponent.AbstractService<Set<ContentDescriptorI>>() {
			@Override
			public Set<ContentDescriptorI> call() throws Exception {
				return ((Peer) this.getServiceOwner()).match(cd, matched, hops);
			}
		});
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
