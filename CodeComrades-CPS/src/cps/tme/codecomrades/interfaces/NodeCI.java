package cps.tme.codecomrades.interfaces;

import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface NodeCI extends RequiredCI, OfferedCI {
    PeerNodeAddressI connectOld(PeerNodeAddressI a) throws Exception;
    void connect(PeerNodeAddressI peer) throws Exception;
    void disconnectOld(PeerNodeAddressI a) throws Exception;
    void disconnect(PeerNodeAddressI neighbor) throws Exception;
    void acceptNeighbors(Set<PeerNodeAddressI> neighbors) throws Exception;
    void acceptConnected(PeerNodeAddressI neighbor) throws Exception;
}
