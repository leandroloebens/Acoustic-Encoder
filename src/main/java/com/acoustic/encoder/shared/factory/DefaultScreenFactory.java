package com.acoustic.encoder.shared.factory;

import com.acoustic.encoder.features.conversion.service.FileService;
import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.conversion.view.ConversionViewManager;
import com.acoustic.encoder.features.conversion.view.DefaultConversionScreen;

import com.acoustic.encoder.features.conversion.view.swing.DefaultSwingConversionViewAssembler;
import com.acoustic.encoder.features.conversion.view.swing.DefaultSwingConversionViewManager;
import com.acoustic.encoder.features.conversion.view.swing.SwingConversionViewAssembler;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.DefaultSwingConversionViewComponentsFactory;
import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.controller.DefaultAudioPlayerController;
import com.acoustic.encoder.features.conversion.controller.DefaultConversionController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.listener.PlayerClosedListener;
import com.acoustic.encoder.features.player.service.AudioPlayerService;
import com.acoustic.encoder.features.conversion.service.ConversionService;
import com.acoustic.encoder.features.player.view.DefaultPlayerScreen;
import com.acoustic.encoder.features.player.view.PlayerScreen;
import com.acoustic.encoder.features.player.view.PlayerViewManager;
import com.acoustic.encoder.features.player.view.swing.DefaultSwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.view.swing.DefaultSwingPlayerViewManager;
import com.acoustic.encoder.features.player.view.swing.components.factory.DefaultSwingPlayerViewComponentsFactory;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.ViewConfigLoader;

public class DefaultScreenFactory implements ScreenFactory  {

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
        AudioPlayerController playerController = new DefaultAudioPlayerController(this.audioPlayerService);

        eventBus.subscribe(PlayerClosedEvent.class, new PlayerClosedListener(playerController));

        return new DefaultPlayerScreen(
                playerController,
                getPlayerViewManager()
        );
    }

    private ConversionViewManager getConversionViewManager() {
        ViewConfigLoader conversionViewConfigLoader =
                new ViewConfigLoader(ViewConfigLoader.CONVERSION_SCREEN_CONFIG_FILE);
        DefaultSwingConversionViewComponentsFactory conversionViewComponentsFactory =
                new DefaultSwingConversionViewComponentsFactory(conversionViewConfigLoader.loadConfigMap());
        SwingConversionViewAssembler conversionViewAssembler =
                new DefaultSwingConversionViewAssembler(conversionViewComponentsFactory.createComponents());

        return new DefaultSwingConversionViewManager(conversionViewAssembler, eventBus);
    }

    private PlayerViewManager getPlayerViewManager() {
        var playerViewConfigLoader = new ViewConfigLoader(ViewConfigLoader.PLAYER_SCREEN_CONFIG_FILE);
        var playerViewComponentsFactory =
                new DefaultSwingPlayerViewComponentsFactory(playerViewConfigLoader.loadConfigMap());
        var playerScreenAssembler =
                new DefaultSwingPlayerViewAssembler(playerViewComponentsFactory.createComponents());

        return new DefaultSwingPlayerViewManager(playerScreenAssembler, eventBus);
    }
}
