package com.acoustic.encoder.domain.music;

import java.util.Objects;

public record MusicalNote(
        Pitch pitch,
        int octave,
        int velocity
) {

    public MusicalNote {

        Objects.requireNonNull(pitch, "Pitch cannot be null!");

        if (octave < 1 || octave > 10) {
            throw new IllegalArgumentException("Octave out of range!");
        }
        if (velocity < 0 || velocity > 127) {
            throw new IllegalArgumentException("Note velocity out of range!");
        }
    }

    public MusicalNote(int pitch, int octave, int velocity) {

        this(Pitch.fromValue(pitch), octave, velocity);
    }

}
