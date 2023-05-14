package cps.tme.codecomrades.connectors;

import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.interfaces.FacadeContentManagementCI;
import cps.tme.codecomrades.javainterfaces.ApplicationNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

import java.util.Set;

public class FacadeContentManagementConnector extends AbstractConnector implements FacadeContentManagementCI {
	@Override
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception {
		((FacadeContentManagementCI) this.offering).acceptFound(found, requestURI);
	}

	@Override
	public void acceptMatched(Set<ContentDescriptorI> matched, String requestURI) throws Exception {
		((FacadeContentManagementCI) this.offering).acceptMatched(matched, requestURI);
	}

	@Override
	public ContentDescriptorI findOld(ContentTemplateI cd, int hops) throws Exception {
		return null;
	}

	@Override
	public void find(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI) throws Exception {
		((FacadeContentManagementCI) this.offering).find(cd, hops, requester, requestURI);
	}

	@Override
	public Set<ContentDescriptorI> matchOld(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws Exception {
		return null;
	}

	@Override
	public void match(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI, Set<ContentDescriptorI> matched) throws Exception {
		((FacadeContentManagementCI) this.offering).match(cd, hops, requester, requestURI, matched);
	}
}
