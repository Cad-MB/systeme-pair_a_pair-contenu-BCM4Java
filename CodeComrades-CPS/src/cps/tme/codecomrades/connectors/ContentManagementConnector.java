package cps.tme.codecomrades.connectors;

import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.javainterfaces.ApplicationNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

import java.util.Set;

public class ContentManagementConnector extends AbstractConnector implements ContentManagementCI {
	@Override
	public ContentDescriptorI findOld(ContentTemplateI cd, int hops) throws Exception {
		return ((ContentManagementCI) this.offering).findOld(cd, hops);
	}

	@Override
	public void find(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI) throws Exception {
		((ContentManagementCI) this.offering).find(cd, hops, requester, requestURI);
	}

	@Override
	public Set<ContentDescriptorI> matchOld(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws Exception {
		return ((ContentManagementCI) this.offering).matchOld(cd, matched, hops);
	}

	@Override
	public void match(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI, Set<ContentDescriptorI> matched) throws Exception {
		((ContentManagementCI) this.offering).match(cd, hops, requester, requestURI, matched);
	}
}
