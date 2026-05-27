package com.acoustic.encoder.features.start.controller;

import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.conversion.ports.TextRepository;
import com.acoustic.encoder.features.start.service.StartService;

import java.io.File;
import java.io.IOException;

public class DefaultStartController implements StartController {

    private static final String NULL_START_SERVICE_ARGUMENT_MSG = "Start Service cannot be null";
    private static final String NULL_FILE_SERVICE_ARGUMENT_MSG = "File Service cannot be null";
    private static final String NULL_FILE_ARGUMENT_MSG = "File cannot be null";

    private final StartService startService;

    private final TextRepository textRepository;

    public DefaultStartController(StartService startService, TextRepository textRepository) {
        if (startService == null) throw new IllegalArgumentException(NULL_START_SERVICE_ARGUMENT_MSG);
        this.startService = startService;

        if (textRepository == null) throw new IllegalArgumentException(NULL_FILE_SERVICE_ARGUMENT_MSG);
        this.textRepository = textRepository;
    }

    @Override
    public MusicProject handleOpenProjectAction(File file) throws IOException, IllegalArgumentException {
        if (file == null) throw new IllegalArgumentException(NULL_FILE_ARGUMENT_MSG);
        return textRepository.loadProject(file);
    }

    @Override
    public MusicProject handleNewProjectAction() {
        return startService.getDefaultMusicProject();
    }

}
