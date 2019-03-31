package org.openchat;

import org.openchat.api.LoginApi;
import org.openchat.api.PostsApi;
import org.openchat.api.UsersApi;
import org.openchat.domain.post.LanguageValidator;
import org.openchat.domain.post.PostIdGenerator;
import org.openchat.domain.post.PostRepository;
import org.openchat.domain.post.PostService;
import org.openchat.domain.user.UserIdGenerator;
import org.openchat.domain.user.UserRepository;
import org.openchat.domain.user.UserService;
import org.openchat.infrastructure.Clock;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;

public class Routes {

    private UserRepository userRepository;
    private UserIdGenerator userIdGenerator;
    private UserService userService;
    private PostRepository postRepository;
    private LanguageValidator languageValidator;
    private Clock clock;
    private PostIdGenerator postIdGenerator;
    private PostService postService;
    private UsersApi usersApi;
    private LoginApi loginApi;
    private PostsApi postsApi;

    public void create() {
        createApis();
        swaggerRoutes();
        openchatRoutes();
    }

    private void createApis() {
        userRepository = new UserRepository();
        userIdGenerator = new UserIdGenerator();
        userService = new UserService(userRepository, userIdGenerator);
        postRepository = new PostRepository();
        languageValidator = new LanguageValidator();
        clock = new Clock();
        postIdGenerator = new PostIdGenerator();
        postService = new PostService(postRepository, languageValidator, clock, postIdGenerator);
        usersApi = new UsersApi(userService);
        loginApi = new LoginApi(userRepository);
        postsApi = new PostsApi(postService);
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
        post("users/:userId/timeline", (req, res) -> postsApi.create(req, res));
        get("users/:userId/timeline", (req, res) -> postsApi.postsByUser(req, res));
        get("users", (req, res) -> usersApi.allUsers(req, res));
    }
}
