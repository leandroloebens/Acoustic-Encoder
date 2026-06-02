package com.acoustic.encoder.features.start.ui;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpenEvent;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.event.StartCloseRequestEvent;
import com.acoustic.encoder.features.start.ui.listener.StartViewProjectReadyToOpenListener;
import com.acoustic.encoder.features.start.ui.listener.StartViewAppShutdownListener;
import com.acoustic.encoder.features.start.ui.listener.StartViewCloseRequestListener;

import java.util.Objects;

public class DefaultStartScreen implements StartScreen {

    private final StartController controller;

    private final StartViewManagerFactory managerFactory;

    private StartViewManager manager;

    private final EventBus eventBus;

    public DefaultStartScreen(StartController controller, StartViewManagerFactory managerFactory, EventBus eventBus) {

        this.controller = Objects.requireNonNull(controller, "StartController cannot be null");

        this.managerFactory = Objects.requireNonNull(managerFactory, "StartViewManagerFactory cannot be null");

        this.eventBus = Objects.requireNonNull(eventBus, "EventBus cannot be null");

        setEvents();

        initialize();

    }

    @Override
    public void initialize() {
        this.manager = managerFactory.createViewManager(controller);
    }

    @Override
    public void showWindow() {
        manager.showFrame();
    }

    @Override
    public void hideWindow() {
        manager.hideFrame();
    }

    @Override
    public void closeWindow() {
        manager.disposeFrame();
    }

    private void setEvents() {
        eventBus.subscribe(AppShutdownEvent.class, new StartViewAppShutdownListener(this));
        eventBus.subscribe(StartCloseRequestEvent.class, new StartViewCloseRequestListener(this));
        eventBus.subscribe(ProjectReadyToOpenEvent.class, new StartViewProjectReadyToOpenListener(this));
    }

}
