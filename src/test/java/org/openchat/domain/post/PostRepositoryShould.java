package org.openchat.domain.post;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class PostRepositoryShould {

    private static final String ALICE_USER_ID = UUID.randomUUID().toString();
    private static final String BOB_USER_ID = UUID.randomUUID().toString();
    private static final String POST_ID_1 = UUID.randomUUID().toString();
    private static final String POST_ID_2 = UUID.randomUUID().toString();
    private static final String POST_ID_3 = UUID.randomUUID().toString();
    private static final String TEXT = "Hello";
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2019, 3, 30, 22, 30, 0);

    private PostRepository postRepository;
    private Post postAlice1;
    private Post postAlice2;
    private Post postBob;

    @Before
    public void setup() {
        initMocksBehavior();
        postRepository = new PostRepository();
    }

    @Test
    public void returnPostsByUser() {
        postRepository.save(postAlice1);
        postRepository.save(postAlice2);
        postRepository.save(postBob);

        List<Post> result = postRepository.postsBy(ALICE_USER_ID);

        assertThat(result).isEqualTo(asList(postAlice1, postAlice2));
    }

    @Test
    public void returnPostByUsersIds() {
        postRepository.save(postAlice1);
        postRepository.save(postAlice2);
        postRepository.save(postBob);

        List<Post> alicesPosts = postRepository.postsBy(asList(ALICE_USER_ID));
        List<Post> alicesAndBobsPosts = postRepository.postsBy(asList(ALICE_USER_ID, BOB_USER_ID));

        assertThat(alicesPosts).isEqualTo(asList(postAlice1, postAlice2));
        assertThat(alicesAndBobsPosts).isEqualTo(asList(postAlice1, postAlice2, postBob));
    }

    private void initMocksBehavior() {
        postAlice1 = new Post(POST_ID_1, ALICE_USER_ID, TEXT, DATE_TIME);
        postAlice2 = new Post(POST_ID_2, ALICE_USER_ID, TEXT, DATE_TIME);
        postBob = new Post(POST_ID_3, BOB_USER_ID, TEXT, DATE_TIME);
    }
}
