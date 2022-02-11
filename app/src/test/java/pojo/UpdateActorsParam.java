package pojo;

public class UpdateActorsParam {
    public String first_name, last_name;
    public int actor_id;

    public UpdateActorsParam(String a, String b, int c) {
        first_name = a;
        last_name = b;
        actor_id = c;
    }
}
