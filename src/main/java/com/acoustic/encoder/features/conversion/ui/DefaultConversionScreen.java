package com.acoustic.encoder.features.conversion.ui;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.event.ConversionScreenCloseRequestEvent;

public class DefaultConversionScreen implements ConversionScreen {

    private final ConversionController conversionController;

    private final ConversionViewManager manager;

    private final EventBus eventBus;

    public DefaultConversionScreen(
            ConversionController conversionController,
            ConversionViewManager manager,
            EventBus eventBus)
    {

        if (conversionController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.conversionController = conversionController;

        if (manager == null) throw new IllegalArgumentException("Manager cannot be null!");
        this.manager = manager;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

        setEvents();

        initialize();

    }

    @Override
    public void initialize() {
        this.manager.assemble(this.conversionController);
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

    private void setEvents() {
        eventBus.subscribe(AppShutdownEvent.class, event -> closeWindow());
        eventBus.subscribe(
                ConversionScreenCloseRequestEvent.class, event -> closeWindow());
    }

}

