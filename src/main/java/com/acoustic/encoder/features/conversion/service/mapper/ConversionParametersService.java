package com.acoustic.encoder.features.conversion.service.mapper;

import com.acoustic.encoder.features.conversion.dto.MusicParametersState;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;

public interface ConversionParametersService {

    MusicParametersState unwrapMusicProject(UserConversionInput project);


    UserConversionInput wrapMusicProject(String text, MusicParametersState state);

}
