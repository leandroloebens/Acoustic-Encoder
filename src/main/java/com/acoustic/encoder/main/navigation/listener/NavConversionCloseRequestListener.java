package com.acoustic.encoder.main.navigation.listener;

import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.conversion.event.ConversionCloseRequestEvent;
import com.acoustic.encoder.main.navigation.AppNavigator;

import java.util.Objects;

public class NavConversionCloseRequestListener implements EventListener<ConversionCloseRequestEvent> {

    private final AppNavigator navigator;

    public NavConversionCloseRequestListener(AppNavigator navigator) {
        this.navigator = Objects.requireNonNull(navigator, "AppNavigator cannot be null");
    }

    @Override
    public void onEvent(ConversionCloseRequestEvent event) {
        navigator.closeApp();
    }

}
