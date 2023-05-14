package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.javainterfaces.ApplicationNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

import java.util.Set;

public class ContentManagementOutboundPort extends AbstractOutboundPort implements ContentManagementCI {
	public ContentManagementOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ContentManagementCI.class, owner);
		assert uri != null;
	}

	public ContentManagementOutboundPort(ComponentI owner) throws Exception {
		super(ContentManagementCI.class, owner);
	}

	@Override
	public ContentDescriptorI findOld(ContentTemplateI cd, int hops) throws Exception {
		return ((ContentManagementCI) this.getConnector()).findOld(cd, hops);
	}

	@Override
	public void find(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI) throws Exception {
		((ContentManagementCI) this.getConnector()).find(cd, hops, requester, requestURI);
	}

	@Override
	public Set<ContentDescriptorI> matchOld(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws Exception {
		return ((ContentManagementCI) this.getConnector()).matchOld(cd, matched, hops);
	}

	@Override
	public void match(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI, Set<ContentDescriptorI> matched) throws Exception {
		((ContentManagementCI) this.getConnector()).match(cd, hops, requester, requestURI, matched);
	}
}
