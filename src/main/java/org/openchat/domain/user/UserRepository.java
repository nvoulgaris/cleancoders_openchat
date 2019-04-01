package org.openchat.domain.user;

import java.util.*;

public class UserRepository {

    private Map<String, User> users;
    private List<Following> followings;

    public UserRepository() {
        this.users = new HashMap<>();
        this.followings = new ArrayList<>();
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

    public boolean followingExists(Following following) {
        return followings.stream()
                .anyMatch(f -> following.equals(f));
    }

    public void saveFollowing(Following following) {
        followings.add(following);
    }

    public List<User> all() {
        return new ArrayList<>(users.values());
    }

    private boolean invalidPasswordFor(User user, CredentialsDto credentials) {
        return !user.getPassword().equals(credentials.getPassword());
    }
}
