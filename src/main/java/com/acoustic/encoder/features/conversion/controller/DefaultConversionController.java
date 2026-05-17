package com.acoustic.encoder.features.conversion.controller;

import com.acoustic.encoder.features.conversion.ports.TextRepository;
import com.acoustic.encoder.domain.music.MusicModel;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
import com.acoustic.encoder.features.conversion.service.ConversionService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DefaultConversionController implements ConversionController {

    private final ConversionService conversionService;

    private final TextRepository textRepository;

    public DefaultConversionController(ConversionService conversionService, TextRepository textRepository) {

        this.conversionService = conversionService;

        this.textRepository = textRepository;

    }

    @Override
    public void handleConvertAction(UserConversionInput input) {
        Objects.requireNonNull(input, "UserInput cannot be null!");

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

        this.textRepository.saveText(textInput, file);

    }

    @Override
    public String handleLoadTextAction(File file) throws IOException {

        return this.textRepository.loadText(file);

    }

    @Override
    public void handleSaveProjectAction(UserConversionInput project, File file) throws IOException{

        this.textRepository.saveProject(project, file);

    }

}
