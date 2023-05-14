package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.interfaces.FacadeContentManagementCI;
import cps.tme.codecomrades.javainterfaces.ApplicationNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

import java.util.Set;

public class FacadeContentManagementOutboundPort extends AbstractOutboundPort implements FacadeContentManagementCI {
	public FacadeContentManagementOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, FacadeContentManagementCI.class, owner);
		assert uri != null;
	}

	public FacadeContentManagementOutboundPort(ComponentI owner) throws Exception {
		super(FacadeContentManagementCI.class, owner);
	}

	@Override
	public ContentDescriptorI findOld(ContentTemplateI cd, int hops) throws Exception {
		return null;
	}

	@Override
	public void find(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI) throws Exception {
		((FacadeContentManagementCI) this.getConnector()).find(cd, hops, requester, requestURI);
	}

	@Override
	public Set<ContentDescriptorI> matchOld(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws Exception {
		return null;
	}

	@Override
	public void match(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI, Set<ContentDescriptorI> matched) throws Exception {
		((FacadeContentManagementCI) this.getConnector()).match(cd, hops, requester, requestURI, matched);
	}

	@Override
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception {
		((FacadeContentManagementCI) this.getConnector()).acceptFound(found, requestURI);
	}

	@Override
	public void acceptMatched(Set<ContentDescriptorI> matched, String requestURI) throws Exception {
		((FacadeContentManagementCI) this.getConnector()).acceptMatched(matched, requestURI);
	}
}
