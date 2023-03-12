package cps.tme.codecomrades.connectors;

import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

import java.util.Set;

public class ContentManagementConnector extends AbstractConnector implements ContentManagementCI {
    @Override
    public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
        return ((ContentManagementCI) this.offering).find(cd, hops);
    }

    @Override
    public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) {
        return null;
    }
}
