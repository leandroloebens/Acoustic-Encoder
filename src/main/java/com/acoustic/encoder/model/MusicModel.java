package com.acoustic.encoder.model;

import java.util.List;

public record MusicModel(
        List<MusicalInstruction> musicalInstructions,
        int defaultMidiInstrument,
        int bpm,
        int defaultOctave,
        int defaultVolume
) { }
