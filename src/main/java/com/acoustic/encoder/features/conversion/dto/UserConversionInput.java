package com.acoustic.encoder.features.conversion.dto;

import com.acoustic.encoder.domain.voice.VoiceConfig;

import java.util.List;

public record UserConversionInput(
        String text,
        int bpm,
        List<VoiceConfig> voiceConfigList
) {}
