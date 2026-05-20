package com.acoustic.encoder.features.conversion.dto;


import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.voice.VoiceConfig;

import java.util.List;

public record MusicProject(
        String text,
        Bpm bpm,
        List<VoiceConfig> voiceConfigList
) {}
