package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

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

        userService.createFrom(registrationDto);
    }

    @Test
    public void createANewUser() {
        userService.createFrom(registrationDto);

        verify(userRepository).save(user);
    }

    @Test
    public void returnTheNewlyCreatedUser() {
        User result = userService.createFrom(registrationDto);

        assertThat(result).isEqualTo(user);
    }

    private void initMocksBehavior() {
        registrationDto = new RegistrationDto(USERNAME, PASSWORD, ABOUT);
        user = new User(ID, USERNAME, PASSWORD, ABOUT);
        when(userIdGenerator.next()).thenReturn(ID);
    }
}
