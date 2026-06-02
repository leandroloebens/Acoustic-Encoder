package com.acoustic.encoder.main.navigation.listener;

import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.start.event.StartCloseRequestEvent;
import com.acoustic.encoder.main.navigation.AppNavigator;

import java.util.Objects;

public class NavStartCloseRequestListener implements EventListener<StartCloseRequestEvent> {

    private final AppNavigator navigator;

    public NavStartCloseRequestListener(AppNavigator appNavigator) {
        this.navigator = Objects.requireNonNull(appNavigator, "AppNavigator cannot be null");
    }

    @Override
    public void onEvent(StartCloseRequestEvent event) {
        navigator.closeApp();
    }

}
