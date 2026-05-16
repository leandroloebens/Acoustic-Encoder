package com.acoustic.encoder.features.conversion.controller;

import com.acoustic.encoder.shared.service.FileService;
import com.acoustic.encoder.shared.model.MusicModel;
import com.acoustic.encoder.shared.dto.MusicProject;
import com.acoustic.encoder.features.conversion.service.ConversionService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DefaultConversionController implements ConversionController {

    private final ConversionService conversionService;

    private final FileService fileService;

    public DefaultConversionController(ConversionService conversionService, FileService fileService) {

        this.conversionService = conversionService;

        this.fileService = fileService;

    }

    @Override
    public void handleConvertAction(MusicProject input) {
        Objects.requireNonNull(input, "Project cannot be null!");

        MusicModel music = this.conversionService.textToMusic(
                input.text(),
                input.bpm(),
                input.voiceConfigList()
        );

        //TESTE----------- TODO tirar
        System.out.println(music);

    }

    @Override
    public void handleSaveTextAction(String textInput, File file) throws IOException {

        this.fileService.saveToTextFile(textInput, file);

    }

    @Override
    public String handleLoadTextAction(File file) throws IOException {

        return this.fileService.loadFromTextFile(file);

    }

    @Override
    public void handleSaveProjectAction(MusicProject project, File file) throws IOException{

        this.fileService.saveProjectFile(project, file);

    }

}
