package com.acoustic.encoder.domain.voice;

import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.List;

public record Voice(
        List<MusicalInstruction> musicalInstructions,
        VoiceConfig config
) {
}
