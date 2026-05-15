package com.acoustic.encoder.features.conversion.controller;

import com.acoustic.encoder.shared.dto.MusicProject;

import java.io.File;
import java.io.IOException;

public interface ConversionController {

    void handleConvertAction(MusicProject input);

    void handleSaveTextAction(String textInput, File file) throws IOException;

    String handleLoadTextAction(File file) throws IOException;

    void handleSaveProjectAction(MusicProject input, File file) throws IOException;

}
