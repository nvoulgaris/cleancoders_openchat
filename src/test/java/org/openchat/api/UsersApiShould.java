package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openchat.domain.user.RegistrationDto;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import org.openchat.domain.user.UsernameAlreadyInUseException;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UsersApiShould {

    private static final String ID = UUID.randomUUID().toString();
    private static final String USERNAME = "a username";
    private static final String PASSWORD = "a password";
    private static final String ABOUT = "about me";

    private UsersApi usersApi;
    private RegistrationDto registrationDto;
    private User user;

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

        verify(userService).createFrom(registrationDto);
    }

    @Test
    public void returnErrorWhenCreatingAUserWithExistingUsername() {
        when(userService.createFrom(registrationDto)).thenThrow(new UsernameAlreadyInUseException());

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

    private void initMocksBehavior() {
        registrationDto = new RegistrationDto(USERNAME, PASSWORD, ABOUT);
        user = new User(ID, USERNAME, PASSWORD, ABOUT);
        when(request.body()).thenReturn(jsonWith(registrationDto));
        when(userService.createFrom(registrationDto)).thenReturn(user);
    }

    private String jsonWith(RegistrationDto registrationDto) {
        return new JsonObject()
                .add("username", registrationDto.getUsername())
                .add("password", registrationDto.getPassword())
                .add("about", registrationDto.getAbout())
                .toString();
    }

    private String jsonWith(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout())
                .toString();
    }
}
