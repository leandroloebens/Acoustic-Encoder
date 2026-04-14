package com.acoustic.encoder.features.player.model;

public enum Pitch {

    // Enum values correspond to MusicalInstructions parameter values
    C(0), C_SHARP(1), D(2), D_SHARP(3),
    E(4), F(5), F_SHARP(6), G(7),
    G_SHARP(8), A(9), A_SHARP(10), B(11);

    private final int value;

    Pitch(int value) {

        if (value < 0 || value > 11) throw new IllegalArgumentException("Invalid pitch value!");
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
