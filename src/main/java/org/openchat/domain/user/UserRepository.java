package org.openchat.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class UserRepository {

    private List<User> users;
    private List<Following> followings;

    public UserRepository() {
        this.users = new ArrayList<>();
        this.followings = new ArrayList<>();
    }

    public boolean alreadyInUse(String username) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    public void save(User user) {
        users.add(user);
    }

    public Optional<User> fetchWith(CredentialsDto credentials) {
        return users.stream()
                .filter(user -> user.getUsername().equals(credentials.getUsername()) && user.getPassword().equals(credentials.getPassword()))
                .findFirst();
    }

    public boolean followingExists(Following following) {
        return followings.stream()
                .anyMatch(following::equals);
    }

    public void saveFollowing(Following following) {
        followings.add(following);
    }

    public List<User> all() {
        return users;
    }

    public List<User> followeesFor(String followerId) {
        return followings.stream()
                .filter(following -> following.getFollowerId().equals(followerId))
                .map(following -> userById(following.getFolloweeId()))
                .collect(toList());
    }

    private User userById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
