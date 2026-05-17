package com.acoustic.encoder.features.start.controller;

import com.acoustic.encoder.features.conversion.dto.UserConversionInput;

import java.io.File;

public interface StartController {

    UserConversionInput handleOpenProjectAction(File file);

    UserConversionInput handleNewProjectAction();

}
