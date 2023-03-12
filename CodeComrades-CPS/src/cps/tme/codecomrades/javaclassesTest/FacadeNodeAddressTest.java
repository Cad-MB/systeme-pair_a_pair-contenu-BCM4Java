import cps.tme.codecomrades.javaclasses.FacadeNodeAddress;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FacadeNodeAddressTest {

    @Test
    void testGetNodeManagementURI() {
        String expected = "http://localhost:8080";
        FacadeNodeAddress address = new FacadeNodeAddress(expected);
        String actual = address.getNodeManagementURI();
        assertEquals(expected, actual);
    }

    @Test
    void testGetNodeIdentifier() {
        String expected = "http://localhost:8080";
        FacadeNodeAddress address = new FacadeNodeAddress(expected);
        String actual = address.getNodeIdentifier();
        assertEquals(expected, actual);
    }

    @Test
    void testIsFacade() {
        FacadeNodeAddress address = new FacadeNodeAddress("http://localhost:8080");
        assertTrue(address.isFacade());
    }

    @Test
    void testIsPeer() {
        FacadeNodeAddress address = new FacadeNodeAddress("http://localhost:8080");
        assertFalse(address.isPeer());
    }

}
