package com.acoustic.encoder.shared.factory;

import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.conversion.view.DefaultConversionScreen;
import com.acoustic.encoder.features.conversion.view.components.factory.ConversionScreenComponentsFactory;
import com.acoustic.encoder.features.player.controller.DefaultAudioPlayerController;
import com.acoustic.encoder.features.conversion.controller.DefaultConversionController;
import com.acoustic.encoder.features.player.service.AudioPlayerService;
import com.acoustic.encoder.features.conversion.service.ConversionService;
import com.acoustic.encoder.features.player.view.DefaultPlayerScreen;
import com.acoustic.encoder.features.player.view.PlayerScreen;

public class DefaultScreenFactory implements ScreenFactory  {

    private final ConversionService conversionService;

    private final ConversionScreenComponentsFactory conversionComponentsFactory;

    private final AudioPlayerService audioPlayerService;

    // private final PlayerScreenComponentsFactory playerComponentsFactory;


    public DefaultScreenFactory(
            ConversionService conversionService,
            ConversionScreenComponentsFactory conversionComponentsFactory,
            AudioPlayerService audioPlayerService
    ) {

        this.conversionService = conversionService;
        this.conversionComponentsFactory = conversionComponentsFactory;
        this.audioPlayerService = audioPlayerService;

    }

    public ConversionScreen createConversionScreen() {

        return new DefaultConversionScreen(
                new DefaultConversionController(this.conversionService),
                conversionComponentsFactory.createComponents()
        );
    }

    public PlayerScreen createPlayerScreen() {
        return new DefaultPlayerScreen(
                new DefaultAudioPlayerController(this.audioPlayerService)
        );
    }
}
