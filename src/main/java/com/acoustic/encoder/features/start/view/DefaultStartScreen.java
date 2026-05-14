package com.acoustic.encoder.features.start.view;

import com.acoustic.encoder.features.start.controller.StartController;

public class DefaultStartScreen implements StartScreen {
    private static final String ILLEGAL_FILE_SERVICE_ARGUMENT = "File service cannot be null";
    private static final String ILLEGAL_MANAGER_ARGUMENT = "Start view manager cannot be null";

    private final StartController controller;

    private final StartViewManager manager;

    public DefaultStartScreen(StartController controller, StartViewManager manager) {
        if (controller == null) throw new IllegalArgumentException(ILLEGAL_FILE_SERVICE_ARGUMENT);
        this.controller = controller;

        if (manager == null) throw new IllegalArgumentException(ILLEGAL_MANAGER_ARGUMENT);
        this.manager = manager;

        this.startWindow();
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

    private void startWindow() {
        manager.assemble(this.controller);
    }

}
