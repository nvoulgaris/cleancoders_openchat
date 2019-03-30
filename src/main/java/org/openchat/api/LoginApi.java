package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.CredentialsDto;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserRepository;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.UserParser.jsonWith;

public class LoginApi {

    private static final String DEFAULT_VALUE = "";
    private UserRepository userRepository;

    public LoginApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(Request request, Response response) {
        CredentialsDto credentials = credentialsDtoFrom(request);
        Optional<User> user = userRepository.fetchWith(credentials);
        if (user.isPresent()) {
            response.status(OK_200);
            response.type("application/json");
            return jsonWith(user.get());
        } else {
            response.status(NOT_FOUND_404);
            return "Invalid credentials.";
        }
    }

    private CredentialsDto credentialsDtoFrom(Request request) {
        JsonObject json = Json.parse(request.body()).asObject();
        return new CredentialsDto(
                json.getString("username", DEFAULT_VALUE),
                json.getString("password", DEFAULT_VALUE)
        );
    }
}
