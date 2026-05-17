package com.acoustic.encoder.domain.shared;

public record Octave(int value) {

    private static final int MIN_OCTAVE = 0;
    private static final int MAX_OCTAVE = 9;

    public Octave {
        if (value < MIN_OCTAVE) {
            throw new IllegalArgumentException("Octave value must be greater than " + MIN_OCTAVE);
        }
        if (value > MAX_OCTAVE) {
            throw new IllegalArgumentException("Octave value must be less than " + MAX_OCTAVE);
        }
    }

}
