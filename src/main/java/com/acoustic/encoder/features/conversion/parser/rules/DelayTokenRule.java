package com.acoustic.encoder.features.conversion.parser.rules;

import com.acoustic.encoder.features.conversion.parser.TokenMatch;
import com.acoustic.encoder.domain.music.MusicalCommand;
import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelayTokenRule implements TokenRule {

    private static final Pattern PATTERN = Pattern.compile("\\[(\\d+)]");

    @Override
    public Optional<TokenMatch> match(String text, int index) {
        Objects.requireNonNull(text, "Text cannot be null!");
        if (index < 0 || index >= text.length()) {
            throw new IllegalArgumentException("Index is out of bounds!");
        }

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
