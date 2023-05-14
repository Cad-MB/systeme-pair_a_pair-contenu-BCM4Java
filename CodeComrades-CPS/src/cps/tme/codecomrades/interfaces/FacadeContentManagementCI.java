package cps.tme.codecomrades.interfaces;

import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;

import java.util.Set;

public interface FacadeContentManagementCI extends ContentManagementCI {
	void acceptFound(ContentDescriptorI found, String requestURI) throws Exception;

	void acceptMatched(Set<ContentDescriptorI> matched, String requestURI) throws Exception;
}
