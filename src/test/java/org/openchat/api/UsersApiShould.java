package org.openchat.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openchat.domain.user.RegistrationDto;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import org.openchat.domain.user.UsernameAlreadyInUseException;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.openchat.infrastructure.UserTestParser.jsonWith;

public class UsersApiShould {

    private static final String ID = UUID.randomUUID().toString();
    private static final String USERNAME = "a username";
    private static final String PASSWORD = "a password";
    private static final String ABOUT = "about me";

    private UsersApi usersApi;
    private RegistrationDto registrationDto;
    private User user;
    private List<User> users;

    @Mock
    private Request request;
    @Mock
    private Response response;
    @Mock
    private UserService userService;

    @Before
    public void setup() {
        initMocks(this);
        initMocksBehavior();
        usersApi = new UsersApi(userService);
    }

    @Test
    public void createANewUser() {
        usersApi.register(request, response);

        verify(userService).createUserFrom(registrationDto);
    }

    @Test
    public void returnErrorWhenCreatingAUserWithExistingUsername() {
        when(userService.createUserFrom(registrationDto)).thenThrow(new UsernameAlreadyInUseException());

        String result = usersApi.register(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("Username already in use.");
    }

    @Test
    public void returnNewlyCreatedUser() {
        String result = usersApi.register(request, response);

        verify(response).status(201);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonWith(user));
    }

    @Test
    public void returnAllUsers() {
        String result = usersApi.allUsers(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonWith(users));
    }

    private void initMocksBehavior() {
        registrationDto = new RegistrationDto(USERNAME, PASSWORD, ABOUT);
        user = new User(ID, USERNAME, PASSWORD, ABOUT);
        users = asList(user);
        when(request.body()).thenReturn(jsonWith(registrationDto));
        when(userService.createUserFrom(registrationDto)).thenReturn(user);
        when(userService.allUsers()).thenReturn(users);
    }
}
