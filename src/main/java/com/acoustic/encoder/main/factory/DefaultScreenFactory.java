package com.acoustic.encoder.main.factory;

import com.acoustic.encoder.features.conversion.ui.factory.ConversionViewManagerFactory;
import com.acoustic.encoder.features.conversion.ui.swing.factory.DefaultSwingConversionViewManagerFactory;
import com.acoustic.encoder.features.start.controller.DefaultStartController;
import com.acoustic.encoder.features.start.ui.DefaultStartScreen;
import com.acoustic.encoder.features.start.ui.StartScreen;
import com.acoustic.encoder.features.start.ui.factory.StartViewManagerFactory;
import com.acoustic.encoder.features.start.ui.swing.factory.DefaultSwingStartViewManagerFactory;
import com.acoustic.encoder.domain.ports.FileService;
import com.acoustic.encoder.features.conversion.ui.ConversionScreen;
import com.acoustic.encoder.features.conversion.ui.DefaultConversionScreen;

import com.acoustic.encoder.features.player.controller.DefaultAudioPlayerController;
import com.acoustic.encoder.features.conversion.controller.DefaultConversionController;
import com.acoustic.encoder.features.player.service.AudioPlayerService;
import com.acoustic.encoder.features.conversion.service.ConversionService;
import com.acoustic.encoder.features.player.ui.DefaultPlayerScreen;
import com.acoustic.encoder.features.player.ui.PlayerScreen;
import com.acoustic.encoder.features.player.ui.PlayerViewManager;
import com.acoustic.encoder.features.player.ui.swing.DefaultSwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.ui.swing.DefaultSwingPlayerViewManager;
import com.acoustic.encoder.features.player.ui.swing.SwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.ui.swing.components.factory.DefaultSwingPlayerViewComponentsFactory;
import com.acoustic.encoder.features.player.ui.swing.components.factory.SwingPlayerViewComponentsFactory;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.infrastructure.ui_shared.ViewConfigLoader;

public class DefaultScreenFactory implements ScreenFactory {

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

    @Override
    public StartScreen createStartScreen() {
        StartViewManagerFactory managerFactory = new DefaultSwingStartViewManagerFactory();

        return new DefaultStartScreen(
                new DefaultStartController(this.fileService),
                managerFactory.createViewManager()
        );
    }

    @Override
    public ConversionScreen createConversionScreen() {
        ConversionViewManagerFactory managerFactory = new DefaultSwingConversionViewManagerFactory(eventBus);

        return new DefaultConversionScreen(
                new DefaultConversionController(this.conversionService, this.fileService),
                managerFactory.createViewManager()
        );
    }

    @Override
    public PlayerScreen createPlayerScreen() {
        return new DefaultPlayerScreen(
                new DefaultAudioPlayerController(this.audioPlayerService),
                getPlayerViewManager(),
                eventBus
        );
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
