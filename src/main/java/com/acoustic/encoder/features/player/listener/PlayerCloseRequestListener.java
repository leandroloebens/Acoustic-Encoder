package com.acoustic.encoder.features.player.listener;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerCloseRequestEvent;
import com.acoustic.encoder.domain.event.EventListener;

public class PlayerCloseRequestListener implements EventListener<PlayerCloseRequestEvent> {
    private final AudioPlayerController controller;

    public PlayerCloseRequestListener(AudioPlayerController controller) {
        this.controller = controller;
    }

    @Override
    public void onEvent(PlayerCloseRequestEvent event) {
        this.controller.handlePauseAction();
    }
}
