package com.acoustic.encoder.shared.dto;

import com.acoustic.encoder.shared.model.VoiceConfig;

import java.util.List;

public record MusicProject(
        String text,
        int bpm,
        List<VoiceConfig> voiceConfigList
) {}
