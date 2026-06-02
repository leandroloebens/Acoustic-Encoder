package com.acoustic.encoder.main.navigation.listener;

import com.acoustic.encoder.domain.event.ConversionCompletedEvent;
import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.main.navigation.AppNavigator;

import java.util.Objects;

public class NavConversionCompletedListener implements EventListener<ConversionCompletedEvent> {

    private final AppNavigator navigator;

    public NavConversionCompletedListener(AppNavigator navigator) {
        this.navigator = Objects.requireNonNull(navigator, "AppNavigator cannot be null");
    }

    @Override
    public void onEvent(ConversionCompletedEvent event) {
        navigator.displayPlayerScreen();
    }
}
