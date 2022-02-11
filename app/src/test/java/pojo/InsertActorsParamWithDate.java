package pojo;

import java.util.*;

public class InsertActorsParamWithDate {
    public String first_name;
    public String last_name;
    public Date last_update;

    public InsertActorsParamWithDate(String first_name, String last_name, Date date) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.last_update = date;
    }
}