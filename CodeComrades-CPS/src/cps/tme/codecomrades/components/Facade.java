package cps.tme.codecomrades.components;

import cps.tme.codecomrades.connectors.ContentManagementConnector;
import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javaclasses.ContentDescriptor;
import cps.tme.codecomrades.javaclasses.ContentTemplate;
import cps.tme.codecomrades.javainterfaces.*;
import cps.tme.codecomrades.ports.ContentManagementOutboundPort;
import cps.tme.codecomrades.ports.NodeManagementInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.cps.p2Pcm.dataread.ContentDataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@OfferedInterfaces(offered = {NodeManagementCI.class, ContentManagementCI.class})
@RequiredInterfaces(required = {ContentManagementCI.class})

public class Facade extends AbstractComponent {

    protected final NodeManagementInboundPort nodeManagementInboundPort;
    protected ContentManagementOutboundPort contentManagementOutboundPort;
    protected Set<PeerNodeAddressI> rootNodes;
    private ArrayList<HashMap<String, Object>> readTemplates;
    protected ArrayList<ContentTemplateI> processedTemplates;


    protected Facade(String nodeManagementInboundPortURI, String contentManagementOutboundPortURI) throws Exception {
        super(10, 0);
        assert nodeManagementInboundPortURI != null;

        this.nodeManagementInboundPort = new NodeManagementInboundPort(nodeManagementInboundPortURI, this);
        this.nodeManagementInboundPort.publishPort();

        this.contentManagementOutboundPort = new ContentManagementOutboundPort(contentManagementOutboundPortURI, this);
        this.contentManagementOutboundPort.publishPort();

        this.getTracer().setTitle("Facade");
        this.getTracer().setRelativePosition(1, 0);

        AbstractComponent.checkImplementationInvariant(this);
        AbstractComponent.checkInvariant(this);

        this.rootNodes = new HashSet<>();

        this.processedTemplates = new ArrayList<>();
    }

    protected Facade(String reflectionInboundPortURI, String nodeManagementInboundPortURI, String contentManagementOutboundPortURI) throws Exception {
        super(reflectionInboundPortURI, 10, 0);

        this.nodeManagementInboundPort = new NodeManagementInboundPort(nodeManagementInboundPortURI, this);
        this.nodeManagementInboundPort.publishPort();

        this.contentManagementOutboundPort = new ContentManagementOutboundPort(contentManagementOutboundPortURI, this);
        this.contentManagementOutboundPort.publishPort();

        this.getTracer().setTitle("Facade : " + reflectionInboundPortURI);
        this.getTracer().setRelativePosition(1, 0);

        this.rootNodes = new HashSet<>();

        this.processedTemplates = new ArrayList<>();
    }

    protected Facade(String reflectionInboundPortURI) throws Exception {
        super(reflectionInboundPortURI, 10, 0);

        this.nodeManagementInboundPort = new NodeManagementInboundPort(reflectionInboundPortURI+"-nm-inbound-port", this);
        this.nodeManagementInboundPort.publishPort();

        this.contentManagementOutboundPort = new ContentManagementOutboundPort(reflectionInboundPortURI+"-cm-outbound-port", this);
        this.contentManagementOutboundPort.publishPort();

        this.getTracer().setTitle("Facade : " + reflectionInboundPortURI);
        this.getTracer().setRelativePosition(0, 0);

        this.rootNodes = new HashSet<>();

        this.processedTemplates = new ArrayList<>();
    }

    @Override
    public synchronized void start() throws ComponentStartException {
        super.start();
        try {
            readTemplates = ContentDataManager.readTemplates(0);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        for (HashMap<String, Object> h : readTemplates) {
            Set<String> interpreters, composers;
            if (h.get(ContentDataManager.INTERPRETERS_KEY) != null)
                interpreters = new HashSet<>((ArrayList<String>) h.get(ContentDataManager.INTERPRETERS_KEY));
            else
                interpreters = null;
            if (h.get(ContentDataManager.COMPOSERS_KEY) != null)
                composers = new HashSet<>((ArrayList<String>) h.get(ContentDataManager.COMPOSERS_KEY));
            else
                composers = null;
            processedTemplates.add(new ContentTemplate((String) h.get(ContentDataManager.TITLE_KEY),
                    (String) h.get(ContentDataManager.ALBUM_TITLE_KEY),
                    interpreters,
                    composers));
            this.traceMessage(ContentDataManager.toString(h)+"\n");
        }
    }

    @Override
    public void execute() throws Exception {
        super.execute();
        Thread.sleep(35000L);
        ContentDescriptor result = (ContentDescriptor) this.handleRequest(new AbstractService<ContentDescriptorI>() {
            @Override
            public ContentDescriptorI call() throws Exception {
                return find(processedTemplates.get(1), 5);
            }
        });
        if(result!=null)
            this.traceMessage("MATCHING DESCRIPTOR FOUND!");
    }

    public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
        this.traceMessage("Added peer node " + a.getNodeURI() + " as root.\n");
        //System.out.println("Added peer node " + a.getNodeURI() + " as root.");
        Set<PeerNodeAddressI> neighborsOfA = new HashSet<>(this.rootNodes);
        this.rootNodes.add(a);
        this.traceMessage("Current root nodes of facade: " + this.rootNodes.toString() + "\n");
        this.traceMessage("Neighbors returned: " + neighborsOfA + "\n");
        //System.out.println("Current root nodes of facade: " + this.rootNodes.toString());
        //System.out.println("Neighbors returned: " + neighborsOfA);
        return neighborsOfA;
    }

    public void leave(PeerNodeAddressI a) throws Exception {
        this.rootNodes.remove(a);
    }

    public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
        ContentDescriptorI r;
        this.traceMessage("Starting Request\n");
        for (PeerNodeAddressI root : this.rootNodes) {
            this.traceMessage("Sending search to " + ((ContentNodeAddressI) root).getContentManagementURI() + "\n");
            if (isPortConnected(contentManagementOutboundPort.getPortURI())) {
                this.doPortDisconnection(contentManagementOutboundPort.getPortURI());
            }
            this.doPortConnection(this.contentManagementOutboundPort.getPortURI(), ((ContentNodeAddressI) root).getContentManagementURI(), ContentManagementConnector.class.getCanonicalName());
            r = this.contentManagementOutboundPort.find(cd, hops);
            if (r != null)
                return r;
            Thread.sleep(1000L);
        }
        return null;
    }

    @Override
    public synchronized void finalise() throws Exception {
        this.nodeManagementInboundPort.unpublishPort();
        if (isPortConnected(this.contentManagementOutboundPort.getPortURI()))
            this.doPortDisconnection(this.contentManagementOutboundPort.getPortURI());
        this.contentManagementOutboundPort.unpublishPort();
        super.finalise();
    }
}
