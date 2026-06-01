package com.acoustic.encoder.features.conversion.service;

import com.acoustic.encoder.domain.music.MusicModel;
import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.domain.voice.VoiceList;
import com.acoustic.encoder.domain.event.ConversionCompletedEvent;
import com.acoustic.encoder.features.conversion.parser.VoiceParser;
import com.acoustic.encoder.domain.event.EventBus;

import java.util.List;
import java.util.Objects;

public class DefaultConversionService implements ConversionService {

    private final VoiceParser voiceParser;

    public DefaultConversionService(VoiceParser voiceParser, EventBus eventBus) {

        this.voiceParser = Objects.requireNonNull(voiceParser, "VoiceParser cannot be null!");
    }

    @Override
    public MusicModel textToMusic(String text, Bpm bpm, List<VoiceConfig> configs) {
        Objects.requireNonNull(text, "Text cannot be null!");
        Objects.requireNonNull(bpm, "Bpm cannot be null!");
        Objects.requireNonNull(configs, "VoiceConfigs cannot be null!");

        VoiceList voiceList = voiceParser.parseVoices(text, configs);

        return new MusicModel(voiceList, bpm);
    }
}
