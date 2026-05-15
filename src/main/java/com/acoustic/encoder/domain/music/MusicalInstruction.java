package com.acoustic.encoder.domain.music;

public record MusicalInstruction(
        MusicalCommand command,
        int parameter
) {}
