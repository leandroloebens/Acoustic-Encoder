package com.acoustic.encoder.features.conversion.parser.rules;

import com.acoustic.encoder.features.conversion.parser.TokenMatch;

import java.util.Optional;

public interface TokenRule {

    Optional<TokenMatch> match(String text, int index);

}
