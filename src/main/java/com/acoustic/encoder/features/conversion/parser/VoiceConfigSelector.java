package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.domain.voice.VoiceConfig;

import java.util.List;

public interface VoiceConfigSelector {

    VoiceConfig selectConfig(List<VoiceConfig> configs, int voiceIndex);
}
