package com.acoustic.encoder.features.conversion.config;

import com.acoustic.encoder.features.conversion.parser.rules.TokenRule;
import com.acoustic.encoder.shared.model.MusicalInstruction;

import java.util.List;

public record ParserConfig(
        List<TokenRule> tokenRules,
        MusicalInstruction defaultInstruction
) {
}
