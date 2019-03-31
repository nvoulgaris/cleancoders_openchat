package org.openchat.domain.post;

import java.util.List;

import static java.util.Arrays.asList;

public class LanguageValidator {

    private static final List<String> INAPPROPRIATE_WORDS = asList("elephant", "orange", "ice cream");

    public void validate(String text) {
        if (containsInappropriateWords(text))
            throw new InappropriateLanguageException();
    }

    private boolean containsInappropriateWords(String text) {
        return INAPPROPRIATE_WORDS.stream()
                                .anyMatch(word -> text.toLowerCase().contains(word));
    }
}
