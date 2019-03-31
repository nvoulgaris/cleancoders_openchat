package org.openchat.domain.user;

import java.util.*;

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

    public Optional<User> fetchWith(CredentialsDto credentials) {
        User user = users.get(credentials.getUsername());
        if (user == null || invalidPasswordFor(user, credentials))
            return Optional.empty();
        else
            return Optional.of(user);
    }

    private boolean invalidPasswordFor(User user, CredentialsDto credentials) {
        return !user.getPassword().equals(credentials.getPassword());
    }

    public List<User> all() {
        return new ArrayList<>(users.values());
    }
}
