package com.acoustic.encoder.features.conversion.dto;

import com.acoustic.encoder.shared.model.VoiceConfig;

import java.util.List;

public record UserConversionInput(
        String text,
        List<VoiceConfig> voices
) {}
