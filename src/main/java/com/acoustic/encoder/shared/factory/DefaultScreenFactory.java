package com.acoustic.encoder.shared.factory;

import com.acoustic.encoder.features.conversion.config.MusicParametersConfigLoader;
import com.acoustic.encoder.features.conversion.dto.MusicParameters;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.SwingConversionViewComponentsFactory;
import com.acoustic.encoder.features.conversion.view.swing.frame.DefaultSwingConversionViewManager;
import com.acoustic.encoder.features.conversion.view.swing.frame.binder.DefaultSwingConversionViewFrameBinder;
import com.acoustic.encoder.features.conversion.view.swing.frame.binder.SwingConversionViewFrameBinder;
import com.acoustic.encoder.features.start.controller.DefaultStartController;
import com.acoustic.encoder.features.start.view.DefaultStartScreen;
import com.acoustic.encoder.features.start.view.StartScreen;
import com.acoustic.encoder.features.start.view.StartViewManager;
import com.acoustic.encoder.features.start.view.swing.components.factory.DefaultSwingStartViewComponentsFactory;
import com.acoustic.encoder.features.start.view.swing.components.factory.SwingStartViewComponentsFactory;
import com.acoustic.encoder.features.start.view.swing.frame.DefaultStartViewManager;
import com.acoustic.encoder.features.start.view.swing.frame.assembler.DefaultSwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.view.swing.frame.assembler.SwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.view.swing.frame.binder.DefaultSwingStartViewFrameBinder;
import com.acoustic.encoder.features.start.view.swing.frame.binder.SwingStartViewFrameBinder;
import com.acoustic.encoder.shared.service.FileService;
import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.conversion.view.ConversionViewManager;
import com.acoustic.encoder.features.conversion.view.DefaultConversionScreen;

import com.acoustic.encoder.features.conversion.view.swing.frame.assembler.DefaultSwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.view.swing.frame.assembler.SwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.DefaultSwingConversionViewComponentsFactory;
import com.acoustic.encoder.features.player.audio.midi.MidiInstrumentListProvider;
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

    private final static String START_VIEW_CONFIG_FILE = "startViewMapping.properties";
    private final static String CONVERSION_VIEW_CONFIG_FILE = "conversionViewMapping.properties";
    private final static String DEFAULT_MUSIC_PARAMETERS_FILE = "defaultMusicParameters.properties";
    private final static String PLAYER_VIEW_CONFIG_FILE = "playerViewMapping.properties";

    private final static int MIDI_INSTRUMENT_BANK = 0;

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
        return new DefaultStartScreen(
                new DefaultStartController(this.fileService),
                getStartViewManager()
        );
    }

    @Override
    public ConversionScreen createConversionScreen() {
        return new DefaultConversionScreen(
                new DefaultConversionController(this.conversionService, this.fileService),
                getConversionViewManager()
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

    private StartViewManager getStartViewManager() {
        return new DefaultStartViewManager(
                getStartViewAssembler(),
                new DefaultSwingStartViewFrameBinder()
        );
    }

    private SwingStartViewFrameAssembler getStartViewAssembler() {
        ViewConfigLoader startViewConfigLoader = new ViewConfigLoader(START_VIEW_CONFIG_FILE);

        SwingStartViewComponentsFactory startViewComponentsFactory =
                new DefaultSwingStartViewComponentsFactory(startViewConfigLoader.loadConfigMap());

        return new DefaultSwingStartViewFrameAssembler(startViewComponentsFactory.createComponents());
    }

    private ConversionViewManager getConversionViewManager() {
        SwingConversionViewFrameAssembler conversionViewAssembler = getConversionViewAssembler();

        MusicParametersConfigLoader parametersLoader =
                new MusicParametersConfigLoader(DEFAULT_MUSIC_PARAMETERS_FILE);
        MusicParameters defaultMusicParameters = parametersLoader.loadDefaultMusicParameters();

        SwingConversionViewFrameBinder conversionViewBinder =
                new DefaultSwingConversionViewFrameBinder(defaultMusicParameters);

        return new DefaultSwingConversionViewManager(conversionViewAssembler, conversionViewBinder, eventBus);
    }

    private SwingConversionViewFrameAssembler getConversionViewAssembler() {
        ViewConfigLoader conversionViewConfigLoader =
                new ViewConfigLoader(CONVERSION_VIEW_CONFIG_FILE);

        SwingConversionViewComponentsFactory conversionViewComponentsFactory =
                new DefaultSwingConversionViewComponentsFactory(
                        conversionViewConfigLoader.loadConfigMap(),
                        new MidiInstrumentListProvider(MIDI_INSTRUMENT_BANK)
                );

        return new DefaultSwingConversionViewFrameAssembler(conversionViewComponentsFactory.createComponents());
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
