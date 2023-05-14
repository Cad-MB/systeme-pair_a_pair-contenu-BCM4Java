package cps.tme.codecomrades.plugins;

import cps.tme.codecomrades.components.Peer;
import cps.tme.codecomrades.connectors.ContentManagementConnector;
import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.interfaces.FacadeContentManagementCI;
import cps.tme.codecomrades.javaclasses.ContentNodeAddress;
import cps.tme.codecomrades.javainterfaces.*;
import cps.tme.codecomrades.ports.ContentManagementOutboundPort;
import cps.tme.codecomrades.ports.FacadeContentManagementOutboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;

import java.util.*;

public class ContentManagementPlugin extends PeerNodePlugin {
	protected ContentManagementOutboundPort cmop;

	protected HashMap<ContentNodeAddressI, ContentManagementOutboundPort> cmopMap;
	protected FacadeContentManagementOutboundPort fcmop;
	protected int id;

	protected ArrayList<ContentDescriptorI> processedDescriptors;
	protected ContentNodeAddressI cmAdr;

	public ContentManagementPlugin() {
		super();
	}

	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);

		this.cmAdr = new ContentNodeAddress(((Peer) this.getOwner()).getReflectionURI(), ((Peer) this.getOwner()).getReflectionURI() + "-cntMgmt");

		this.addRequiredInterface(ContentManagementCI.class);

		this.cmop = new ContentManagementOutboundPort(this.getOwner());
		this.cmop.publishPort();

		this.addRequiredInterface(FacadeContentManagementCI.class);

		this.fcmop = new FacadeContentManagementOutboundPort(this.cmAdr.getNodeURI() + "-fcmop", this.getOwner());
		this.fcmop.publishPort();

		this.id = ((Peer) getOwner()).getId();
		this.processedDescriptors = ((Peer) getOwner()).getProcessedDescriptors();

		this.cmopMap = new HashMap<>();
	}

	@Override
	public void initialise() throws Exception {
		super.initialise();
		int n = 0;
		ContentManagementOutboundPort cmop;
		for (PeerNodeAddressI neighbor : neighboringNodes) {
			cmop = new ContentManagementOutboundPort(this.cmAdr.getNodeURI() + "-cmop-" + n, this.getOwner());
			cmop.publishPort();
			cmop.doConnection(((ContentNodeAddressI) neighbor).getContentManagementURI(), ContentManagementConnector.class.getCanonicalName());
			cmopMap.put((ContentNodeAddressI) neighbor, cmop);
			n++;
		}
	}

	@Override
	public void finalise() throws Exception {
		for (ContentManagementOutboundPort cmop : cmopMap.values()) {
			cmop.doDisconnection();
		}
		super.finalise();
	}

	@Override
	public void uninstall() throws Exception {
		for (ContentManagementOutboundPort cmop : cmopMap.values()) {
			cmop.unpublishPort();
			cmop.destroyPort();
		}

		this.cmop.unpublishPort();
		this.cmop.destroyPort();
		this.removeRequiredInterface(ContentManagementCI.class);

		this.fcmop.unpublishPort();
		this.fcmop.destroyPort();
		this.removeRequiredInterface(FacadeContentManagementCI.class);

		super.uninstall();
	}

	public ContentDescriptorI findOld(ContentTemplateI cd, int hops) throws Exception {
		boolean hasDesc = false;
		ContentDescriptorI r = null;
		for (ContentDescriptorI descriptor : this.processedDescriptors) {
			if (descriptor.match(cd)) {
				hasDesc = true;
				r = descriptor;
			}
		}
		if (hasDesc) {
			this.getOwner().traceMessage("HAS THE DESCRIPTOR!\n");
			return r;
		} else if (hops > 0) {
			this.getOwner().traceMessage(this.id + ": No descriptor. Sending search to neighbors...\n");
			int i = 0;
			for (PeerNodeAddressI neighbor : this.neighboringNodes) {
				i++;
				if (i < 3) {
					this.getOwner().traceMessage("Sending search from " + this.cmop.getPortURI() + " to " + ((ContentNodeAddressI) neighbor).getContentManagementURI() + "\n");
					if (getOwner().isPortConnected(this.cmop.getPortURI())) {
						this.getOwner().doPortDisconnection(this.cmop.getPortURI());
					}
					this.getOwner().doPortConnection(this.cmop.getPortURI(), ((ContentNodeAddressI) neighbor).getContentManagementURI(), ContentManagementConnector.class.getCanonicalName());
					r = this.cmop.findOld(cd, hops - 1);
					if (r != null) return r;
					Thread.sleep(500L);
				}
			}
			this.getOwner().traceMessage("All current searches sent.\n");
		}
		return null;
	}

	public void find(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI) throws Exception {
		this.getOwner().doPortConnection(this.fcmop.getPortURI(), requester.getContentManagementURI(), ContentManagementConnector.class.getCanonicalName());
		for (ContentDescriptorI descriptor : this.processedDescriptors) {
			if (descriptor.match(cd)) {
				this.getOwner().traceMessage("HAS THE DESCRIPTOR!\n");
				this.fcmop.acceptFound(descriptor, requestURI);
				this.getOwner().doPortDisconnection(this.fcmop.getPortURI());
				return;
			}
		}
		if (hops <= 0) {
			this.fcmop.acceptFound(null, requestURI);
			this.getOwner().doPortDisconnection(this.fcmop.getPortURI());
			return;
		}
		this.getOwner().doPortDisconnection(this.fcmop.getPortURI());
		this.getOwner().traceMessage(this.id + ": No descriptor. Sending search to neighbors...\n");
		int i = 0;
		for (PeerNodeAddressI neighbor : this.neighboringNodes) {
			i++;
			if (i < 3) {
				this.getOwner().traceMessage("Sending search from " + this.cmAdr.getNodeURI() + " to " + neighbor.getNodeURI() + "\n");
				this.getOwner().runTask(new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							cmopMap.get((ContentNodeAddressI) neighbor).find(cd, hops - 1, requester, requestURI);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				});
			}
		}
		this.getOwner().traceMessage("All current searches sent.\n");
	}

	public Set<ContentDescriptorI> matchOld(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops) throws Exception {
		Set<ContentDescriptorI> r = new HashSet<>(matched);
		for (ContentDescriptorI descriptor : this.processedDescriptors) {
			if (descriptor.match(cd)) {
				r.add(descriptor);
				this.getOwner().traceMessage("Found matching descriptor: " + descriptor + "\n");
			}
		}
		if (hops <= 0) return r;
		else {
			Thread.sleep(500L);
			Random rnd = new Random();
			ArrayList<PeerNodeAddressI> neighbors = new ArrayList<>(this.neighboringNodes);
			PeerNodeAddressI picked = neighbors.get(rnd.nextInt(neighbors.size()));
			this.getOwner().traceMessage("Sending search from " + this.cmop.getPortURI() + " to " + ((ContentNodeAddressI) picked).getContentManagementURI() + "\n");
			if (getOwner().isPortConnected(cmop.getPortURI())) {
				this.getOwner().doPortDisconnection(cmop.getPortURI());
			}
			this.getOwner().doPortConnection(this.cmop.getPortURI(), ((ContentNodeAddressI) picked).getContentManagementURI(), ContentManagementConnector.class.getCanonicalName());
			return this.cmop.matchOld(cd, r, hops - 1);
		}
	}

	public void match(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI, Set<ContentDescriptorI> matched) throws Exception {
		Set<ContentDescriptorI> r = new HashSet<>(matched);
		for (ContentDescriptorI descriptor : this.processedDescriptors) {
			if (descriptor.match(cd)) {
				r.add(descriptor);
				this.getOwner().traceMessage("Found matching descriptor: " + descriptor + "\n");
			}
		}
		if (hops <= 0) {
			this.fcmop.acceptMatched(r, requestURI);
		} else {
			Random rnd = new Random();
			ArrayList<PeerNodeAddressI> neighbors = new ArrayList<>(this.neighboringNodes);
			PeerNodeAddressI picked = neighbors.get(rnd.nextInt(neighbors.size()));
			this.getOwner().traceMessage("Sending search from " + this.cmAdr.getNodeURI() + " to " + ((ContentNodeAddressI) picked).getContentManagementURI() + "\n");
			this.cmopMap.get((ContentNodeAddressI) picked).match(cd, hops - 1, requester, requestURI, r);
		}
		this.getOwner().doPortDisconnection(this.fcmop.getPortURI());
	}

}
