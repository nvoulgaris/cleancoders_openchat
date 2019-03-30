package org.openchat.domain.user;

import java.util.Objects;

public class RegistrationDto {

    private final String username;
    private final String password;
    private final String about;

    public RegistrationDto(String username, String password, String about) {
        this.username = username;
        this.password = password;
        this.about = about;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationDto that = (RegistrationDto) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(about, that.about);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, about);
    }
}
