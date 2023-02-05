package cps.tme.codecomrades.componentinterfaces;

import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface NodeCI extends RequiredCI {
    PeerNodeAddressI connect(PeerNodeAddressI a);
    void disconnect(PeerNodeAddressI a);
}