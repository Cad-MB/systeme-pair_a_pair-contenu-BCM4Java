package cps.tme.codecomrades.interfaces;

import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface NodeManagementCI extends RequiredCI, OfferedCI {
    Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception;

    void leave(PeerNodeAddressI a) throws Exception;
}
