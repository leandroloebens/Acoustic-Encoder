package com.acoustic.encoder.domain.music;

import java.util.Objects;

public record MusicalInstruction(
        MusicalCommand command,
        int parameter
) {

    public MusicalInstruction {
        Objects.requireNonNull(command, "Command cannot be null!");

    }
}
