package com.acoustic.encoder.features.start.service;

import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
import com.acoustic.encoder.features.start.config.MusicProjectConfigLoader;

public class DefaultStartService implements StartService {

    private static final String NULL_PATH_ERROR_MSG = "Default music project path cannot be null";

    private final String defaultMusicProjectPath;

    public DefaultStartService(String defaultMusicProjectPath) {
        if (defaultMusicProjectPath == null) throw new IllegalArgumentException(NULL_PATH_ERROR_MSG);
        this.defaultMusicProjectPath = defaultMusicProjectPath;
    }

    @Override
    public UserConversionInput getDefaultMusicProject() {
        MusicProjectConfigLoader configLoader = new MusicProjectConfigLoader(defaultMusicProjectPath);

        return configLoader.loadMusicProject();
    }

}
