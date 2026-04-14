package com.acoustic.encoder.shared.navigation.listener;

import com.acoustic.encoder.features.conversion.event.ConversionCompletedEvent;
import com.acoustic.encoder.shared.event.EventListener;
import com.acoustic.encoder.shared.navigation.AppNavigator;

public class NavigationConversionCompletedListener implements EventListener<ConversionCompletedEvent> {

    private final AppNavigator navigator;

    public NavigationConversionCompletedListener(AppNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void onEvent(ConversionCompletedEvent event) {
        navigator.displayPlayerScreen();
    }
}
