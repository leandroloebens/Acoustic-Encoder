package com.acoustic.encoder.model;

public record UserConversionInput(
        String text,
        int defaultMidiInstrument,
        int bpm,
        int defaultOctave,
        int defaultVolume
) {}
