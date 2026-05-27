package com.acoustic.encoder.features.conversion.controller;

import com.acoustic.encoder.features.conversion.dto.MusicProject;

import java.io.File;
import java.io.IOException;

public interface ConversionController {

    void handleConvertAction(MusicProject input);

    void handleSaveTextAction(String textInput, File file) throws IOException;

    String handleLoadTextAction(File file) throws IOException, IllegalArgumentException;

    void handleSaveProjectAction(MusicProject input, File file) throws IOException;

    MusicProject handleLoadProjectAction(File file) throws IOException, IllegalArgumentException;

}
