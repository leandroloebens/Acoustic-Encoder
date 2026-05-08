package com.acoustic.encoder.features.conversion.view;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.shared.model.MusicConfig;

public class DefaultConversionScreen implements ConversionScreen {

    private final static int INITIAL_VOLUME = 64;
    private final static int INITIAL_INSTRUMENT = 0;
    private final static int INITIAL_BPM = 120;
    private final static int INITIAL_OCTAVE = 5;

    private final ConversionController conversionController;

    private final ConversionViewManager manager;

    public DefaultConversionScreen(ConversionController conversionController, ConversionViewManager manager) {

        if (conversionController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.conversionController = conversionController;

        if (manager == null) throw new IllegalArgumentException("Manager cannot be null!");
        this.manager = manager;

        this.manager.setInitialDefaultParameters(
                new MusicConfig(
                        INITIAL_INSTRUMENT,
                        INITIAL_BPM,
                        INITIAL_OCTAVE,
                        INITIAL_VOLUME
                )
        );

        startWindow();

    }

    @Override
    public void showWindow() {

        this.manager.showFrame();

    }

    @Override
    public void hideWindow() {

        this.manager.hideFrame();

    }

    @Override
    public void closeWindow() {

        this.manager.disposeFrame();

    }

    private void startWindow() {

        this.manager.startFrame(this.conversionController);

    }
}

