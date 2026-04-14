package com.acoustic.encoder.shared.model;

public record MusicalInstruction(
        MusicalCommand command,
        int parameter
) {}
