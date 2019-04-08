package org.openchat.domain.post;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PostRepository {

    private List<Post> posts;

    public PostRepository() {
        this.posts = new ArrayList<>();
    }

    public void save(Post post) {
        posts.add(post);
    }

    public List<Post> postsBy(String userId) {
        return posts.stream()
                .filter(post -> post.getUserId().equals(userId))
                .collect(toList());
    }

    public List<Post> postsBy(List<String> userIds) {
        return posts.stream()
                .filter(post -> userIds.contains(post.getUserId()))
                .collect(toList());
    }
}
