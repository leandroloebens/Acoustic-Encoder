package com.acoustic.encoder.main.navigation.listener;

import com.acoustic.encoder.domain.event.ConversionCompletedEvent;
import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.main.navigation.AppNavigator;

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
