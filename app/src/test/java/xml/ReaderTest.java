package xml;

import java.io.File;
import java.util.HashMap;

import javax.swing.text.html.HTML.Tag;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    @Test
    public void shouldReader() {
        String path = "src/test/resources/queries.xml";
        Reader r = new Reader(path);

        String f = r.getFilepath();

        assertNotEquals(f, null);
    }

    @Test
    public void shouldFetchQuerySyntax() {
        String path = "src/test/resources/queries.xml";
        Reader r = new Reader(path);

        String queryID = "findActors";
        try {
            HashMap<String, String> hm = r.fetchQuerySyntax(queryID);
            assertEquals(hm.get("tag"), "sql");
            assertEquals(hm.get("paramType"), "org.foo.Bar");
            assertEquals(hm.get("query"), "SELECT * FROM actor WHERE actor_id=${actor_id} AND last_name=${last_name};");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
