package com.acoustic.encoder.features.conversion.dto;

import com.acoustic.encoder.domain.voice.VoiceParameters;

import java.util.List;

public record MusicParameters(int bpm, List<VoiceParameters> voiceParameters) {
}
