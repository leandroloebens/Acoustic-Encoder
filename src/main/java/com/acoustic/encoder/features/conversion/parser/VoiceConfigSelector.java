package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.shared.model.VoiceConfig;

import java.util.List;

public interface VoiceConfigSelector {

    VoiceConfig selectConfig(List<VoiceConfig> configs, int voiceIndex);
}
