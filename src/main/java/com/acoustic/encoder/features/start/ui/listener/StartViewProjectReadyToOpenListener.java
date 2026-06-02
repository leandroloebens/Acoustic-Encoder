package com.acoustic.encoder.features.start.ui.listener;

import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpenEvent;
import com.acoustic.encoder.features.start.ui.StartScreen;

import java.util.Objects;

public class StartViewProjectReadyToOpenListener implements EventListener<ProjectReadyToOpenEvent> {

    private final StartScreen startScreen;

    public StartViewProjectReadyToOpenListener(StartScreen startScreen) {
        this.startScreen = Objects.requireNonNull(startScreen, "Start screen cannot be null");
    }

    @Override
    public void onEvent(ProjectReadyToOpenEvent event) {
        startScreen.closeWindow();
    }

}
