package cps.tme.codecomrades.plugins;

import cps.tme.codecomrades.components.Facade;
import cps.tme.codecomrades.connectors.ContentManagementConnector;
import cps.tme.codecomrades.interfaces.ContentManagementCI;
import cps.tme.codecomrades.interfaces.FacadeContentManagementCI;
import cps.tme.codecomrades.javaclasses.ApplicationNodeAddress;
import cps.tme.codecomrades.javainterfaces.*;
import cps.tme.codecomrades.ports.ContentManagementOutboundPort;
import cps.tme.codecomrades.ports.FacadeContentManagementInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;

import java.util.*;

public class FacadeContentManagementPlugin extends AbstractPlugin {

	protected ApplicationNodeAddressI appAdr;
	protected FacadeContentManagementInboundPort fcmip;
	protected HashMap<ContentNodeAddressI, ContentManagementOutboundPort> cmopMap;
	protected String rfl;
	protected ArrayList<ContentTemplateI> processedTemplates;
	protected Map<ContentTemplateI, String> tplReqMap;
	protected Map<String, ContentDescriptorI> foundMap;
	protected Map<String, Set<ContentDescriptorI>> matchedMap;

	protected Set<PeerNodeAddressI> rootNodes;
	public static int INITIAL_CALLS = 2;

	public FacadeContentManagementPlugin() {
		super();
	}

	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);
		this.rfl = ((Facade) this.getOwner()).getReflectionURI();
		this.appAdr = new ApplicationNodeAddress(rfl + "-nodeMgmt", rfl + "-fCntMgmt");

		this.addOfferedInterface(FacadeContentManagementCI.class);

		this.addRequiredInterface(ContentManagementCI.class);
		this.cmopMap = new HashMap<>();


		this.processedTemplates = ((Facade) getOwner()).getProcessedTemplates();
		this.rootNodes = ((Facade) getOwner()).getRootNodes();
		tplReqMap = new HashMap<>();
		foundMap = new HashMap<>();
		matchedMap = new HashMap<>();


	}

	@Override
	public void initialise() throws Exception {
		super.initialise();

		this.fcmip = new FacadeContentManagementInboundPort(this.appAdr.getContentManagementURI(), this.getOwner());
		this.fcmip.publishPort();
		int n = 0;
		for (ContentTemplateI template : processedTemplates) {
			tplReqMap.put(template, rfl + "-req" + n);
			n++;
		}

		ContentManagementOutboundPort cmop;
		for (PeerNodeAddressI rootNode : rootNodes) {
			cmop = new ContentManagementOutboundPort(rfl + "-cmop-" + n, this.getOwner());
			cmop.publishPort();
			cmop.doConnection(((ContentNodeAddressI) rootNode).getContentManagementURI(), ContentManagementConnector.class.getCanonicalName());
			cmopMap.put((ContentNodeAddressI) rootNode, cmop);
			n++;
		}

	}

	@Override
	public void finalise() throws Exception {

		this.getOwner().doPortDisconnection(this.fcmip.getPortURI());

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
		this.removeRequiredInterface(ContentManagementCI.class);
		this.fcmip.unpublishPort();
		this.fcmip.destroyPort();
		this.removeOfferedInterface(FacadeContentManagementCI.class);
		super.uninstall();
	}

	public void find(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI) throws Exception {
		this.getOwner().traceMessage("Starting Find Request\n");
		Random rnd = new Random();
		ArrayList<PeerNodeAddressI> roots = new ArrayList<>(this.rootNodes);
		int picked = rnd.nextInt(roots.size());
		PeerNodeAddressI root;
		ContentManagementOutboundPort cmop;
		for (int i = 0; i < INITIAL_CALLS; i++) {
			root = roots.get((picked + i) % roots.size());
			this.getOwner().traceMessage("Sending search to " + ((ContentNodeAddressI) root).getContentManagementURI() + "\n");
			cmop = cmopMap.get((ContentNodeAddressI) root);
			ContentManagementOutboundPort cmopCopy = cmop;
			this.getOwner().runTask(new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						cmopCopy.find(cd, hops, requester, requestURI);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
	}
	
	public void match(ContentTemplateI cd, int hops, ApplicationNodeAddressI requester, String requestURI, Set<ContentDescriptorI> matched) throws Exception {
		this.getOwner().traceMessage("Starting Match Request\n");
		Random rnd = new Random();
		ArrayList<PeerNodeAddressI> roots = new ArrayList<>(this.rootNodes);
		int picked = rnd.nextInt(roots.size());
		PeerNodeAddressI root;
		ContentManagementOutboundPort cmop;

		for (int i = 0; i < INITIAL_CALLS; i++) {
			root = roots.get((picked + i) % roots.size());
			this.getOwner().traceMessage("Sending match search to " + ((ContentNodeAddressI) root).getContentManagementURI() + "\n");
			cmop = cmopMap.get((ContentNodeAddressI) root);
			ContentManagementOutboundPort cmopCopy = cmop;
			this.getOwner().runTask(new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						cmopCopy.match(cd, hops, requester, requestURI, matched);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
			
			this.cmopMap.get(root).match(cd, hops, requester, requestURI, matched);
		}
	}

	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception {
		foundMap.put(requestURI, found);
	}

	public void acceptMatched(Set<ContentDescriptorI> matched, String requestURI) throws Exception {
		matchedMap.put(requestURI, matched);
	}
}
