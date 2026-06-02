package com.acoustic.encoder.features.conversion.ui.listener;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.conversion.ui.ConversionScreen;

import java.util.Objects;

public class ConversionViewAppShutdownListener implements EventListener<AppShutdownEvent> {

    private final ConversionScreen conversionScreen;

    public ConversionViewAppShutdownListener(ConversionScreen conversionScreen) {
        this.conversionScreen = Objects.requireNonNull(conversionScreen, "Conversion Screen cannot be null");
    }

    @Override
    public void onEvent(AppShutdownEvent event) {
        conversionScreen.closeWindow();
    }
}
