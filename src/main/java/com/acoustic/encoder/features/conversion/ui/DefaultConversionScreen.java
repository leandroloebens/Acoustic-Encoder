package com.acoustic.encoder.features.conversion.ui;

import com.acoustic.encoder.features.conversion.controller.ConversionController;

public class DefaultConversionScreen implements ConversionScreen {

    private final ConversionController conversionController;

    private final ConversionViewManager manager;

    public DefaultConversionScreen(ConversionController conversionController, ConversionViewManager manager) {

        if (conversionController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.conversionController = conversionController;

        if (manager == null) throw new IllegalArgumentException("Manager cannot be null!");
        this.manager = manager;

        startWindow();

    }

    @Override
    public void showWindow() {
        this.manager.show();
    }

    @Override
    public void hideWindow() {
        this.manager.hide();
    }

    @Override
    public void closeWindow() {
        this.manager.dispose();
    }

    private void startWindow() {
        this.manager.assemble(this.conversionController);
    }
}

