package com.acoustic.encoder;

import com.acoustic.encoder.features.conversion.event.ConversionCompletedEvent;
import com.acoustic.encoder.features.player.audio.midi.*;
import com.acoustic.encoder.features.player.export.midi.MidiFileExporter;
import com.acoustic.encoder.features.conversion.view.swing.DefaultSwingConversionScreenAssembler;
import com.acoustic.encoder.features.conversion.view.swing.DefaultSwingConversionScreenManager;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.SwingConversionScreenComponentsFactory;
import com.acoustic.encoder.features.player.listener.PlayerConversionCompletedListener;
import com.acoustic.encoder.shared.event.DefaultEventBus;
import com.acoustic.encoder.shared.navigation.DefaultAppNavigator;
import com.acoustic.encoder.features.conversion.config.ConfigLoader;
import com.acoustic.encoder.shared.factory.DefaultScreenFactory;
import com.acoustic.encoder.features.conversion.parser.TextToInstructionParser;
import com.acoustic.encoder.features.player.service.DefaultAudioPlayerService;
import com.acoustic.encoder.features.conversion.service.DefaultConversionService;
import com.acoustic.encoder.shared.navigation.listener.NavigationConversionCompletedListener;

import javax.sound.midi.MidiSystem;

public class Main {

    void main() throws Exception {

        // Conversion Service
        var configLoader = new ConfigLoader(ConfigLoader.CONFIG_FILE_NAME);
        var instructionParser = new TextToInstructionParser(configLoader.loadConfigMap());

        var eventBus = new DefaultEventBus();

        var conversionService = new DefaultConversionService(instructionParser, eventBus);

        // Conversion View
        var conversionScreenComponentsFactory = new SwingConversionScreenComponentsFactory();
        var conversionScreenAssembler = new DefaultSwingConversionScreenAssembler(conversionScreenComponentsFactory.createComponents());
        var conversionScreenManager = new DefaultSwingConversionScreenManager(conversionScreenAssembler);

        // Audio Player Service
        var sequenceBuilder = new DefaultSequenceBuilder();
        var sequencePlayer = new DefaultSequencePlayer(MidiSystem.getSequencer());

        var audioPlayer = new JSoundAudioAdapter(sequenceBuilder, sequencePlayer);
        var musicExporter = new MidiFileExporter(sequencePlayer);

        var audioPlayerService = new DefaultAudioPlayerService(audioPlayer, musicExporter);

        // Player View

        // Navigation
        var screenFactory = new DefaultScreenFactory(
                conversionService,
                conversionScreenManager,
                audioPlayerService
        );
        var appNavigator = new DefaultAppNavigator(screenFactory);

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
