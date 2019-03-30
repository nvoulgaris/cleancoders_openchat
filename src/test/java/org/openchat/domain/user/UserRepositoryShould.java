package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryShould {

    private static final String ID = UUID.randomUUID().toString();
    private static final String USERNAME = "a username";
    private static final String PASSWORD = "a password";
    private static final String ABOUT = "about me";

    private UserRepository userRepository;
    private User user;

    @Before
    public void setup() {
        user = new User(ID, USERNAME, PASSWORD, ABOUT);
        userRepository = new UserRepository();
    }

    @Test
    public void informWhenUsernameIsAlreadyInUse() {
        userRepository.save(user);
        userRepository.save(user);

        boolean result = userRepository.alreadyInUse(USERNAME);

        assertThat(result).isEqualTo(true);
    }
}
