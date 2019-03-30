package org.openchat.domain.user;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private Map<String, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    public boolean alreadyInUse(String username) {
        return users.containsKey(username);
    }

    public void save(User user) {
        users.put(user.getUsername(), user);
    }
}
