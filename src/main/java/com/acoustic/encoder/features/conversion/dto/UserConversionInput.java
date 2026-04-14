package com.acoustic.encoder.features.conversion.dto;

public record UserConversionInput(
        String text,
        int defaultMidiInstrument,
        int bpm,
        int defaultOctave,
        int defaultVolume
) {}
