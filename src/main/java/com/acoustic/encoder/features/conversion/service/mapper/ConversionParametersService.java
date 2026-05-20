package com.acoustic.encoder.features.conversion.service.mapper;

import com.acoustic.encoder.features.conversion.dto.MusicParametersState;
import com.acoustic.encoder.features.conversion.dto.MusicProject;

public interface ConversionParametersService {

    MusicParametersState unwrapMusicProject(MusicProject project);


    MusicProject wrapMusicProject(String text, MusicParametersState state);

}
