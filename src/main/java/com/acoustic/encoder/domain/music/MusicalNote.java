package com.acoustic.encoder.domain.music;

import com.acoustic.encoder.domain.shared.Octave;

import java.util.Objects;

public record MusicalNote(
        Pitch pitch,
        Octave octave,
        int velocity
) {

    public MusicalNote {
        Objects.requireNonNull(pitch, "Pitch cannot be null!");
        Objects.requireNonNull(octave, "Octave cannot be null!");

    }

    public MusicalNote(int pitch, Octave octave, int velocity) {

        this(Pitch.fromValue(pitch), octave, velocity);
    }

}
