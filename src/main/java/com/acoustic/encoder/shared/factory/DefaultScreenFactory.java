package com.acoustic.encoder.shared.factory;

import com.acoustic.encoder.features.conversion.view.ConversionViewManagerFactory;
import com.acoustic.encoder.features.conversion.view.swing.factory.DefaultSwingConversionViewManagerFactory;
import com.acoustic.encoder.features.player.view.PlayerViewManagerFactory;
import com.acoustic.encoder.features.player.view.swing.factory.DefaultSwingPlayerViewManagerFactory;
import com.acoustic.encoder.features.start.controller.DefaultStartController;
import com.acoustic.encoder.features.start.service.StartService;
import com.acoustic.encoder.features.start.view.DefaultStartScreen;
import com.acoustic.encoder.features.start.view.StartScreen;
import com.acoustic.encoder.features.start.view.StartViewManagerFactory;
import com.acoustic.encoder.features.start.view.swing.factory.DefaultSwingStartViewManagerFactory;
import com.acoustic.encoder.shared.service.FileService;
import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.conversion.view.DefaultConversionScreen;

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
import com.acoustic.encoder.features.player.view.swing.assembler.DefaultSwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.view.swing.DefaultSwingPlayerViewManager;
import com.acoustic.encoder.features.player.view.swing.assembler.SwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.view.swing.components.factory.DefaultSwingPlayerViewComponentsFactory;
import com.acoustic.encoder.features.player.view.swing.components.factory.SwingPlayerViewComponentsFactory;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.ViewConfigLoader;

public class DefaultScreenFactory implements ScreenFactory  {

    private final EventBus eventBus;

    private final StartService startService;

    private final ConversionService conversionService;

    private final FileService fileService;

    private final AudioPlayerService audioPlayerService;


    public DefaultScreenFactory(
            EventBus eventBus,
            StartService startService,
            ConversionService conversionService,
            FileService fileService,
            AudioPlayerService audioPlayerService
    ) {

        this.startService = startService;
        this.conversionService = conversionService;
        this.fileService = fileService;
        this.audioPlayerService = audioPlayerService;
        this.eventBus = eventBus;

    }

    @Override
    public StartScreen createStartScreen() {
        StartViewManagerFactory managerFactory = new DefaultSwingStartViewManagerFactory(eventBus);

        return new DefaultStartScreen(
                new DefaultStartController(this.startService, this.fileService),
                managerFactory.createViewManager(),
                eventBus
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
        PlayerViewManagerFactory managerFactory = new DefaultSwingPlayerViewManagerFactory(eventBus);

        AudioPlayerController playerController = new DefaultAudioPlayerController(this.audioPlayerService);

        eventBus.subscribe(PlayerClosedEvent.class, new PlayerClosedListener(playerController));

        return new DefaultPlayerScreen(
                playerController,
                managerFactory.createViewManager()
        );
    }
}
