package com.acoustic.encoder.features.player.ui.swing.binder.action;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;

public class PlayerFrameExitAction implements Runnable {

    private final EventBus eventBus;

    public PlayerFrameExitAction(EventBus eventBus) {
        if (eventBus == null) throw new IllegalArgumentException("EventBus may not be null");
        this.eventBus = eventBus;
    }

    @Override
    public void run() {
        eventBus.publish(new PlayerClosedEvent());
    }
}
