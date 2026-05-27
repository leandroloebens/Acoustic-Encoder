package com.acoustic.encoder.features.start.controller;

import com.acoustic.encoder.features.conversion.dto.MusicProject;

import java.io.File;
import java.io.IOException;

public interface StartController {

    MusicProject handleOpenProjectAction(File file) throws IOException, IllegalArgumentException;

    MusicProject handleNewProjectAction();

}
