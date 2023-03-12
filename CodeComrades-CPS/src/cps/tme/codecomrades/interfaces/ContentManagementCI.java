package cps.tme.codecomrades.interfaces;

import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface ContentManagementCI extends RequiredCI, OfferedCI {
    ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception;
    Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws Exception;
}
