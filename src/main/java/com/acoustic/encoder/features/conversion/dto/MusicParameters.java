package com.acoustic.encoder.features.conversion.dto;

import com.acoustic.encoder.features.conversion.model.VoiceParameters;

import java.util.List;

public record MusicParameters(int bpm, List<VoiceParameters> voiceParameters) {
}
