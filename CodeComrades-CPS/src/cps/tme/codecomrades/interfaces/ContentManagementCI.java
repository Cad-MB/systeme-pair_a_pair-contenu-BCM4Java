package cps.tme.codecomrades.interfaces;

import cps.tme.codecomrades.javainterfaces.ApplicationNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface ContentManagementCI extends RequiredCI, OfferedCI {
	ContentDescriptorI findOld(ContentTemplateI cd, int hops) throws Exception;

	void find(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI) throws Exception;

	Set<ContentDescriptorI> matchOld(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws Exception;

	void match(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI, Set<ContentDescriptorI> matched) throws Exception;
}
