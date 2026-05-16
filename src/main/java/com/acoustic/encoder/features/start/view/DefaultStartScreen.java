package com.acoustic.encoder.features.start.view;

import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.event.StartScreenCloseRequestEvent;
import com.acoustic.encoder.shared.event.AppShutdownEvent;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.event.ProjectReadyToOpen;

public class DefaultStartScreen implements StartScreen {
    private static final String ILLEGAL_FILE_SERVICE_ARGUMENT = "File service cannot be null";
    private static final String ILLEGAL_MANAGER_ARGUMENT = "Start view manager cannot be null";

    private final StartController controller;

    private final StartViewManager manager;

    private final EventBus eventBus;

    public DefaultStartScreen(StartController controller, StartViewManager manager, EventBus eventBus) {
        if (controller == null) throw new IllegalArgumentException(ILLEGAL_FILE_SERVICE_ARGUMENT);
        this.controller = controller;

        if (manager == null) throw new IllegalArgumentException(ILLEGAL_MANAGER_ARGUMENT);
        this.manager = manager;

        if (eventBus == null) throw new IllegalArgumentException(ILLEGAL_MANAGER_ARGUMENT);
        this.eventBus = eventBus;

        eventBus.subscribe(AppShutdownEvent.class, event -> closeWindow());
        eventBus.subscribe(ProjectReadyToOpen.class, event -> closeWindow());

        initialize();

    }

    @Override
    public void initialize() {
        manager.assemble(this.controller);
    }

    @Override
    public void showWindow() {
        manager.show();
    }

    @Override
    public void hideWindow() {
        manager.hide();
    }

    @Override
    public void closeWindow() {
        manager.dispose();
    }

}
