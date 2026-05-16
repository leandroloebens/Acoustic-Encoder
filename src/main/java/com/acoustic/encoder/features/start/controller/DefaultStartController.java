package com.acoustic.encoder.features.start.controller;

import com.acoustic.encoder.shared.dto.MusicProject;
import com.acoustic.encoder.features.start.service.StartService;
import com.acoustic.encoder.shared.service.FileService;

import java.io.File;

public class DefaultStartController implements StartController {

    private static final String NULL_START_SERVICE_ARGUMENT_MSG = "Start Service cannot be null";
    private static final String NULL_FILE_SERVICE_ARGUMENT_MSG = "File Service cannot be null";
    private static final String NULL_FILE_ARGUMENT_MSG = "File cannot be null";

    private final StartService startService;

    private final FileService fileService;

    public DefaultStartController(StartService startService, FileService fileService) {
        if (startService == null) throw new IllegalArgumentException(NULL_START_SERVICE_ARGUMENT_MSG);
        this.startService = startService;

        if (fileService == null) throw new IllegalArgumentException(NULL_FILE_SERVICE_ARGUMENT_MSG);
        this.fileService = fileService;
    }

    @Override
    public MusicProject handleOpenProjectAction(File file) {

        if (file == null) throw new IllegalArgumentException(NULL_FILE_ARGUMENT_MSG);
        return fileService.loadProjectFile(file);

    }

    @Override
    public MusicProject handleNewProjectAction() {
        return startService.getDefaultMusicProject();
    }

}
