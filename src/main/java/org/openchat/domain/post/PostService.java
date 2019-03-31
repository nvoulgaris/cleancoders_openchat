package org.openchat.domain.post;

import org.openchat.infrastructure.Clock;

public class PostService {
    private final PostRepository postRepository;
    private LanguageValidator languageValidator;
    private final Clock clock;
    private final PostIdGenerator postIdGenerator;

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
}
