package com.acoustic.encoder.domain.music;

import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Velocity;

import java.util.Objects;

public record MusicalNote(
        Pitch pitch,
        Octave octave,
        Velocity velocity
) {

    public MusicalNote {
        Objects.requireNonNull(pitch, "Pitch cannot be null!");
        Objects.requireNonNull(octave, "Octave cannot be null!");
        Objects.requireNonNull(velocity, "Velocity cannot be null!");

    }

}
