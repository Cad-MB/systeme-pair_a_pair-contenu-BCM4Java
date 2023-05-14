package cps.tme.codecomrades.components;

import cps.tme.codecomrades.connectors.ContentManagementConnector;
import cps.tme.codecomrades.connectors.NodeConnector;
import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.interfaces.NodeCI;
import cps.tme.codecomrades.interfaces.NodeManagementCI;
import cps.tme.codecomrades.javaclasses.ContentDescriptor;
import cps.tme.codecomrades.javaclasses.ContentNodeAddress;
import cps.tme.codecomrades.javaclasses.PeerNodeAddress;
import cps.tme.codecomrades.javainterfaces.ContentDescriptorI;
import cps.tme.codecomrades.javainterfaces.ContentNodeAddressI;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import cps.tme.codecomrades.ports.*;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.cps.p2Pcm.dataread.ContentDataManager;

import java.io.IOException;
import java.util.*;

@RequiredInterfaces(required = {NodeManagementCI.class, NodeCI.class, ContentManagementCI.class})
@OfferedInterfaces(offered = {NodeCI.class, ContentManagementCI.class})

public class Peer extends AbstractComponent {

	protected ContentManagementOutboundPort contentManagementOutboundPort;
	protected ContentManagementInboundPort contentManagementInboundPort;
	protected NodeManagementOutboundPort nodeManagementOutboundPort;
	protected NodeOutboundPort nodeOutboundPort;
	protected NodeInboundPort nodeInboundPort;
	protected String reflectionURI;
	protected Set<PeerNodeAddressI> neighboringNodes;
	private final ArrayList<ContentDescriptorI> processedDescriptors;

	protected int id;


	protected Peer(String nodeManagementOutboundPortURI, String nodeOutboundPortURI, String nodeInboundPortURI, String contentManagementOutboundPortURI, String contentManagementInboundPortURI) throws Exception {
		super(10, 0);
		this.nodeManagementOutboundPort = new NodeManagementOutboundPort(nodeManagementOutboundPortURI, this);
		this.nodeManagementOutboundPort.publishPort();
		this.nodeOutboundPort = new NodeOutboundPort(nodeOutboundPortURI, this);
		this.nodeOutboundPort.publishPort();
		this.nodeInboundPort = new NodeInboundPort(nodeInboundPortURI, this);
		this.nodeInboundPort.publishPort();
		this.contentManagementOutboundPort = new ContentManagementOutboundPort(contentManagementOutboundPortURI, this);
		this.contentManagementOutboundPort.publishPort();
		this.contentManagementInboundPort = new ContentManagementInboundPort(contentManagementInboundPortURI, this);
		this.contentManagementInboundPort.publishPort();

		this.getTracer().setTitle("Peer");
		this.getTracer().setRelativePosition(1, 1);

		this.neighboringNodes = new HashSet<>();

		this.processedDescriptors = new ArrayList<>();
	}

	protected Peer(String reflectionInboundPortURI, String nodeManagementOutboundPortURI, String nodeOutboundPortURI, String nodeInboundPortURI, String contentManagementOutboundPortURI, String contentManagementInboundPortURI) throws Exception {
		super(reflectionInboundPortURI, 10, 0);

		this.reflectionURI = reflectionInboundPortURI;
		this.nodeManagementOutboundPort = new NodeManagementOutboundPort(nodeManagementOutboundPortURI, this);
		this.nodeManagementOutboundPort.publishPort();
		this.nodeOutboundPort = new NodeOutboundPort(nodeOutboundPortURI, this);
		this.nodeOutboundPort.publishPort();
		this.nodeInboundPort = new NodeInboundPort(nodeInboundPortURI, this);
		this.nodeInboundPort.publishPort();
		this.contentManagementOutboundPort = new ContentManagementOutboundPort(contentManagementOutboundPortURI, this);
		this.contentManagementOutboundPort.publishPort();
		this.contentManagementInboundPort = new ContentManagementInboundPort(contentManagementInboundPortURI, this);
		this.contentManagementInboundPort.publishPort();

		this.getTracer().setTitle("Peer : " + reflectionInboundPortURI);
		this.getTracer().setRelativePosition(1, 1);

		this.neighboringNodes = new HashSet<>();

		this.processedDescriptors = new ArrayList<>();
	}

