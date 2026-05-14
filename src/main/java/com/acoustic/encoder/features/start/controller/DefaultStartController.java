package com.acoustic.encoder.features.start.controller;

import com.acoustic.encoder.shared.service.FileService;

public class DefaultStartController implements StartController {

    private final FileService fileService;

    public DefaultStartController(FileService fileService) {
        if (fileService == null) throw new IllegalArgumentException();
        this.fileService = fileService;
    }

}
