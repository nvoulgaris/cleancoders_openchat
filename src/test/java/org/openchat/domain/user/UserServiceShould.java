package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceShould {

    private static final String ID = UUID.randomUUID().toString();
    private static final String USERNAME = "a username";
    private static final String PASSWORD = "a password";
    private static final String ABOUT = "about me";

    private UserService userService;
    private RegistrationDto registrationDto;
    private User user;
    private List<User> users;
    private Following following;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserIdGenerator userIdGenerator;

    @Before
    public void setup() {
        initMocks(this);
        initMocksBehavior();
        userService = new UserService(userRepository, userIdGenerator);
    }

    @Test(expected = UsernameAlreadyInUseException.class)
    public void throwExceptionWhenUsernameIsAlreadyInUse() {
        when(userRepository.alreadyInUse(USERNAME)).thenReturn(true);

        userService.createUserFrom(registrationDto);
    }

    @Test
    public void createANewUser() {
        userService.createUserFrom(registrationDto);

        verify(userRepository).save(user);
    }

    @Test
    public void returnTheNewlyCreatedUser() {
        User result = userService.createUserFrom(registrationDto);

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void returnAllUsers() {
        when(userRepository.all()).thenReturn(users);

        List<User> result = userService.allUsers();

        assertThat(result).isEqualTo(users);
    }

    @Test(expected = FollowingAlreadyExistsException.class)
    public void throwFollowingAlreadyExistsExceptionWhenFollowingExists() {
        when(userRepository.followingExists(following)).thenReturn(true);

        userService.createFollowing(following);
    }

    @Test
    public void saveANewFollowing() {
        userService.createFollowing(following);

        verify(userRepository).saveFollowing(following);
    }

    @Test
    public void getFolloweesForUser() {
        userService.followeesFor(ID);

        verify(userRepository).followeesFor(ID);
    }

    private void initMocksBehavior() {
        registrationDto = new RegistrationDto(USERNAME, PASSWORD, ABOUT);
        user = new User(ID, USERNAME, PASSWORD, ABOUT);
        users = asList(user);
        when(userIdGenerator.next()).thenReturn(ID);
    }
}
