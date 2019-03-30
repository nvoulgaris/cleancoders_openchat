package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openchat.domain.user.CredentialsDto;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserRepository;
import spark.Request;
import spark.Response;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginApiShould {

    private static final String ID = UUID.randomUUID().toString();
    private static final String USERNAME = "a username";
    private static final String PASSWORD = "a password";
    private static final String ABOUT = "about me";

    private LoginApi loginApi;
    private CredentialsDto credentialsDto;
    private User user;

    @Mock
    private Request request;
    @Mock
    private Response response;
    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        initMocks(this);
        credentialsDto = new CredentialsDto(USERNAME, PASSWORD);
        user = new User(ID, USERNAME, PASSWORD, ABOUT);
        when(request.body()).thenReturn(jsonWith(credentialsDto));
        loginApi = new LoginApi(userRepository);
    }

    @Test
    public void returnErrorWhenCredentialsAreInvalid() {
        when(userRepository.fetchWith(credentialsDto)).thenReturn(Optional.empty());

        String result = loginApi.login(request, response);

        verify(response).status(404);
        assertThat(result).isEqualTo("Invalid credentials.");
    }

    @Test
    public void loginAndReturnUser() {
        when(userRepository.fetchWith(credentialsDto)).thenReturn(Optional.of(user));

        String result = loginApi.login(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonWith(user));
    }

    private String jsonWith(CredentialsDto credentialsDto) {
        return new JsonObject()
                .add("username", credentialsDto.getUsername())
                .add("password", credentialsDto.getPassword())
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
