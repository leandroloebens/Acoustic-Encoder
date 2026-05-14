package com.acoustic.encoder.features.conversion.parser.rules;

import com.acoustic.encoder.features.conversion.parser.TokenMatch;
import com.acoustic.encoder.shared.model.MusicalCommand;
import com.acoustic.encoder.shared.model.MusicalInstruction;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelayTokenRule implements TokenRule {

    private static final Pattern PATTERN = Pattern.compile("\\[(\\d+)]");

    @Override
    public Optional<TokenMatch> match(String text, int index) {
        Matcher matcher = PATTERN.matcher(text.substring(index));

        if (!matcher.lookingAt()) {
            return Optional.empty();
        }

        MusicalInstruction instruction = new MusicalInstruction(
                MusicalCommand.DELAY_BEATS,
                Integer.parseInt(matcher.group(1))
        );

        return Optional.of(new TokenMatch(instruction, matcher.end()));
    }

}
