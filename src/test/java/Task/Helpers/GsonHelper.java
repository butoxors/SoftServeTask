package Task.Helpers;

import Task.Models.User;
import com.google.gson.Gson;

public class GsonHelper {
    private static Gson g;
    static{
        g = new Gson();
    }

    public static String serialize(User user){
        return g.toJson(user);
    }

    public static User deserialize(String json){
        User user = g.fromJson(json, User.class);
        return user;
    }
}
