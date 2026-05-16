package com.acoustic.encoder.features.conversion.parser.config;

import com.acoustic.encoder.features.conversion.parser.rules.TokenRule;
import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.List;

public record ParserConfig(
        List<TokenRule> tokenRules,
        MusicalInstruction defaultInstruction
) {
}
