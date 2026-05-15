package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.domain.voice.VoiceConfig;

import java.util.List;
import java.util.Objects;

public class RoundRobinVoiceConfigSelector implements VoiceConfigSelector {

    @Override
    public VoiceConfig selectConfig(List<VoiceConfig> configs, int voiceIndex) {
        Objects.requireNonNull(configs, "VoiceConfigs cannot be null!");

        if (configs.isEmpty()) {
            throw new IllegalArgumentException("VoiceConfigs cannot be null or empty!");
        }
        if (voiceIndex < 0) {
            throw new IllegalArgumentException("Voice index cannot be negative!");
        }

        return configs.get(voiceIndex % configs.size());
    }
}
