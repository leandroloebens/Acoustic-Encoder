package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.domain.music.MusicalInstruction;

public record TokenMatch(
        MusicalInstruction instruction,
        int consumedChars
) {
}
