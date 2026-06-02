package com.acoustic.encoder.features.start.ui.listener;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.start.ui.StartScreen;

import java.util.Objects;

public class StartViewAppShutdownListener implements EventListener<AppShutdownEvent> {

    private final StartScreen startScreen;

    public StartViewAppShutdownListener(StartScreen startScreen) {
        this.startScreen = Objects.requireNonNull(startScreen, "Start screen must not be null");
    }

    @Override
    public void onEvent(AppShutdownEvent event) {
        startScreen.closeWindow();
    }

}
