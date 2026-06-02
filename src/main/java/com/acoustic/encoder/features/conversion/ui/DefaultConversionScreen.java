package com.acoustic.encoder.features.conversion.ui;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.event.ConversionCloseRequestEvent;
import com.acoustic.encoder.features.conversion.ui.listener.ConversionViewAppShutdownListener;
import com.acoustic.encoder.features.conversion.ui.listener.ConversionViewCloseRequestListener;

public class DefaultConversionScreen implements ConversionScreen {

    private final ConversionController conversionController;

    private final ConversionViewManagerFactory managerFactory;

    private ConversionViewManager manager;

    private final EventBus eventBus;

    public DefaultConversionScreen(
            ConversionController conversionController,
            ConversionViewManagerFactory managerFactory,
            EventBus eventBus)
    {

        if (conversionController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.conversionController = conversionController;

        if (managerFactory == null) throw new IllegalArgumentException("Manager factory cannot be null!");
        this.managerFactory = managerFactory;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

        setEvents();

        initialize();

    }

    @Override
    public void initialize() {
        this.manager = managerFactory.createViewManager(conversionController);
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

    private void setEvents() {
        eventBus.subscribe(AppShutdownEvent.class, new ConversionViewAppShutdownListener(this));
        eventBus.subscribe(
                ConversionCloseRequestEvent.class, new ConversionViewCloseRequestListener(this));
    }

}

