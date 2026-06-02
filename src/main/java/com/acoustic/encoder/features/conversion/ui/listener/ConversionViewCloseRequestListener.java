package com.acoustic.encoder.features.conversion.ui.listener;

import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.conversion.event.ConversionCloseRequestEvent;
import com.acoustic.encoder.features.conversion.ui.ConversionScreen;

import java.util.Objects;

public class ConversionViewCloseRequestListener implements EventListener<ConversionCloseRequestEvent> {

    private final ConversionScreen conversionScreen;

    public ConversionViewCloseRequestListener(ConversionScreen conversionScreen) {
        this.conversionScreen = Objects.requireNonNull(conversionScreen, "Conversion Screen cannot be null");
    }

    @Override
    public void onEvent(ConversionCloseRequestEvent event) {
        conversionScreen.closeWindow();
    }
}
