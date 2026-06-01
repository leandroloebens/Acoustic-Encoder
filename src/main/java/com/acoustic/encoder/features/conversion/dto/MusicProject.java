package com.acoustic.encoder.features.conversion.dto;


import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.voice.VoiceConfig;

import java.util.List;
import java.util.Objects;

public record MusicProject(
        String text,
        Bpm bpm,
        List<VoiceConfig> voiceConfigList
) {

    public MusicProject(String text, MusicParametersState musicParametersState) {
        Objects.requireNonNull(text, "Text cannot be null!");
        Objects.requireNonNull(musicParametersState, "MusicParametersState cannot be null!");

        this(
            text,
            musicParametersState.getBpm(),
            musicParametersState.getAllVoices()
                    .stream()
                    .map(v -> new VoiceConfig(v.getInstrument(), v.getOctave(), v.getVolume()))
                    .toList()
        );
    }
}
