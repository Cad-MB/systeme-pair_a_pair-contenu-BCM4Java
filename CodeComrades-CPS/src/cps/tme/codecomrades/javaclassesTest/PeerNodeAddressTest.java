import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cps.tme.codecomrades.javaclasses.PeerNodeAddress;
import org.junit.jupiter.api.Test;

import cps.tme.codecomrades.javainterfaces.PeerNodeAddressI;

public class PeerNodeAddressTest {

    @Test
    public void testGetNodeIdentifier() {
        String nodeURI = "nodeURI";
        PeerNodeAddressI peerNodeAddress = new PeerNodeAddress(nodeURI);
        assertEquals(nodeURI, peerNodeAddress.getNodeIdentifier());
    }

    @Test
    public void testIsFacade() {
        PeerNodeAddressI peerNodeAddress = new PeerNodeAddress("nodeURI");
        assertFalse(peerNodeAddress.isFacade());
    }

    @Test
    public void testIsPeer() {
        PeerNodeAddressI peerNodeAddress = new PeerNodeAddress("nodeURI");
        assertTrue(peerNodeAddress.isPeer());
    }

    @Test
    public void testGetNodeURI() {
        String nodeURI = "nodeURI";
        PeerNodeAddressI peerNodeAddress = new PeerNodeAddress(nodeURI);
        assertEquals(nodeURI, peerNodeAddress.getNodeURI());
    }
}
