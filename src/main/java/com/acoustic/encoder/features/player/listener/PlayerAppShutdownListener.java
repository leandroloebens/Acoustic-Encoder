package com.acoustic.encoder.features.player.listener;

import com.acoustic.encoder.features.player.service.AudioPlayerService;
import com.acoustic.encoder.shared.event.AppShutdownEvent;
import com.acoustic.encoder.shared.event.EventListener;

import java.util.Objects;

public class PlayerAppShutdownListener implements EventListener<AppShutdownEvent> {

    private final AudioPlayerService audioPlayerService;

    public PlayerAppShutdownListener(AudioPlayerService audioPlayerService) {
        this.audioPlayerService = Objects.requireNonNull(
                audioPlayerService, "AudioPlayerService cannot be null!");
    }

    @Override
    public void onEvent(AppShutdownEvent event) {

        audioPlayerService.closePlayer();
    }
}
