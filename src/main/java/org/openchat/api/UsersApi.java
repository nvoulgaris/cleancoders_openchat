package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.RegistrationDto;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import org.openchat.domain.user.UsernameAlreadyInUseException;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.UserParser.jsonWith;

public class UsersApi {

    private static final String DEFAULT_VALUE = "";

    private UserService userService;

    public UsersApi(UserService userService) {
        this.userService = userService;
    }

    public String register(Request request, Response response) {
        RegistrationDto registrationDto = registrationDtoFrom(request);
        try {
            User user = userService.createUserFrom(registrationDto);
            return createdResponse(response, user);
        } catch (UsernameAlreadyInUseException e) {
            return badRequestResponse(response);
        }
    }

    public String allUsers(Request request, Response response) {
        List<User> users = userService.allUsers();

        response.status(OK_200);
        response.type("application/json");
        return jsonWith(users);
    }

    private String createdResponse(Response response, User user) {
        response.status(CREATED_201);
        response.type("application/json");
        return jsonWith(user);
    }

    private String badRequestResponse(Response response) {
        response.status(BAD_REQUEST_400);
        return "Username already in use.";
    }

    private RegistrationDto registrationDtoFrom(Request request) {
        JsonObject json = Json.parse(request.body()).asObject();
        return new RegistrationDto(
                json.getString("username", DEFAULT_VALUE),
                json.getString("password", DEFAULT_VALUE),
                json.getString("about", DEFAULT_VALUE)
        );
    }
}
