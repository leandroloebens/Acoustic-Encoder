package com.acoustic.encoder.features.conversion.service;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.domain.music.MusicModel;

import java.util.List;

public interface ConversionService {

    MusicModel textToMusic(String text, Bpm bpm, List<VoiceConfig> configs);

}
