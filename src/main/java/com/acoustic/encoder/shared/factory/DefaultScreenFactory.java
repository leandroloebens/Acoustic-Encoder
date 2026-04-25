package com.acoustic.encoder.shared.factory;

import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.conversion.view.ConversionScreenManager;
import com.acoustic.encoder.features.conversion.view.DefaultConversionScreen;

import com.acoustic.encoder.features.player.controller.DefaultAudioPlayerController;
import com.acoustic.encoder.features.conversion.controller.DefaultConversionController;
import com.acoustic.encoder.features.player.service.AudioPlayerService;
import com.acoustic.encoder.features.conversion.service.ConversionService;
import com.acoustic.encoder.features.player.view.DefaultPlayerScreen;
import com.acoustic.encoder.features.player.view.PlayerScreen;
import com.acoustic.encoder.features.player.view.PlayerScreenManager;
import com.acoustic.encoder.shared.event.EventBus;

public class DefaultScreenFactory implements ScreenFactory  {

    private final EventBus eventBus;

    private final ConversionService conversionService;

    private final ConversionScreenManager conversionScreenManager;

    private final AudioPlayerService audioPlayerService;

    private final PlayerScreenManager playerScreenManager;


    public DefaultScreenFactory(
            EventBus eventBus,
            ConversionService conversionService,
            ConversionScreenManager conversionScreenManager,
            AudioPlayerService audioPlayerService,
            PlayerScreenManager playerScreenManager
    ) {

        this.conversionService = conversionService;
        this.conversionScreenManager = conversionScreenManager;
        this.audioPlayerService = audioPlayerService;
        this.playerScreenManager = playerScreenManager;
        this.eventBus = eventBus;

    }

    public ConversionScreen createConversionScreen() {

        return new DefaultConversionScreen(
                new DefaultConversionController(this.conversionService),
                conversionScreenManager
        );
    }

    public PlayerScreen createPlayerScreen() {
        return new DefaultPlayerScreen(
                new DefaultAudioPlayerController(this.audioPlayerService),
                playerScreenManager,
                eventBus
        );
    }
}
