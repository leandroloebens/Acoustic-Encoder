package com.acoustic.encoder.features.conversion.service;

import com.acoustic.encoder.shared.model.VoiceConfig;
import com.acoustic.encoder.shared.model.MusicModel;

import java.util.List;

public interface ConversionService {

    MusicModel textToMusic(String text, int bpm, List<VoiceConfig> configs);

}
