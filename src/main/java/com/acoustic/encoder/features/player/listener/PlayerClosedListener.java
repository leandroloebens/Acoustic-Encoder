package com.acoustic.encoder.features.player.listener;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.shared.event.EventListener;

public class PlayerClosedListener implements EventListener<PlayerClosedEvent> {
    private final AudioPlayerController controller;

    public PlayerClosedListener(AudioPlayerController controller) {
        this.controller = controller;
    }

    @Override
    public void onEvent(PlayerClosedEvent event) {
        this.controller.handlePauseAction();
    }
}
