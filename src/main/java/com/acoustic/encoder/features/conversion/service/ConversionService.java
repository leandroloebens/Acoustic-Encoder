package com.acoustic.encoder.features.conversion.service;

import com.acoustic.encoder.shared.model.VoiceConfig;
import com.acoustic.encoder.shared.model.MusicModel;

public interface ConversionService {

    MusicModel textToMusic(String text, VoiceConfig config);

}
