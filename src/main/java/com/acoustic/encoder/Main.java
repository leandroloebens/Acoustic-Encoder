package com.acoustic.encoder;

import com.acoustic.encoder.features.conversion.event.ConversionCompletedEvent;
import com.acoustic.encoder.features.conversion.view.swing.components.config.SwingConversionConfig;
import com.acoustic.encoder.features.player.audio.midi.*;
import com.acoustic.encoder.features.player.export.midi.MidiFileExporter;
import com.acoustic.encoder.features.conversion.view.swing.DefaultSwingConversionScreenAssembler;
import com.acoustic.encoder.features.conversion.view.swing.DefaultSwingConversionScreenManager;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.SwingConversionScreenComponentsFactory;
import com.acoustic.encoder.features.player.listener.PlayerConversionCompletedListener;
import com.acoustic.encoder.shared.event.DefaultEventBus;
import com.acoustic.encoder.shared.navigation.DefaultAppNavigator;
import com.acoustic.encoder.features.conversion.config.ParserConfigLoader;
import com.acoustic.encoder.shared.factory.DefaultScreenFactory;
import com.acoustic.encoder.features.conversion.parser.TextToInstructionParser;
import com.acoustic.encoder.features.player.service.DefaultAudioPlayerService;
import com.acoustic.encoder.features.conversion.service.DefaultConversionService;
import com.acoustic.encoder.shared.navigation.listener.NavigationConversionCompletedListener;
import com.acoustic.encoder.shared.view.ScreenConfigLoader;

import javax.sound.midi.MidiSystem;

public class Main {

    void main() throws Exception {

        // Event Bus
        var eventBus = new DefaultEventBus();


        // Conversion Service
        var parserConfigLoader = new ParserConfigLoader(ParserConfigLoader.CONFIG_FILE_NAME);
        var instructionParser = new TextToInstructionParser(parserConfigLoader.loadConfigMap());

        var conversionService = new DefaultConversionService(instructionParser, eventBus);


        // Conversion View
        var screenConfigLoader = new ScreenConfigLoader(ScreenConfigLoader.CONVERSION_SCREEN_CONFIG_FILE);
        var conversionScreenComponentsFactory = new SwingConversionScreenComponentsFactory(screenConfigLoader.loadConfigMap());
        var conversionScreenAssembler = new DefaultSwingConversionScreenAssembler(conversionScreenComponentsFactory.createComponents());
        var conversionScreenManager = new DefaultSwingConversionScreenManager(conversionScreenAssembler, eventBus);


        // Audio Player Service
        var sequenceBuilder = new DefaultSequenceBuilder();
        var sequencePlayer = new DefaultSequencePlayer(MidiSystem.getSequencer());

        var audioPlayer = new JSoundAudioAdapter(sequenceBuilder, sequencePlayer);
        var musicExporter = new MidiFileExporter(sequencePlayer);

        var audioPlayerService = new DefaultAudioPlayerService(audioPlayer, musicExporter);


        // Player View


        // Navigation
        var screenFactory = new DefaultScreenFactory(
                eventBus,
                conversionService,
                conversionScreenManager,
                audioPlayerService
        );
        var appNavigator = new DefaultAppNavigator(screenFactory, eventBus);


        // subscribes listeners to their events
        eventBus.subscribe(
                ConversionCompletedEvent.class,
                new PlayerConversionCompletedListener(audioPlayerService)
        );
        eventBus.subscribe(
                ConversionCompletedEvent.class,
                new NavigationConversionCompletedListener(appNavigator)
        );

        appNavigator.startApp();

    }
}
