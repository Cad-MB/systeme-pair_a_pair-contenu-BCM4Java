package cps.tme.codecomrades.javaclasses;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationNodeAddressTest {

    @Test
    public void testGetContentManagementURI() {
        String nodeManagementURI = "node-management-uri";
        String contentManagementURI = "content-management-uri";
        ApplicationNodeAddress address = new ApplicationNodeAddress(nodeManagementURI, contentManagementURI);
        assertEquals(contentManagementURI, address.getContentManagementURI());
    }
}