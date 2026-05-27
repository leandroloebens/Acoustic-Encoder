package com.acoustic.encoder.features.start.ui;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpen;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.event.StartScreenCloseRequestEvent;

public class DefaultStartScreen implements StartScreen {
    private static final String ILLEGAL_FILE_SERVICE_ARGUMENT = "File service cannot be null";
    private static final String ILLEGAL_MANAGER_ARGUMENT = "Start view manager cannot be null";

    private final StartController controller;

    private final StartViewManagerFactory managerFactory;

    private StartViewManager manager;

    private final EventBus eventBus;

    public DefaultStartScreen(StartController controller, StartViewManagerFactory managerFactory, EventBus eventBus) {
        if (controller == null) throw new IllegalArgumentException(ILLEGAL_FILE_SERVICE_ARGUMENT);
        this.controller = controller;

        if (managerFactory == null) throw new IllegalArgumentException(ILLEGAL_MANAGER_ARGUMENT);
        this.managerFactory = managerFactory;

        if (eventBus == null) throw new IllegalArgumentException(ILLEGAL_MANAGER_ARGUMENT);
        this.eventBus = eventBus;

        setEvents();

        initialize();

    }

    @Override
    public void initialize() {
        this.manager = managerFactory.createViewManager(controller);;
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
        eventBus.subscribe(AppShutdownEvent.class, event -> closeWindow());
        eventBus.subscribe(StartScreenCloseRequestEvent.class, event -> closeWindow());
        eventBus.subscribe(ProjectReadyToOpen.class, event -> closeWindow());
    }

}
