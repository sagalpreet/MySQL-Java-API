package xml;

import java.io.File;
import java.util.HashMap;

import javax.swing.text.html.HTML.Tag;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    private Reader r;

    @Test
    public void shouldReader() {
        String path = "src/test/resources/queries.xml";
        r = new Reader(path);

        String f = r.getFilepath();

        assertNotEquals(f, null);
    }

    @Test
    public void shouldFetchQuerySyntax() {
        String queryID = "addMovie";
        try {
            HashMap<String, String> hm = r.fetchQuerySyntax(queryID);
            assertEquals(hm.get("tag"), "sql");
            assertEquals(hm.get("paramType"), "org.foo.Bar");
            assertEquals(hm.get("query"), "INSERT INTO my_table(x, y, x) VALUES(${propX}, ${propY}, ${propZ});");
        } catch (Exception e) {
            System.out.println("Failed");
            // e.printStackTrace();
        }

    }

}
