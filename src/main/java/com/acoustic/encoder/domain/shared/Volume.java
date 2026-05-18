package com.acoustic.encoder.domain.shared;

public record Volume(int value) {

    public static final int MIN_VOLUME = 0;
    public static final int MAX_VOLUME = 127;

    public Volume {
        if (value < MIN_VOLUME) {
            throw new IllegalArgumentException("Volume value must be greater than " + MIN_VOLUME);
        }
        if (value > MAX_VOLUME) {
            throw new IllegalArgumentException("Volume value must be less than " + MAX_VOLUME);
        }
    }

}

