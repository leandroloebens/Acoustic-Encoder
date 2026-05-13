package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.shared.model.MusicalInstruction;

public record TokenMatch(
        MusicalInstruction instruction,
        int consumedChars
) {
}
