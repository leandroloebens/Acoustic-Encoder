package com.acoustic.encoder.features.player.listener;

import com.acoustic.encoder.features.conversion.event.ConversionCompletedEvent;
import com.acoustic.encoder.features.player.service.AudioPlayerService;
import com.acoustic.encoder.shared.event.EventListener;


public class PlayerConversionCompletedListener implements EventListener<ConversionCompletedEvent> {

    private final AudioPlayerService playerService;

    public PlayerConversionCompletedListener(AudioPlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void onEvent(ConversionCompletedEvent event) {

        try {
            playerService.setPlayerMusic(event.musicModel());
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }
}
