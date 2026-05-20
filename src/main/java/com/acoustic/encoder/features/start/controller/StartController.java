package com.acoustic.encoder.features.start.controller;

import com.acoustic.encoder.features.conversion.dto.MusicProject;

import java.io.File;

public interface StartController {

    MusicProject handleOpenProjectAction(File file);

    MusicProject handleNewProjectAction();

}
