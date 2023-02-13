package cps.tme.codecomrades.interfaces;

import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface NodeCI extends RequiredCI, OfferedCI {
    PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception;
    void disconnect(PeerNodeAddressI a) throws Exception;
}
