package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.shared.model.VoiceConfig;
import com.acoustic.encoder.shared.model.VoiceList;

import java.util.List;

public interface VoiceParser {

    VoiceList parseVoices(String text, List<VoiceConfig> configs);
}
