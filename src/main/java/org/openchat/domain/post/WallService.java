package org.openchat.domain.post;

import org.openchat.domain.user.User;
import org.openchat.domain.user.UserRepository;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class WallService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public WallService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<Post> wallFor(String userId) {
        List<User> followees = userRepository.followeesFor(userId);
        List<String> followeesIds = followees.stream()
                                            .map(User::getId)
                                            .collect(toList());
        followeesIds.add(userId);

        return postRepository.postsBy(followeesIds).stream()
                .sorted(comparing(Post::getDateTime).reversed())
                .collect(toList());
    }
}
