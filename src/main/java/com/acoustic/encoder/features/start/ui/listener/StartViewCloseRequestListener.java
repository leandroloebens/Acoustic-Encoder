package com.acoustic.encoder.features.start.ui.listener;

import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.start.event.StartCloseRequestEvent;
import com.acoustic.encoder.features.start.ui.StartScreen;

import java.util.Objects;

public class StartViewCloseRequestListener implements EventListener<StartCloseRequestEvent> {

    private final StartScreen startScreen;

    public StartViewCloseRequestListener(StartScreen startScreen) {
        this.startScreen = Objects.requireNonNull(startScreen, "Start screen cannot be null");
    }

    @Override
    public void onEvent(StartCloseRequestEvent event) {
        startScreen.closeWindow();
    }

}
