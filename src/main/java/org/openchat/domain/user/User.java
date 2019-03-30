package org.openchat.domain.user;

public class User {

    private final String id;
    private final String username;
    private final String password;
    private final String about;

    public User(String id, String username, String password, String about) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAbout() {
        return about;
    }
}
