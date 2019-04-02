package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryShould {

    private static final String USERNAME = "a username";
    private static final String USERNAME_2 = "a second username";
    private static final String USERNAME_3 = "a third username";
    private static final String PASSWORD = "a password";
    private static final String ABOUT = "about me";
    private static final String INVALID_USERNAME = "an invalid username";
    private static final String INVALID_PASSWORD = "an invalid password";
    private static final String FOLLOWER_ID = UUID.randomUUID().toString();
    private static final String FOLLOWER_ID_2 = UUID.randomUUID().toString();
    private static final String FOLLOWEE_ID = UUID.randomUUID().toString();
    private static final String FOLLOWEE_ID_2 = UUID.randomUUID().toString();
    private static final String FOLLOWEE_ID_3 = UUID.randomUUID().toString();

    private UserRepository userRepository;
    private User user1;
    private User user2;
    private User user3;
    private Following following1;
    private Following following2;
    private Following following3;

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

    @Test
    public void informWhenAFollowingExists() {
        userRepository.saveFollowing(following1);

        assertThat(userRepository.followingExists(following1)).isTrue();
        assertThat(userRepository.followingExists(following2)).isFalse();
    }

    @Test
    public void returnAllFolloweesForAUser() {
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.saveFollowing(following1);
        userRepository.saveFollowing(following2);
        userRepository.saveFollowing(following3);

        List<User> result = userRepository.followeesFor(FOLLOWER_ID);

        assertThat(result).isEqualTo(asList(user1, user3));
    }

    private void initMocksBehavior() {
        user1 = new User(FOLLOWEE_ID, USERNAME, PASSWORD, ABOUT);
        user2 = new User(FOLLOWEE_ID_2, USERNAME_2, PASSWORD, ABOUT);
        user3 = new User(FOLLOWEE_ID_3, USERNAME_3, PASSWORD, ABOUT);
        following1 = new Following(FOLLOWER_ID, FOLLOWEE_ID);
        following2 = new Following(FOLLOWER_ID_2, FOLLOWEE_ID_2);
        following3 = new Following(FOLLOWER_ID, FOLLOWEE_ID_3);
    }
}