	protected Peer(String reflectionInboundPortURI) throws Exception {
		super(reflectionInboundPortURI, 10, 0);
		this.reflectionURI = reflectionInboundPortURI;
		this.nodeManagementOutboundPort = new NodeManagementOutboundPort(reflectionInboundPortURI + "-nm-outbound-port", this);
		this.nodeManagementOutboundPort.publishPort();
		this.nodeOutboundPort = new NodeOutboundPort(reflectionInboundPortURI + "-node-outbound-port", this);
		this.nodeOutboundPort.publishPort();
		this.nodeInboundPort = new NodeInboundPort(reflectionInboundPortURI + "-node-inbound-port", this);
		this.nodeInboundPort.publishPort();
		this.contentManagementOutboundPort = new ContentManagementOutboundPort(reflectionInboundPortURI + "-cm-outbound-port", this);
		this.contentManagementOutboundPort.publishPort();
		this.contentManagementInboundPort = new ContentManagementInboundPort(reflectionInboundPortURI + "-cm-inbound-port", this);
		this.contentManagementInboundPort.publishPort();

		this.getTracer().setTitle("Peer : " + reflectionInboundPortURI);
		this.getTracer().setRelativePosition(1, 1);

		this.neighboringNodes = new HashSet<>();

		this.processedDescriptors = new ArrayList<>();
	}

	protected Peer(int peerID) throws Exception {
		super("peer" + peerID, 100, 0);
		this.id = peerID;
		String reflectionInboundPortURI = "peer" + this.id;
		this.reflectionURI = reflectionInboundPortURI;
		this.nodeManagementOutboundPort = new NodeManagementOutboundPort(reflectionInboundPortURI + "-nm-outbound-port", this);
		this.nodeManagementOutboundPort.publishPort();
		this.nodeOutboundPort = new NodeOutboundPort(reflectionInboundPortURI + "-node-outbound-port", this);
		this.nodeOutboundPort.publishPort();
		this.nodeInboundPort = new NodeInboundPort(reflectionInboundPortURI + "-node-inbound-port", this);
		this.nodeInboundPort.publishPort();
		this.contentManagementOutboundPort = new ContentManagementOutboundPort(reflectionInboundPortURI + "-cm-outbound-port", this);
		this.contentManagementOutboundPort.publishPort();
		this.contentManagementInboundPort = new ContentManagementInboundPort(reflectionInboundPortURI + "-cm-inbound-port", this);
		this.contentManagementInboundPort.publishPort();

		this.getTracer().setTitle("Peer : " + reflectionInboundPortURI);
		this.getTracer().setRelativePosition(this.id % 4, 1 + this.id / 4);

		this.neighboringNodes = new HashSet<>();

		this.processedDescriptors = new ArrayList<>();
	}

