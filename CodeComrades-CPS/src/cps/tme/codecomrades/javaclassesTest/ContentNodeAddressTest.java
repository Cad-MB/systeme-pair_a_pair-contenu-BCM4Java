import cps.tme.codecomrades.javaclasses.ContentNodeAddress;
import cps.tme.codecomrades.javainterfaces.ContentNodeAddressI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContentNodeAddressTest {

    @Test
    public void testGetContentManagementURI() {
        String nodeURI = "http://localhost:8080/contentNode";
        String contentManagementURI = "http://localhost:8080/contentManagement";
        ContentNodeAddressI contentNodeAddress = new ContentNodeAddress(nodeURI, contentManagementURI);

        String expectedContentManagementURI = contentManagementURI;
        String actualContentManagementURI = contentNodeAddress.getContentManagementURI();

        assertEquals(expectedContentManagementURI, actualContentManagementURI);
    }
}
