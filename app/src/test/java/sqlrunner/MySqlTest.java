package sqlrunner;

import java.util.Date;

import pojo.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MySqlTest {
    private MySql msql;

    @Test
    public void shouldSelectOne() {
        String queryId = "findActors";
        FindActorsParam queryParam = new FindActorsParam(87, "PECK");

        try {
            msql = new MySql("src/test/resources/queries.xml", "cs305", "password", "sakila");
        } catch (Exception e) {
            e.printStackTrace();
        }

        FindActorsResult res = null;

        try {
            res = msql.selectOne(queryId, queryParam, FindActorsResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(res.actor_id, "87");
        assertEquals(res.first_name, "SPENCER");
        assertEquals(res.last_name, "PECK");
        assertEquals(res.last_update, "2006-02-15 04:34:33");
    }

    @Test
    public void shouldSelectMany() {
        String queryId = "findManyActors";
        FindManyActorsParam queryParam = new FindManyActorsParam("PECK");

        try {
            msql = new MySql("src/test/resources/queries.xml", "cs305", "password", "sakila");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<FindActorsResult> res = null;

        try {
            res = (ArrayList<FindActorsResult>) msql.selectMany(queryId, queryParam, FindActorsResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(res.size(), 3);
        assertEquals(res.get(0).actor_id, "30");
        assertEquals(res.get(1).actor_id, "33");
        assertEquals(res.get(2).actor_id, "87");

        
    }

    @Test
    public void shouldUpdate() {
        String queryId = "updateActor";
        UpdateActorsParam queryParam = new UpdateActorsParam("TOM", "HOLLAND", 1);

        try {
            msql = new MySql("src/test/resources/queries.xml", "cs305", "password", "sakila");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int res = -1;

        try {
            res = msql.update(queryId, queryParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(res, 1);
    }

    @Test
    public void shouldInsert() {
        String queryId = "insertActor";
        InsertActorsParam queryParam = new InsertActorsParam("GAL", "GADOT");

        try {
            msql = new MySql("src/test/resources/queries.xml", "cs305", "password", "sakila");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int res = -1;

        try {
            res = msql.insert(queryId, queryParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(res, 1);
    }

    @Test
    public void shouldDelete() {
        String queryId = "deleteActor";
        DeleteActorsParam queryParam = new DeleteActorsParam("SAGALPREET");

        try {
            msql = new MySql("src/test/resources/queries.xml", "cs305", "password", "sakila");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int res = -1;

        try {
            res = msql.delete(queryId, queryParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(res, 0);
    }
}
