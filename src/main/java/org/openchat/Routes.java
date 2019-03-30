package org.openchat;

import org.openchat.api.LoginApi;
import org.openchat.api.UsersApi;
import org.openchat.domain.user.UserIdGenerator;
import org.openchat.domain.user.UserRepository;
import org.openchat.domain.user.UserService;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;

public class Routes {

    private UserRepository userRepository;
    private UserIdGenerator userIdGenerator;
    private UserService userService;
    private UsersApi usersApi;
    private LoginApi loginApi;

    public void create() {
        createApis();
        swaggerRoutes();
        openchatRoutes();
    }

    private void createApis() {
        userRepository = new UserRepository();
        userIdGenerator = new UserIdGenerator();
        userService = new UserService(userRepository, userIdGenerator);
        usersApi = new UsersApi(userService);
        loginApi = new LoginApi(userRepository);
    }

    private void swaggerRoutes() {
        options("users", (req, res) -> "OK");
        options("login", (req, res) -> "OK");
        options("users/:userId/timeline", (req, res) -> "OK");
        options("followings", (req, res) -> "OK");
        options("followings/:userId/followees", (req, res) -> "OK");
        options("users/:userId/wall", (req, res) -> "OK");
    }

    private void openchatRoutes() {
        get("status", (req, res) -> "OpenChat: OK!");
        post("users", (req, res) -> usersApi.register(req, res));
        post("login", (req, res) -> loginApi.login(req, res));
    }
}
