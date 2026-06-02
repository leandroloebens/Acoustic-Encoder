package com.acoustic.encoder.features.player.ui.listener;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.player.ui.PlayerScreen;

import java.util.Objects;

public class PlayerViewAppShutdownListener implements EventListener<AppShutdownEvent> {

    private final PlayerScreen playerScreen;

    public PlayerViewAppShutdownListener(PlayerScreen playerScreen) {
        this.playerScreen = Objects.requireNonNull(playerScreen, "Player Screen cannot be null");
    }

    @Override
    public void onEvent(AppShutdownEvent event) {
        playerScreen.closeWindow();
    }

}
