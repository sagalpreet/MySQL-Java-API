package date;

import java.util.Date;
import java.util.HashMap;

public class MysqlDate {
    // hashmap for mapping months to numbers
    private static HashMap<String, String> months = new HashMap<String, String>() {{
        put("Jan", "01");
        put("Feb", "02");
        put("Mar", "03");
        put("Apr", "04");
        put("May", "05");
        put("Jun", "06");
        put("Jul", "07");
        put("Aug", "08");
        put("Sep", "09");
        put("Oct", "10");
        put("Nov", "11");
        put("Dec", "12");
    }};

    public static String  toMysqlDate(Date date) {
        // an example of the two formats
        // 2006-02-15 04:34:33
        // Fri Feb 11 01:11:12 IST 2022

        String javaDate = date.toString(); // convert to string
        int n = javaDate.length(); // returns the length of the string

        // extract numbers from one string and construct the other one
        String mySqlDate =  javaDate.substring(n-4, n) + "-" + 
                            months.get(javaDate.substring(4, 7)) + "-" +
                            javaDate.substring(8, 10) + " " +
                            javaDate.substring(11, 19);

        return mySqlDate; // return date in the format supported by MySQL
    }
}
