package org.openchat.domain.post;

import org.openchat.infrastructure.Clock;

import java.util.List;

public class PostService {

    private PostRepository postRepository;
    private LanguageValidator languageValidator;
    private Clock clock;
    private PostIdGenerator postIdGenerator;

    public PostService(PostRepository postRepository, LanguageValidator languageValidator, Clock clock, PostIdGenerator postIdGenerator) {
        this.postRepository = postRepository;
        this.languageValidator = languageValidator;
        this.clock = clock;
        this.postIdGenerator = postIdGenerator;
    }

    public Post createPost(String userId, String text) {
        languageValidator.validate(text);
        Post post = new Post(postIdGenerator.next(), userId, text, clock.now());
        postRepository.save(post);
        return post;
    }

    public List<Post> postsBy(String userId) {
        throw new UnsupportedOperationException();
    }
}
