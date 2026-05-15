package com.acoustic.encoder.features.start.service;

import com.acoustic.encoder.features.start.config.MusicProjectConfigLoader;
import com.acoustic.encoder.shared.dto.MusicProject;

public class DefaultStartService implements StartService {

    private static final String NULL_PATH_ERROR_MSG = "Default music project path cannot be null";

    private final String defaultMusicProjectPath;

    public DefaultStartService(String defaultMusicProjectPath) {
        if (defaultMusicProjectPath == null) throw new IllegalArgumentException(NULL_PATH_ERROR_MSG);
        this.defaultMusicProjectPath = defaultMusicProjectPath;
    }

    @Override
    public MusicProject getDefaultMusicProject() {
        MusicProjectConfigLoader configLoader = new MusicProjectConfigLoader(defaultMusicProjectPath);

        return configLoader.loadMusicProject();
    }

}
