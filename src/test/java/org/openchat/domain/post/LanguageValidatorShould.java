package org.openchat.domain.post;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class LanguageValidatorShould {

    private LanguageValidator languageValidator = new LanguageValidator();

    @Parameters({
            "elephant",
            "orange",
            "ice cream",
            "ELEPHANT",
            "ORANGE",
            "ICE CREAM",
            "ElEPHant",
            "OraNGe",
            "IcE CReAm",
            "a big elephant",
            "an orange circle",
            "I want an ice cream"
    })
    @Test(expected = InappropriateLanguageException.class)
    public void throwInappropriateLanguageExceptionWhenTextIsInappropriate(String text) {
        languageValidator.validate(text);
    }

    @Test
    public void doNothingWhenTextIsAppropriate() {
        languageValidator.validate("This is a valid text");
    }
}
