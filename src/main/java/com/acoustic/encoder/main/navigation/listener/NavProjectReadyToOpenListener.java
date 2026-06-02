package com.acoustic.encoder.main.navigation.listener;

import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpenEvent;
import com.acoustic.encoder.main.navigation.AppNavigator;

import java.util.Objects;

public class NavProjectReadyToOpenListener implements EventListener<ProjectReadyToOpenEvent> {

    private final AppNavigator navigator;

    public NavProjectReadyToOpenListener(AppNavigator navigator) {
        this.navigator = Objects.requireNonNull(navigator,  "AppNavigator cannot be null");
    }

    @Override
    public void onEvent(ProjectReadyToOpenEvent event) {
        navigator.displayConversionScreen();
    }

}
