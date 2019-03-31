package org.openchat.domain.post;

import java.util.UUID;

public class PostIdGenerator {

    public String next() {
        return UUID.randomUUID().toString();
    }
}
