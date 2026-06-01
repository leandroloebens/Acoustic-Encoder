package com.acoustic.encoder.domain.shared;

public record Velocity(int value) {

    public static final int MIN_VELOCITY = 0;
    public static final int MAX_VELOCITY = 127;

    public Velocity {
        if (value < MIN_VELOCITY) {
            throw new IllegalArgumentException("Velocity value must be greater than " + MIN_VELOCITY);
        }
        if (value > MAX_VELOCITY) {
            throw new IllegalArgumentException("Velocity value must be less than " + MAX_VELOCITY);
        }
    }
}
