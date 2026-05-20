package com.acoustic.encoder.main.factory;


import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.ports.TextRepository;
import com.acoustic.encoder.features.conversion.ui.ConversionScreen;
import com.acoustic.encoder.features.conversion.ui.DefaultConversionScreen;
import com.acoustic.encoder.features.conversion.ui.ConversionViewManagerFactory;
import com.acoustic.encoder.features.conversion.ui.swing.manager.DefaultSwingConversionViewManagerFactory;
import com.acoustic.encoder.features.player.ui.DefaultPlayerScreen;
import com.acoustic.encoder.features.player.ui.PlayerScreen;
import com.acoustic.encoder.features.player.ui.PlayerViewManagerFactory;
import com.acoustic.encoder.features.player.ui.swing.manager.DefaultSwingPlayerViewManagerFactory;
import com.acoustic.encoder.features.start.controller.DefaultStartController;
import com.acoustic.encoder.features.start.service.StartService;
import com.acoustic.encoder.features.start.ui.DefaultStartScreen;
import com.acoustic.encoder.features.start.ui.StartScreen;
import com.acoustic.encoder.features.start.ui.StartViewManagerFactory;
import com.acoustic.encoder.features.start.ui.swing.manager.DefaultSwingStartViewManagerFactory;
import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.controller.DefaultAudioPlayerController;
import com.acoustic.encoder.features.conversion.controller.DefaultConversionController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.listener.PlayerClosedListener;
import com.acoustic.encoder.features.player.service.AudioPlayerService;
import com.acoustic.encoder.features.conversion.service.ConversionService;


public class DefaultScreenFactory implements ScreenFactory  {

    private final EventBus eventBus;

    private final StartService startService;

    private final ConversionService conversionService;

    private final TextRepository textRepository;

    private final AudioPlayerService audioPlayerService;


    public DefaultScreenFactory(
            EventBus eventBus,
            StartService startService,
            ConversionService conversionService,
            TextRepository textRepository,
            AudioPlayerService audioPlayerService
    ) {

        this.startService = startService;
        this.conversionService = conversionService;
        this.textRepository = textRepository;
        this.audioPlayerService = audioPlayerService;
        this.eventBus = eventBus;

    }

    @Override
    public StartScreen createStartScreen() {
        StartViewManagerFactory managerFactory = new DefaultSwingStartViewManagerFactory(eventBus);

        return new DefaultStartScreen(
                new DefaultStartController(this.startService, this.textRepository),
                managerFactory.createViewManager(),
                eventBus
        );
    }

    @Override
    public ConversionScreen createConversionScreen() {
        ConversionViewManagerFactory managerFactory = new DefaultSwingConversionViewManagerFactory(eventBus);

        return new DefaultConversionScreen(
                new DefaultConversionController(this.conversionService, this.textRepository),
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
