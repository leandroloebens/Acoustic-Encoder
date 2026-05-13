package com.acoustic.encoder.shared.model;

import java.util.List;

public record Voice(
        List<MusicalInstruction> musicalInstructions,
        VoiceConfig config
) {
}
