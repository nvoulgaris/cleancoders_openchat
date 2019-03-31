package org.openchat.domain.user;

import java.util.UUID;

public class UserIdGenerator {

    public String next() {
        return UUID.randomUUID().toString();
    }
}
