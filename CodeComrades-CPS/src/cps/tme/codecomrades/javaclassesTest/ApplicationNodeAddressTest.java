import cps.tme.codecomrades.javaclasses.ApplicationNodeAddress;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationNodeAddressTest {

    @Test
    public void testGetContentManagementURI() {
        String nodeManagementURI = "node-management-uri";
        String contentManagementURI = "content-management-uri";
        ApplicationNodeAddress address = new ApplicationNodeAddress(nodeManagementURI, contentManagementURI);
        assertEquals(contentManagementURI, address.getContentManagementURI());
    }
}