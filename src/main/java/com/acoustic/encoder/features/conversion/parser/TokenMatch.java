package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.Objects;

public record TokenMatch(
        MusicalInstruction instruction,
        int consumedChars
) {

    public TokenMatch {
        Objects.requireNonNull(instruction, "Instruction cannot be null!");
        if (consumedChars < 0) {
            throw new IllegalArgumentException("Consumed chars cannot be negative!");
        }
    }
}
