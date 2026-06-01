package com.acoustic.encoder.domain.music;

public enum Pitch {

    // Enum values correspond to MusicalInstructions PLAY_NOTE parameter values
    C(0), C_SHARP(1), D(2), D_SHARP(3),
    E(4), F(5), F_SHARP(6), G(7),
    G_SHARP(8), A(9), A_SHARP(10), B(11);

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 11;

    private final int value;

    Pitch(int value) {

        if (value < MIN_VALUE || value > MAX_VALUE) throw new IllegalArgumentException("Invalid pitch value!");
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Pitch fromValue(int value) {

        for (Pitch pitch : Pitch.values()) {
            if (pitch.getValue() == value) return pitch;
        }

        throw new IllegalArgumentException("Invalid pitch value!");
    }

}
