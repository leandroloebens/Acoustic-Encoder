package com.acoustic.encoder.model;

public record MusicalInstruction(
        MusicalCommand command,
        int parameter
) {}
