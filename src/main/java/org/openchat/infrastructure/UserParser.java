package org.openchat.infrastructure;

import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.User;

public class UserParser {

    public static String jsonWith(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout())
                .toString();
    }
}
