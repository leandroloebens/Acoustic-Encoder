package com.acoustic.encoder.features.conversion.service;

import com.acoustic.encoder.domain.music.MusicModel;
import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.domain.voice.VoiceList;
import com.acoustic.encoder.domain.event.ConversionCompletedEvent;
import com.acoustic.encoder.features.conversion.parser.VoiceParser;
import com.acoustic.encoder.domain.event.EventBus;

import java.util.List;
import java.util.Objects;

public class DefaultConversionService implements ConversionService {

    private final VoiceParser voiceParser;

    private final EventBus eventBus;

    public DefaultConversionService(VoiceParser voiceParser, EventBus eventBus) {

        this.voiceParser = Objects.requireNonNull(voiceParser, "VoiceParser cannot be null!");
        this.eventBus = Objects.requireNonNull(eventBus, "EventBus cannot be null!");
    }

    @Override
    public MusicModel textToMusic(String text, int bpm, List<VoiceConfig> configs) {

        VoiceList voiceList = voiceParser.parseVoices(text, configs);

        MusicModel music = new MusicModel(voiceList, bpm);

        this.eventBus.publish(new ConversionCompletedEvent(music));

        return music;
    }
}
