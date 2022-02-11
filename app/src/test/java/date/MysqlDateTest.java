package date;

import java.util.Date;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MysqlDateTest {
    
    @Test public void shouldToMysqlDate() {
        Date date = new Date(122, 1, 11, 1, 11, 12);

        System.out.println(date);

        String mysqlDate = MysqlDate.toMysqlDate(date);
        assertEquals(mysqlDate, "2022-02-11 01:11:12");
    }
}
