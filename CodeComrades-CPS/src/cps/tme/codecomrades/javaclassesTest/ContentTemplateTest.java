import cps.tme.codecomrades.javaclasses.ContentTemplate;
import cps.tme.codecomrades.javainterfaces.ContentTemplateI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class ContentTemplateTest {

    @Test
    public void testGetTitle() {
        String title = "Title";
        String albumTitle = "Album";
        Set<String> interpreters = new HashSet<>();
        Set<String> composers = new HashSet<>();

        ContentTemplateI template = new ContentTemplate(title, albumTitle, interpreters, composers);

        Assertions.assertEquals(title, template.getTitle());
    }

    @Test
    public void testGetAlbumTitle() {
        String title = "Title";
        String albumTitle = "Album";
        Set<String> interpreters = new HashSet<>();
        Set<String> composers = new HashSet<>();

        ContentTemplateI template = new ContentTemplate(title, albumTitle, interpreters, composers);

        Assertions.assertEquals(albumTitle, template.getAlbumTitle());
    }

    @Test
    public void testGetInterpreters() {
        String title = "Title";
        String albumTitle = "Album";
        Set<String> interpreters = new HashSet<>();
        interpreters.add("Interpreter1");
        interpreters.add("Interpreter2");
        Set<String> composers = new HashSet<>();

        ContentTemplateI template = new ContentTemplate(title, albumTitle, interpreters, composers);

        Assertions.assertEquals(interpreters, template.getInterpreters());
    }

    @Test
    public void testGetComposers() {
        String title = "Title";
        String albumTitle = "Album";
        Set<String> interpreters = new HashSet<>();
        Set<String> composers = new HashSet<>();
        composers.add("Composer1");
        composers.add("Composer2");

        ContentTemplateI template = new ContentTemplate(title, albumTitle, interpreters, composers);

        Assertions.assertEquals(composers, template.getComposers());
    }
}
