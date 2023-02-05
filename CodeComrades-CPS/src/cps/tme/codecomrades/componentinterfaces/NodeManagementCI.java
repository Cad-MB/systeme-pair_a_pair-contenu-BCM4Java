package cps.tme.codecomrades.componentinterfaces;

import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface NodeManagementCI extends RequiredCI {
    Set<PeerNodeAddressI> join(PeerNodeAddressI a);
    void leave(PeerNodeAddressI a);
}
