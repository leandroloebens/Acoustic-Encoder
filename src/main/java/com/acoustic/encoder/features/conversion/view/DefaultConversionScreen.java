package com.acoustic.encoder.features.conversion.view;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
import com.acoustic.encoder.features.conversion.view.swing.components.ConversionScreenComponentsWrapper;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.ConversionScreenComponentsFactory;
import com.acoustic.encoder.shared.model.MusicConfig;

import javax.swing.*;
import java.awt.*;

public class DefaultConversionScreen implements ConversionScreen {

    private final static String WINDOW_TITLE = "Conversor: Texto para Som";
    private final static int WINDOW_HEIGHT = 400;
    private final static int WINDOW_WIDTH = 500;

    private final ConversionController conversionController;

    private final ConversionScreenComponentsAssembler assembler;

    private int defaultVolume = 64;
    private int defaultOctave = 5;
    private int defaultInstrument = 0;
    private int defaultBpm = 120;

    public DefaultConversionScreen(ConversionController conversionController, ConversionScreenComponentsAssembler assembler) {

        if (conversionController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.conversionController = conversionController;

        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        setConversionAction();

        this.assembler.setDefaultParameters(new MusicConfig(defaultInstrument, defaultBpm, defaultOctave, defaultVolume));
    }

    private void setConversionAction() {
        Runnable action = () -> {
            accessDefaultParameters();
            this.conversionController.handleConvertAction(
                new UserConversionInput(
                        this.assembler.getInputText(),
                        defaultInstrument,
                        defaultBpm,
                        defaultOctave,
                        defaultVolume
                )
        );};

        this.assembler.setConversionAction(action);
    }

    private void accessDefaultParameters() {
        MusicConfig parameters = this.assembler.getDefaultParameters();

        defaultInstrument = parameters.defaultMidiInstrument();
        defaultBpm = parameters.bpm();
        defaultOctave = parameters.defaultOctave();
        defaultVolume = parameters.defaultVolume();
    }

    public void startFrame() {

        this.assembler.assemble(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.assembler.showFrame();

    }

    public void closeFrame() {

        this.assembler.hideFrame();

    }
}

