package com.acoustic.encoder.features.conversion.parser.config;

import com.acoustic.encoder.features.conversion.parser.rules.TokenRule;
import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.List;
import java.util.Objects;

public record ParserConfig(
        List<TokenRule> tokenRules,
        MusicalInstruction defaultInstruction
) {

    public ParserConfig {
        Objects.requireNonNull(tokenRules, "Token rules cannot be null!");
        Objects.requireNonNull(defaultInstruction, "Default instruction cannot be null!");
    }
}
