package org.openchat.infrastructure;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.CredentialsDto;
import org.openchat.domain.user.Following;
import org.openchat.domain.user.RegistrationDto;
import org.openchat.domain.user.User;

import java.util.List;

public class UserTestParser {

    private static String ID = "id";
    private static String USERNAME = "username";
    private static String PASSWORD = "password";
    private static String ABOUT = "about";
    private static String FOLLOWER_ID = "followerId";
    private static String FOLLOWEE_ID = "followeeId";

    public static String jsonWith(RegistrationDto registrationDto) {
        return new JsonObject()
                .add(USERNAME, registrationDto.getUsername())
                .add(PASSWORD, registrationDto.getPassword())
                .add(ABOUT, registrationDto.getAbout())
                .toString();
    }

    public static String jsonWith(CredentialsDto credentialsDto) {
        return new JsonObject()
                .add(USERNAME, credentialsDto.getUsername())
                .add(PASSWORD, credentialsDto.getPassword())
                .toString();
    }

    public static String jsonWith(List<User> users) {
        JsonArray json = new JsonArray();
        users.forEach(user -> json.add(jsonObjectWith(user)));
        return json.toString();
    }

    public static String jsonWith(User user) {
        return jsonObjectWith(user).toString();
    }

    public static String jsonWith(Following following) {
        return jsonObjectWith(following).toString();
    }

    private static JsonObject jsonObjectWith(Following following) {
        return new JsonObject()
                .add(FOLLOWER_ID, following.getFollowerId())
                .add(FOLLOWEE_ID, following.getFolloweeId());
    }

    private static JsonObject jsonObjectWith(User user) {
        return new JsonObject()
                .add(ID, user.getId())
                .add(USERNAME, user.getUsername())
                .add(ABOUT, user.getAbout());
    }
}
