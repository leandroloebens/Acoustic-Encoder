package com.acoustic.encoder.features.conversion.ui.swing.binder.action;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.event.ConversionCloseRequestEvent;

public class ConversionFrameExitAction implements Runnable {

    private final EventBus eventBus;

    public ConversionFrameExitAction(EventBus eventBus) {
        if (eventBus == null) throw new IllegalArgumentException("EventBus may not be null");
        this.eventBus = eventBus;
    }

    @Override
    public void run() {
        eventBus.publish(new ConversionCloseRequestEvent());
    }
}
