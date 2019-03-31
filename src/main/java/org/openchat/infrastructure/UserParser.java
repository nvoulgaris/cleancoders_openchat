package org.openchat.infrastructure;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.User;

import java.util.List;

public class UserParser {

    public static String jsonWith(List<User> users) {
        JsonArray json = new JsonArray();
        users.forEach(user -> json.add(jsonObjectWith(user)));
        return json.toString();
    }

    public static String jsonWith(User user) {
        return jsonObjectWith(user).toString();
    }

    private static JsonObject jsonObjectWith(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout());
    }
}
