package com.acoustic.encoder.features.start.ui.swing.binder.action;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.start.event.StartCloseRequestEvent;

public class StartFrameExitAction implements Runnable {

    private final EventBus eventBus;

    public StartFrameExitAction(EventBus eventBus) {
        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null");
        this.eventBus = eventBus;
    }

    @Override
    public void run() {
        eventBus.publish(new StartCloseRequestEvent());
    }
}
