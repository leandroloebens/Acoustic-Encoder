package com.acoustic.encoder.domain.voice;

import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.List;
import java.util.Objects;

public record Voice(
        List<MusicalInstruction> musicalInstructions,
        VoiceConfig config
) {

    public Voice {
        Objects.requireNonNull(musicalInstructions, "Musical instructions cannot be null!");
        Objects.requireNonNull(config, "VoiceConfig cannot be null!");
    }
}
