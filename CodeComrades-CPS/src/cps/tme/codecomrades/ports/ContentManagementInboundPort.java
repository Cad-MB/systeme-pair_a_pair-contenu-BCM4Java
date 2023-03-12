package cps.tme.codecomrades.ports;

import cps.tme.codecomrades.components.Peer;
import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

import java.util.Set;

public class ContentManagementInboundPort extends AbstractInboundPort implements ContentManagementCI {
    public ContentManagementInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, ContentManagementCI.class, owner);
        assert uri != null;
    }

    public ContentManagementInboundPort(ComponentI owner) throws Exception {
        super(ContentManagementCI.class, owner);
    }

    @Override
    public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
        return this.owner.handleRequest(new AbstractComponent.AbstractService<ContentDescriptorI>() {
            @Override
            public ContentDescriptorI call() throws Exception {
                return ((Peer)this.getServiceOwner()).find(cd, hops);
            }
        });
    }

    @Override
    public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) {
        return null;
    }
}
