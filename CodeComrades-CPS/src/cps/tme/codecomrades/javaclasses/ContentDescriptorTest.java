package cps.tme.codecomrades.javaclasses;

import org.junit.Test;
import static org.junit.Assert.*;

public class ContentDescriptorTest {

    @Test
    public void testContentDescriptor() {
        long size = 100;
        String title = "Title";
        String albumTitle = "Album Title";
        Set<String> interpreters = Set.of("Interpreter 1", "Interpreter 2");
        Set<String> composers = Set.of("Composer 1", "Composer 2");
        ContentDescriptor cd = new ContentDescriptor(size, title, albumTitle, interpreters, composers);
        ContentNodeAddressI cna = new ApplicationNodeAddress("nodeManagementURI", "contentManagementURI");
        cd.setContentNodeAddress(cna);
        assertEquals(size, cd.getSize());
        assertEquals(title, cd.getTitle());
        assertEquals(albumTitle, cd.getAlbumTitle());
        assertEquals(interpreters, cd.getInterpreters());
        assertEquals(composers, cd.getComposers());
        assertEquals(cna, cd.getContentNodeAddress());
        assertTrue(cd.match(cd));
    }
}
