package com.acoustic.encoder.features.conversion.controller;

import com.acoustic.encoder.domain.event.ConversionCompletedEvent;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.conversion.ports.TextRepository;
import com.acoustic.encoder.domain.music.MusicModel;
import com.acoustic.encoder.features.conversion.service.ConversionService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DefaultConversionController implements ConversionController {

    private final ConversionService conversionService;

    private final TextRepository textRepository;

    private final EventBus eventBus;

    public DefaultConversionController(
            ConversionService conversionService, TextRepository textRepository, EventBus eventBus
    ) {

        this.conversionService = Objects.requireNonNull(conversionService, "Conversion service cannot be null!");
        this.textRepository = Objects.requireNonNull(textRepository, "Text repository cannot be null!");
        this.eventBus = Objects.requireNonNull(eventBus, "Event bus cannot be null!");
    }

    @Override
    public void handleConvertAction(MusicProject input) {
        Objects.requireNonNull(input, "UserInput cannot be null!");

        MusicModel music = this.conversionService.textToMusic(
                input.text(),
                input.bpm(),
                input.voiceConfigList()
        );

        this.eventBus.publish(new ConversionCompletedEvent(music));
    }

    @Override
    public void handleSaveTextAction(String textInput, File file) throws IOException {

        this.textRepository.saveText(textInput, file);

    }

    @Override
    public String handleLoadTextAction(File file) throws IOException, IllegalArgumentException {

        return this.textRepository.loadText(file);

    }

    @Override
    public void handleSaveProjectAction(MusicProject project, File file) throws IOException {

        this.textRepository.saveProject(project, file);

    }

    @Override
    public MusicProject handleLoadProjectAction(File file) throws IOException, IllegalArgumentException {

        MusicProject project = this.textRepository.loadProject(file);
        if (project == null) throw new IllegalArgumentException("Loaded project is null!");

        return project;

    }

}
