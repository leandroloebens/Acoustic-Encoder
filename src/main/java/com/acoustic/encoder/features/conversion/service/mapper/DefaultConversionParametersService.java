package com.acoustic.encoder.features.conversion.service.mapper;

import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.domain.voice.VoiceParametersState;
import com.acoustic.encoder.features.conversion.dto.MusicParametersState;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultConversionParametersService implements ConversionParametersService {

    @Override
    public MusicParametersState unwrapMusicProject(UserConversionInput project) {
        MusicParametersState state = new MusicParametersState();
        if (project == null) return state;

        state.setBpm(project.bpm());
        List<VoiceParametersState> voices = project.voiceConfigList().stream()
                .map(VoiceParametersState::new)
                .collect(Collectors.toList());
        state.setAllVoices(voices);
        return state;
    }

    @Override
    public UserConversionInput wrapMusicProject(String text, MusicParametersState state) {
        List<VoiceConfig> configs = state.getAllVoices().stream()
                .map(v -> new VoiceConfig(v.getInstrument(), v.getOctave(), v.getVolume()))
                .collect(Collectors.toList());
        return new UserConversionInput(text, state.getBpm(), configs);
    }
}