	@Override
	public void execute() throws Exception {
		super.execute();
		Thread.sleep(1000L * this.id);
		this.neighboringNodes = this.nodeManagementOutboundPort.joinOld(new ContentNodeAddress(this.nodeInboundPort.getPortURI(), this.contentManagementInboundPort.getPortURI()));
		Thread.sleep(1000L * (/*20 -*/ 2L * this.id));
		this.traceMessage("BEEP " + this.reflectionURI + "\n");
		//System.out.println("BEEP " + this.reflectionURI);
		Random rnd = new Random();
		ArrayList<PeerNodeAddressI> neighbors = new ArrayList<>(this.neighboringNodes);
		int picked;
		PeerNodeAddress myself = new ContentNodeAddress(this.nodeInboundPort.getPortURI(), this.contentManagementInboundPort.getPortURI());
		if (neighbors.size() > 0) {
			picked = rnd.nextInt(neighbors.size());
			PeerNodeAddressI neighbor;
			for (int i = 0; i < 2; i++) {
				neighbor = neighbors.get((picked + i) % neighbors.size());
				PeerNodeAddressI finalNeighbor = neighbor;
				this.runTask(new AbstractTask() {
					@Override
					public void run() {
						try {
							((Peer) this.getTaskOwner()).connect(finalNeighbor);
							((Peer) this.getTaskOwner()).nodeOutboundPort.connectOld(myself);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				});
			}
		}

	}

	@Override
	public synchronized void start() throws ComponentStartException {
		super.start();
		ArrayList<HashMap<String, Object>> readDescriptors;
		try {
			readDescriptors = ContentDataManager.readDescriptors(this.id);
		} catch (ClassNotFoundException | IOException e) {
			throw new RuntimeException(e);
		}

		for (HashMap<String, Object> h : readDescriptors) {
			Set<String> interpreters, composers;
			if (h.get(ContentDataManager.INTERPRETERS_KEY) != null)
				interpreters = new HashSet<>((ArrayList<String>) h.get(ContentDataManager.INTERPRETERS_KEY));
			else interpreters = null;
			if (h.get(ContentDataManager.COMPOSERS_KEY) != null)
				composers = new HashSet<>((ArrayList<String>) h.get(ContentDataManager.COMPOSERS_KEY));
			else composers = null;
			ContentDescriptorI processed = new ContentDescriptor((Long) h.get(ContentDataManager.SIZE_KEY), (String) h.get(ContentDataManager.TITLE_KEY), (String) h.get(ContentDataManager.ALBUM_TITLE_KEY), interpreters, composers);
			this.processedDescriptors.add(processed);
			this.traceMessage(ContentDataManager.toString(h) + "\n");
		}

	}

	@Override
	public synchronized void finalise() throws Exception {
		this.doPortDisconnection(this.nodeManagementOutboundPort.getPortURI());
		this.nodeManagementOutboundPort.unpublishPort();
		this.doPortDisconnection(this.nodeOutboundPort.getPortURI());
		this.nodeOutboundPort.unpublishPort();
		this.nodeInboundPort.unpublishPort();
		if (isPortConnected(this.contentManagementOutboundPort.getPortURI()))
			this.doPortDisconnection(this.contentManagementOutboundPort.getPortURI());
		this.contentManagementOutboundPort.unpublishPort();
		this.contentManagementInboundPort.unpublishPort();
		super.finalise();
	}

	public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception {
		if (!isPortConnected(this.nodeOutboundPort.getPortURI())) {
			this.traceMessage("Connecting " + this.nodeOutboundPort.getPortURI() + " to " + a.getNodeURI() + "\n");
			this.doPortConnection(this.nodeOutboundPort.getPortURI(), a.getNodeURI(), NodeConnector.class.getCanonicalName());
			PeerNodeAddress myself = new ContentNodeAddress(this.nodeInboundPort.getPortURI(), this.contentManagementInboundPort.getPortURI());
			this.neighboringNodes.add(a);
			return myself;
		}
		return null;
	}

	public void disconnect(PeerNodeAddressI a) throws Exception {
		if (isPortConnected(this.nodeOutboundPort.getPortURI())) {
			this.traceMessage("Disconnecting " + this.nodeOutboundPort.getPortURI() + " from " + a.getNodeURI() + "\n");
			this.doPortDisconnection(this.nodeOutboundPort.getPortURI());
			this.neighboringNodes.remove(a);
		}
	}

	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		boolean hasDesc = false;
		ContentDescriptorI r = null;
		for (ContentDescriptorI descriptor : this.processedDescriptors) {
			if (descriptor.match(cd)) {
				hasDesc = true;
				r = descriptor;
			}
		}
		if (hasDesc) {
			this.traceMessage("HAS THE DESCRIPTOR!\n");
			return r;
		} else if (hops > 0) {
			this.traceMessage(this.reflectionURI + ": No descriptor. Sending search to neighbors...\n");
			int i = 0;
			for (PeerNodeAddressI neighbor : this.neighboringNodes) {
				i++;
				if (i < 3) {
					this.traceMessage("Sending search from " + this.contentManagementOutboundPort.getPortURI() + " to " + ((ContentNodeAddressI) neighbor).getContentManagementURI() + "\n");
					if (isPortConnected(contentManagementOutboundPort.getPortURI())) {
						this.doPortDisconnection(contentManagementOutboundPort.getPortURI());
					}
					this.doPortConnection(this.contentManagementOutboundPort.getPortURI(), ((ContentNodeAddressI) neighbor).getContentManagementURI(), ContentManagementConnector.class.getCanonicalName());
					r = this.contentManagementOutboundPort.findOld(cd, hops - 1);
					if (r != null) return r;
					Thread.sleep(500L);
				}
			}
			this.traceMessage("All current searches sent.\n");
		}
		return null;
	}

	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws Exception {
		Set<ContentDescriptorI> r = new HashSet<>(matched);
		for (ContentDescriptorI descriptor : this.processedDescriptors) {
			if (descriptor.match(cd)) {
				r.add(descriptor);
				this.traceMessage("Found matching descriptor: " + descriptor + "\n");
			}
		}
		if (hops <= 0)
			return r;
		else {
			Thread.sleep(500L);
			Random rnd = new Random();
			ArrayList<PeerNodeAddressI> neighbors = new ArrayList<>(this.neighboringNodes);
			PeerNodeAddressI picked = neighbors.get(rnd.nextInt(neighbors.size()));
			this.traceMessage("Sending search from " + this.contentManagementOutboundPort.getPortURI() + " to " + ((ContentNodeAddressI) picked).getContentManagementURI() + "\n");
			if (isPortConnected(contentManagementOutboundPort.getPortURI())) {
				this.doPortDisconnection(contentManagementOutboundPort.getPortURI());
			}
			this.doPortConnection(this.contentManagementOutboundPort.getPortURI(), ((ContentNodeAddressI) picked).getContentManagementURI(), ContentManagementConnector.class.getCanonicalName());
			return this.contentManagementOutboundPort.matchOld(cd, r, hops - 1);
		}
	}

	public int getId() {
		return id;
	}

	public ArrayList<ContentDescriptorI> getProcessedDescriptors() {
		return processedDescriptors;
	}

	public String getReflectionURI() {
		return reflectionURI;
	}
}

