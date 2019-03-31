package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryShould {

    private static final String ID = UUID.randomUUID().toString();
    private static final String ID_2 = UUID.randomUUID().toString();
    private static final String USERNAME = "a username";
    private static final String USERNAME_2 = "a second username";
    private static final String PASSWORD = "a password";
    private static final String ABOUT = "about me";
    private static final String INVALID_USERNAME = "an invalid username";
    private static final String INVALID_PASSWORD = "an invalid password";

    private UserRepository userRepository;
    private User user1;
    private User user2;

    @Before
    public void setup() {
        initMocksBehavior();
        userRepository = new UserRepository();
    }

    @Test
    public void informWhenUsernameIsAlreadyInUse() {
        userRepository.save(user1);
        userRepository.save(user1);

        boolean result = userRepository.alreadyInUse(USERNAME);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void returnEmptyWhenUsernameDoesNotExist() {
        CredentialsDto credentialsWithInvalidUsername = new CredentialsDto(INVALID_USERNAME, PASSWORD);

        Optional<User> result = userRepository.fetchWith(credentialsWithInvalidUsername);

        assertThat(result).isEmpty();
    }

    @Test
    public void returnEmptyWhenUsernameExistsButPasswordIsInvalid() {
        CredentialsDto credentialsWithInvalidPassword = new CredentialsDto(USERNAME, INVALID_PASSWORD);

        Optional<User> result = userRepository.fetchWith(credentialsWithInvalidPassword);

        assertThat(result).isEmpty();
    }

    @Test
    public void returnUserWhenUsernameAndPasswordAreValid() {
        CredentialsDto validCredentials = new CredentialsDto(USERNAME, PASSWORD);
        userRepository.save(user1);

        Optional<User> result = userRepository.fetchWith(validCredentials);

        assertThat(result.get()).isEqualTo(user1);
    }

    @Test
    public void returnAllUsers() {
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> result = userRepository.all();

        assertThat(result).containsExactlyInAnyOrder(user1, user2);
    }

    private void initMocksBehavior() {
        user1 = new User(ID, USERNAME, PASSWORD, ABOUT);
        user2 = new User(ID_2, USERNAME_2, PASSWORD, ABOUT);
    }
}
