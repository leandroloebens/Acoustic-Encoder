package com.acoustic.encoder.features.player.ui.listener;

import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.player.event.PlayerCloseRequestEvent;
import com.acoustic.encoder.features.player.ui.PlayerScreen;

import java.util.Objects;

public class PlayerViewCloseRequestListener implements EventListener<PlayerCloseRequestEvent> {

    private final PlayerScreen playerScreen;

    public PlayerViewCloseRequestListener(PlayerScreen playerScreen) {
        this.playerScreen = Objects.requireNonNull(playerScreen, "Player screen cannot be null");
    }

    @Override
    public void onEvent(PlayerCloseRequestEvent event) {
        playerScreen.hideWindow();
    }

}
