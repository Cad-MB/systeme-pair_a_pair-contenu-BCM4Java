package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.interfaces.ContentManagementCI;
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
    public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
        return ((ContentManagementCI) this.getConnector()).find(cd, hops);
    }

    @Override
    public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) {
        return null;
    }
}
