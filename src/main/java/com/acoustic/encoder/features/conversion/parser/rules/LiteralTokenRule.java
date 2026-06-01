package com.acoustic.encoder.features.conversion.parser.rules;

import com.acoustic.encoder.features.conversion.parser.TokenMatch;
import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.Objects;
import java.util.Optional;

public class LiteralTokenRule implements TokenRule {

    private final String token;
    private final MusicalInstruction instruction;

    public LiteralTokenRule(String token, MusicalInstruction instruction) {
        this.token = token;
        this.instruction = instruction;
    }

    @Override
    public Optional<TokenMatch> match(String text, int index) {
        Objects.requireNonNull(text, "Text cannot be null!");
        if (index < 0 || index >= text.length()) {
            throw new IllegalArgumentException("Index is out of bounds!");
        }

        if (text.startsWith(this.token, index)) {
            return Optional.of(new TokenMatch(this.instruction, token.length()));
        }

        return Optional.empty();
    }
}
