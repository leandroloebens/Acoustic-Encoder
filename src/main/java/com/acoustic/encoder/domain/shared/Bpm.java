package com.acoustic.encoder.domain.shared;

public record Bpm(int value) {

    private static final int MIN_BPM = 10;
    private static final int MAX_BPM = 1000;

    public Bpm {
        if (value < MIN_BPM) {
            throw new IllegalArgumentException("BPM value must be greater than " + MIN_BPM);
        }
        if (value > MAX_BPM) {
            throw new IllegalArgumentException("BPM value must be less than " + MAX_BPM);
        }
    }
}
