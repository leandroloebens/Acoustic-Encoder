package com.acoustic.encoder.shared.factory;

import com.acoustic.encoder.features.conversion.config.MusicParametersConfigLoader;
import com.acoustic.encoder.features.conversion.dto.MusicParameters;
import com.acoustic.encoder.features.conversion.service.FileService;
import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.conversion.view.ConversionViewManager;
import com.acoustic.encoder.features.conversion.view.DefaultConversionScreen;

import com.acoustic.encoder.features.conversion.view.swing.*;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.DefaultSwingConversionViewComponentsFactory;
import com.acoustic.encoder.features.player.controller.DefaultAudioPlayerController;
import com.acoustic.encoder.features.conversion.controller.DefaultConversionController;
import com.acoustic.encoder.features.player.service.AudioPlayerService;
import com.acoustic.encoder.features.conversion.service.ConversionService;
import com.acoustic.encoder.features.player.view.DefaultPlayerScreen;
import com.acoustic.encoder.features.player.view.PlayerScreen;
import com.acoustic.encoder.features.player.view.PlayerViewManager;
import com.acoustic.encoder.features.player.view.swing.DefaultSwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.view.swing.DefaultSwingPlayerViewManager;
import com.acoustic.encoder.features.player.view.swing.SwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.view.swing.components.factory.DefaultSwingPlayerViewComponentsFactory;
import com.acoustic.encoder.features.player.view.swing.components.factory.SwingPlayerViewComponentsFactory;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.ViewConfigLoader;

public class DefaultScreenFactory implements ScreenFactory  {

    private final static String CONVERSION_VIEW_CONFIG_FILE = "conversionViewMapping.properties";

    private final static String DEFAULT_MUSIC_PARAMETERS_FILE = "defaultMusicParameters.properties";

    private final static String PLAYER_VIEW_CONFIG_FILE = "playerViewMapping.properties";

    private final EventBus eventBus;

    private final ConversionService conversionService;

    private final FileService fileService;

    private final AudioPlayerService audioPlayerService;


    public DefaultScreenFactory(
            EventBus eventBus,
            ConversionService conversionService,
            FileService fileService,
            AudioPlayerService audioPlayerService
    ) {

        this.conversionService = conversionService;
        this.fileService = fileService;
        this.audioPlayerService = audioPlayerService;
        this.eventBus = eventBus;

    }

    public ConversionScreen createConversionScreen() {

        return new DefaultConversionScreen(
                new DefaultConversionController(this.conversionService, this.fileService),
                getConversionViewManager()
        );
    }

    public PlayerScreen createPlayerScreen() {
        return new DefaultPlayerScreen(
                new DefaultAudioPlayerController(this.audioPlayerService),
                getPlayerViewManager(),
                eventBus
        );
    }

    private ConversionViewManager getConversionViewManager() {
        ViewConfigLoader conversionViewConfigLoader =
                new ViewConfigLoader(CONVERSION_VIEW_CONFIG_FILE);

        DefaultSwingConversionViewComponentsFactory conversionViewComponentsFactory =
                new DefaultSwingConversionViewComponentsFactory(conversionViewConfigLoader.loadConfigMap());

        SwingConversionViewAssembler conversionViewAssembler =
                new DefaultSwingConversionViewAssembler(conversionViewComponentsFactory.createComponents());

        MusicParametersConfigLoader parametersLoader =
                new MusicParametersConfigLoader(DEFAULT_MUSIC_PARAMETERS_FILE);
        MusicParameters defaultMusicParameters = parametersLoader.loadDefaultMusicParameters();

        SwingConversionViewBinder conversionViewBinder =
                new DefaultSwingConversionViewBinder(defaultMusicParameters);

        return new DefaultSwingConversionViewManager(conversionViewAssembler, conversionViewBinder, eventBus);
    }

    private PlayerViewManager getPlayerViewManager() {
        ViewConfigLoader playerViewConfigLoader =
                new ViewConfigLoader(PLAYER_VIEW_CONFIG_FILE);

        SwingPlayerViewComponentsFactory playerViewComponentsFactory =
                new DefaultSwingPlayerViewComponentsFactory(playerViewConfigLoader.loadConfigMap());

        SwingPlayerViewAssembler playerViewAssembler =
                new DefaultSwingPlayerViewAssembler(playerViewComponentsFactory.createComponents());

        return new DefaultSwingPlayerViewManager(playerViewAssembler, eventBus);
    }
}
